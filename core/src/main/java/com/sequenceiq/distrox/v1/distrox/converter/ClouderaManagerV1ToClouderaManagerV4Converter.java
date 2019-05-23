package com.sequenceiq.distrox.v1.distrox.converter;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.cm.ClouderaManagerV4Request;
import com.sequenceiq.distrox.api.distrox.request.cluster.cm.ClouderaManagerV1Request;

@Component
public class ClouderaManagerV1ToClouderaManagerV4Converter {

    public ClouderaManagerV4Request convert(ClouderaManagerV1Request source) {
        ClouderaManagerV4Request response = new ClouderaManagerV4Request();
//        response.setProducts();
//        response.setRepository();
        return response;
    }
}
