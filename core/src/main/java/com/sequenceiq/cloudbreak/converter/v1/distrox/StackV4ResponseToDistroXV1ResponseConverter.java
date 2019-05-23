package com.sequenceiq.cloudbreak.converter.v1.distrox;

import org.springframework.stereotype.Component;

import com.sequenceiq.distrox.api.v1.distrox.model.DistroXV1Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.StackV4Response;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;

@Component
public class StackV4ResponseToDistroXV1ResponseConverter
        extends AbstractConversionServiceAwareConverter<StackV4Response, DistroXV1Response> {

    @Override
    public DistroXV1Response convert(StackV4Response source) {
        DistroXV1Response distroXV1Response = new DistroXV1Response();
        return distroXV1Response;
    }
}
