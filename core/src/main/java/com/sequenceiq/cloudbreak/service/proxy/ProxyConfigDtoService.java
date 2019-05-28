package com.sequenceiq.cloudbreak.service.proxy;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.dto.ProxyConfig;
import com.sequenceiq.cloudbreak.service.secret.service.SecretService;
import com.sequenceiq.environment.api.v1.proxy.endpoint.ProxyEndpoint;
import com.sequenceiq.environment.api.v1.proxy.model.response.ProxyResponse;
import com.sequenceiq.environment.client.EnvironmentServiceClient;

@Service
public class ProxyConfigDtoService {

    @Inject
    private EnvironmentServiceClient environmentServiceClient;

    @Inject
    private SecretService secretService;

    public ProxyConfig get(String resourceCrn, String accountId, String userCrn) {
        ProxyEndpoint proxyEndpoint = environmentServiceClient
                .withCrn(userCrn)
                .proxyV1Endpoint();

        ProxyResponse proxyResponse = proxyEndpoint.get(resourceCrn);

        return ProxyConfig.builder()
                .withName(proxyResponse.getName())
                .withCrn(proxyResponse.getCrn())
                .withCrn(userCrn)
                .withAccountId(accountId)
                .withProtocol(proxyResponse.getProtocol())
                .withServerHost(proxyResponse.getHost())
                .withServerPort(proxyResponse.getPort())
                .withUserName(secretService.getByResponse(proxyResponse.getUserName()))
                .withPassword(secretService.getByResponse(proxyResponse.getPassword()))
                .build();
    }
}
