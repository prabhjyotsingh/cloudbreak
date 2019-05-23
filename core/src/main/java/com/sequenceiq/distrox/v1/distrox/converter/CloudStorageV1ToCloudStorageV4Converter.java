package com.sequenceiq.distrox.v1.distrox.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.CloudStorageV4Request;
import com.sequenceiq.distrox.api.distrox.request.cluster.storage.CloudStorageV1Request;

@Component
public class CloudStorageV1ToCloudStorageV4Converter {

    @Inject
    private LocationV4ToLocationV1Converter locationConverter;

    public CloudStorageV4Request convert(CloudStorageV1Request source) {
        CloudStorageV4Request response = new CloudStorageV4Request();
        response.setLocations(locationConverter.convert(source.getLocations()));
        response.setS3(source.getS3());
        return response;
    }
}
