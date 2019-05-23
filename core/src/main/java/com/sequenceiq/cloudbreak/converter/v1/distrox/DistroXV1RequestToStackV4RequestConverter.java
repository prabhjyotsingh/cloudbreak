package com.sequenceiq.cloudbreak.converter.v1.distrox;

import org.springframework.stereotype.Component;

import com.sequenceiq.distrox.api.v1.distrox.model.DistroXV1Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.StackV4Request;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;

@Component
public class DistroXV1RequestToStackV4RequestConverter
        extends AbstractConversionServiceAwareConverter<DistroXV1Request, StackV4Request> {

    @Override
    public StackV4Request convert(DistroXV1Request source) {
        StackV4Request stackV4Request = new StackV4Request();
        return stackV4Request;
    }
}
