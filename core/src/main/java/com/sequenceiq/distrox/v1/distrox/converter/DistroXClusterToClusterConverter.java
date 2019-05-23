package com.sequenceiq.distrox.v1.distrox.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.ExecutorType;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.ClusterV4Request;
import com.sequenceiq.cloudbreak.domain.LdapConfig;
import com.sequenceiq.cloudbreak.service.ldapconfig.LdapConfigService;
import com.sequenceiq.cloudbreak.service.rdsconfig.RdsConfigService;
import com.sequenceiq.cloudbreak.service.workspace.WorkspaceService;
import com.sequenceiq.cloudbreak.workspace.model.Workspace;
import com.sequenceiq.distrox.api.v1.distrox.model.cluster.DistroXClusterV1Request;

@Component
public class DistroXClusterToClusterConverter {

    @Inject
    private RdsConfigService rdsConfigService;

    @Inject
    private WorkspaceService workspaceService;

    @Inject
    private LdapConfigService ldapConfigService;

    @Inject
    private ClouderaManagerV1ToClouderaManagerV4Converter cmConverter;

    @Inject
    private CloudStorageV1ToCloudStorageV4Converter cloudStorageConverter;

    @Inject
    private GatewayV1ToGatewayV4Converter gatewayConverter;

    public ClusterV4Request convert(DistroXClusterV1Request source) {

        Workspace workspace = workspaceService.getForCurrentUser();

        ClusterV4Request response = new ClusterV4Request();
        response.setKerberosName(null);
        response.setGateway(gatewayConverter.convert(source.getGateway()));
        response.setAmbari(null);
        response.setName(null);
        response.setDatabases(source.getDatabases());
        response.setLdapName(ldapConfigService.findAllInWorkspace(workspace.getId()).stream().findFirst().map(LdapConfig::getName).orElse(null));
        response.setBlueprintName(source.getBlueprintName());
        response.setUserName(source.getUserName());
        response.setPassword(source.getPassword());
        response.setProxyName(source.getProxy());
        response.setCm(cmConverter.convert(source.getCm()));
        response.setCloudStorage(cloudStorageConverter.convert(source.getCloudStorage()));
        response.setValidateBlueprint(false);
        response.setExecutorType(ExecutorType.DEFAULT);
        response.setCustomContainer(null);
        response.setCustomQueue(null);
        return response;
    }
}
