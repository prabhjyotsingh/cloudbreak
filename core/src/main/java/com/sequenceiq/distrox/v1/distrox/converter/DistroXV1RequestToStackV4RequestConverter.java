package com.sequenceiq.distrox.v1.distrox.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.StackType;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.StackV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.environment.EnvironmentSettingsV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.environment.placement.PlacementSettingsV4Request;
import com.sequenceiq.cloudbreak.domain.environment.Environment;
import com.sequenceiq.cloudbreak.domain.environment.Region;
import com.sequenceiq.cloudbreak.service.environment.EnvironmentService;
import com.sequenceiq.distrox.api.v1.distrox.model.DistroXV1Request;

@Component
public class DistroXV1RequestToStackV4RequestConverter {

    @Inject
    private DistroXEnvironmentV1ToEnvironmentSettingsConverter environmentConverter;

    @Inject
    private DistroXAuthenticationToStaAuthenticationConverter authenticationConverter;

    @Inject
    private DistroXImageToImageSettingsConverter imageConverter;

    @Inject
    private DistroXClusterToClusterConverter clusterConverter;

    @Inject
    private InstanceGroupV1ToInstanceGroupV4Converter instanceGroupConverter;

    @Inject
    private EnvironmentService environmentService;

    public StackV4Request convert(DistroXV1Request source) {
        StackV4Request request = new StackV4Request();
        request.setName(source.getName());
        request.setType(StackType.WORKLOAD);
        EnvironmentSettingsV4Request environment = environmentConverter.convert(source.getEnvironment());
        request.setEnvironment(environment);
        request.setCloudPlatform(source.getCloudPlatform());
        request.setAuthentication(authenticationConverter.convert(source.getAuthentication()));
        request.setImage(imageConverter.convert(source.getImage()));
        request.setCluster(clusterConverter.convert(source.getCluster()));
        request.setPlacement(getPlacement());
        request.setInstanceGroups(instanceGroupConverter.convert(source.getInstanceGroups()));
        request.setInputs(source.getInputs());
        request.setTags(null);
        request.setNetwork();
        request.setSharedService();
        request.setCustomDomain(null);
        request.setGatewayPort(null);
        request.setAws(null);
        request.setAzure(null);
        request.setGcp(null);
        request.setMock(null);
        request.setOpenstack(null);
        request.setYarn(null);
        request.setTimeToLive(0L);
        return request;
    }

    private PlacementSettingsV4Request getPlacement() {
        Environment environment = environmentService.getByNameForWorkspace("", null);
        PlacementSettingsV4Request placementSettings = new PlacementSettingsV4Request();
        placementSettings.setRegion(environment.getRegionSet().stream().findFirst().map(Region::getName).orElse(null));
        return placementSettings;
    }
}
