package com.sequenceiq.cloudbreak.converter.v1.distrox;

import org.springframework.stereotype.Component;

import com.sequenceiq.distrox.api.distrox.request.UpdateDistroXV1Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.UpdateClusterV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.repository.RepositoryV4Request;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;

@Component
public class UpdateDistroXV1RequestToUpdateClusterV4RequestConverter
        extends AbstractConversionServiceAwareConverter<UpdateDistroXV1Request, UpdateClusterV4Request> {
    @Override
    public UpdateClusterV4Request convert(UpdateDistroXV1Request source) {
        UpdateClusterV4Request updateClusterV4Request = new UpdateClusterV4Request();
        updateClusterV4Request.setHostgroups(source.getHostgroups());
        updateClusterV4Request.setUserNamePassword(source.getUserNamePassword());
        updateClusterV4Request.setStackRepository(source.getRepository());
        updateClusterV4Request.setBlueprintName(source.getBlueprintName());
        updateClusterV4Request.setHostGroupAdjustment(source.getHostGroupAdjustment());
        updateClusterV4Request.setKerberosPassword(source.getKerberosPassword());
        updateClusterV4Request.setKerberosPrincipal(source.getKerberosPrincipal());
        updateClusterV4Request.setStatus(source.getStatus());
        updateClusterV4Request.setValidateBlueprint(source.getValidateBlueprint());
        updateClusterV4Request.setStackRepository(getConversionService().convert(source.getRepository(), RepositoryV4Request.class));

        return updateClusterV4Request;
    }
}
