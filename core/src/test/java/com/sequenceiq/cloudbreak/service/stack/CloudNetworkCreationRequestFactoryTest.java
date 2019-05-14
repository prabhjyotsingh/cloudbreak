package com.sequenceiq.cloudbreak.service.stack;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sequenceiq.cloudbreak.cloud.event.platform.CloudNetworkCreationRequest;
import com.sequenceiq.cloudbreak.cloud.model.ExtendedCloudCredential;
import com.sequenceiq.cloudbreak.converter.spi.CredentialToExtendedCloudCredentialConverter;
import com.sequenceiq.cloudbreak.domain.Credential;

@RunWith(MockitoJUnitRunner.class)
public class CloudNetworkCreationRequestFactoryTest {

    public static final String ENV_NAME = "TEST_ENV";

    public static final String CLOUD_PLATFORM = "AWS";

    public static final String REGION = "US-WEST";

    public static final String NETWORK_CIDR = "0.0.0.0/16";

    public static final Set<String> SUBNET_CIDRS = Set.of("1.1.1.1/8", "2.2.2.2/8");

    @InjectMocks
    private CloudNetworkCreationRequestFactory underTest;

    @Mock
    private CredentialToExtendedCloudCredentialConverter credentialToExtendedCloudCredentialConverter;

    @Test
    public void testCreateShouldCreateANewCreateCloudNetworkRequestInstance() {
        Credential credential = new Credential();
        ExtendedCloudCredential cloudCredential = mock(ExtendedCloudCredential.class);
        when(credentialToExtendedCloudCredentialConverter.convert(credential)).thenReturn(cloudCredential);

        CloudNetworkCreationRequest actual = underTest.create(ENV_NAME, credential, CLOUD_PLATFORM, REGION, NETWORK_CIDR, SUBNET_CIDRS);

        assertEquals(ENV_NAME, actual.getEnvName());
        assertEquals(cloudCredential, actual.getCloudCredential());
        assertEquals(cloudCredential, actual.getExtendedCloudCredential());
        assertEquals(CLOUD_PLATFORM, actual.getVariant());
        assertEquals(REGION, actual.getRegion());
        assertEquals(NETWORK_CIDR, actual.getNetworkCidr());
        assertEquals(SUBNET_CIDRS, actual.getSubnetCidrs());
    }
}