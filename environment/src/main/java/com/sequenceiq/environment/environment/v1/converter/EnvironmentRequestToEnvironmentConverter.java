package com.sequenceiq.environment.environment.v1.converter;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.environment.api.v1.environment.model.request.EnvironmentRequest;
import com.sequenceiq.environment.environment.domain.Environment;

@Component
public class EnvironmentRequestToEnvironmentConverter extends AbstractConversionServiceAwareConverter<EnvironmentRequest, Environment> {
    @Override
    public Environment convert(EnvironmentRequest source) {
        Environment environment = new Environment();
        environment.setName(source.getName());
        environment.setDescription(source.getDescription());
        return environment;
    }
}
