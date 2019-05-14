package com.sequenceiq.cloudbreak.service.stack;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.cloud.event.platform.CloudNetworkCreationRequest;
import com.sequenceiq.cloudbreak.cloud.model.ExtendedCloudCredential;
import com.sequenceiq.cloudbreak.converter.spi.CredentialToExtendedCloudCredentialConverter;
import com.sequenceiq.cloudbreak.domain.Credential;

@Service
class CloudNetworkCreationRequestFactory {

    @Inject
    private CredentialToExtendedCloudCredentialConverter credentialToExtendedCloudCredentialConverter;

    CloudNetworkCreationRequest create(String envName, Credential credential, String cloudPlatform, String region, String networkCidr, Set<String> subnetCidrs) {
        ExtendedCloudCredential cloudCredential = credentialToExtendedCloudCredentialConverter.convert(credential);

        return new CloudNetworkCreationRequest(envName, cloudCredential, cloudCredential, cloudPlatform, region, networkCidr, subnetCidrs);
    }
}
