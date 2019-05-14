package com.sequenceiq.cloudbreak.cloud.azure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.Subnet;
import com.sequenceiq.cloudbreak.cloud.azure.client.AzureClient;
import com.sequenceiq.cloudbreak.cloud.azure.client.AzureClientService;
import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;
import com.sequenceiq.cloudbreak.cloud.model.CreatedCloudNetwork;
import com.sequenceiq.cloudbreak.cloud.model.Platform;
import com.sequenceiq.cloudbreak.cloud.model.Region;
import com.sequenceiq.cloudbreak.cloud.model.Variant;

@RunWith(MockitoJUnitRunner.class)
public class AzureNetworkConnectorTest {

    public static final String ENV_NAME = "testEnv";

    public static final String SUBNET_CIDR_0 = "1.1.1.1/8";

    public static final String SUBNET_CIDR_1 = "2.2.2.2/8";

    public static final String SUBNET_ID_0 = "testEnv-0";

    public static final String SUBNET_ID_1 = "testEnv-1";

    @InjectMocks
    private AzureNetworkConnector underTest;

    @Mock
    private AzureClientService azureClientService;

    @Mock
    private AzureClient azureClient;

    @Mock
    private Network azureNetwork;

    @Before
    public void before() {
        azureNetwork = Mockito.mock(Network.class);
        when(azureNetwork.name()).thenReturn(ENV_NAME);
        when(azureNetwork.subnets()).thenReturn(createSubnetMap());
    }

    @Test
    public void testPlatformShouldReturnAzurePlatform() {
        Platform actual = underTest.platform();

        Assert.assertEquals(AzureConstants.PLATFORM, actual);
    }

    @Test
    public void testVariantShouldReturnAzurePlatform() {
        Variant actual = underTest.variant();

        Assert.assertEquals(AzureConstants.VARIANT, actual);
    }

    @Test
    public void testCreateNetworkWithSubnetsShouldReturnTheNetworkNameAndSubnetName() {
        CloudCredential cloudCredential = new CloudCredential(1L, "credential");
        Region region = Region.region("US_WEST_2");
        String networkCidr = "0.0.0.0/16";
        Set<String> subnetCidrs = new HashSet<>(Arrays.asList(SUBNET_CIDR_0, SUBNET_CIDR_1));

        when(azureClientService.getClient(cloudCredential)).thenReturn(azureClient);
        when(azureClient.createNetwork(eq(ENV_NAME), eq(region), anyString(), eq(networkCidr), anyMap())).thenReturn(azureNetwork);

        CreatedCloudNetwork actual = underTest.createNetworkWithSubnets(ENV_NAME, cloudCredential, region, networkCidr, subnetCidrs);

        assertEquals(ENV_NAME, actual.getNetworkId());
        assertTrue(actual.getSubnetIds().contains(SUBNET_ID_0));
        assertTrue(actual.getSubnetIds().contains(SUBNET_ID_1));
        assertTrue(actual.getSubnetIds().size() == 2);
    }

    private Map<String, Subnet> createSubnetMap() {
        Map<String, Subnet> subnetMap = new HashMap<>();
        subnetMap.put(SUBNET_ID_0, Mockito.mock(Subnet.class));
        subnetMap.put(SUBNET_ID_1, Mockito.mock(Subnet.class));
        return subnetMap;
    }

}