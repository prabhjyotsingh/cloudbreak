package com.sequenceiq.cloudbreak.converter.v1.distrox.update;

import org.springframework.stereotype.Component;

import com.sequenceiq.distrox.api.distrox.request.cluster.repository.RepositoryV1Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.repository.RepositoryV4Request;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;

@Component
public class RepositoryV1RequestToRepositoryV4Request
        extends AbstractConversionServiceAwareConverter<RepositoryV1Request, RepositoryV4Request> {
    @Override
    public RepositoryV4Request convert(RepositoryV1Request source) {
        RepositoryV4Request stackRepositoryV4Request = new RepositoryV4Request();
        stackRepositoryV4Request.setBaseUrl(source.getBaseUrl());
        stackRepositoryV4Request.setGpgKeyUrl(source.getGpgKeyUrl());
        stackRepositoryV4Request.setVersion(source.getVersion());
        return stackRepositoryV4Request;
    }
}
