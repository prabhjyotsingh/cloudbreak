package com.sequenceiq.environment.environment.v1.converter;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.environment.api.v1.environment.model.response.LocationResponse;
import com.sequenceiq.environment.environment.domain.Environment;

@Component
public class EnvironmentToLocationResponseConverter extends AbstractConversionServiceAwareConverter<Environment, LocationResponse> {

    @Override
    public LocationResponse convert(Environment environment) {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setLatitude(environment.getLatitude());
        locationResponse.setLongitude(environment.getLongitude());
        locationResponse.setName(environment.getLocation());
        locationResponse.setDisplayName(environment.getLocationDisplayName());
        return locationResponse;
    }
}
