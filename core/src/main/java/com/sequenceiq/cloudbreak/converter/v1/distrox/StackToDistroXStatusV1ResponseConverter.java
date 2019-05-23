package com.sequenceiq.cloudbreak.converter.v1.distrox;

import org.springframework.stereotype.Component;

import com.sequenceiq.distrox.api.distrox.response.DistroXStatusV1Response;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.domain.stack.Stack;

@Component
public class StackToDistroXStatusV1ResponseConverter
        extends AbstractConversionServiceAwareConverter<Stack, DistroXStatusV1Response> {
    @Override
    public DistroXStatusV1Response convert(Stack source) {
        DistroXStatusV1Response distroXStatusV1Response = new DistroXStatusV1Response();
        distroXStatusV1Response.setStatus(source.getStatus());
        distroXStatusV1Response.setStatusReason(source.getStatusReason());
        distroXStatusV1Response.setClusterStatus(source.getCluster().getStatus());
        distroXStatusV1Response.setClusterStatusReason(source.getCluster().getStatusReason());
        // TODO need crn distroXStatusV1Response.setId(source.getResourceCrn());
        return distroXStatusV1Response;
    }
}
