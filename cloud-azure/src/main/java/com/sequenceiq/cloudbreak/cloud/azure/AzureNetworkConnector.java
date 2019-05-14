package com.sequenceiq.cloudbreak.cloud.azure;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.microsoft.azure.management.network.Network;
import com.sequenceiq.cloudbreak.cloud.NetworkConnector;
import com.sequenceiq.cloudbreak.cloud.azure.client.AzureClient;
import com.sequenceiq.cloudbreak.cloud.azure.client.AzureClientService;
import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;
import com.sequenceiq.cloudbreak.cloud.model.CreatedCloudNetwork;
import com.sequenceiq.cloudbreak.cloud.model.Platform;
import com.sequenceiq.cloudbreak.cloud.model.Region;
import com.sequenceiq.cloudbreak.cloud.model.Variant;

@Service
public class AzureNetworkConnector implements NetworkConnector {

    public static final int TOKEN_LENGTH = 8;

    @Inject
    private AzureClientService azureClientService;

    @Override
    public CreatedCloudNetwork createNetworkWithSubnets(String envName, CloudCredential cloudCredential, Region region, String networkCidr,
            Set<String> subnetCidrs) {
        AzureClient azureClient = azureClientService.getClient(cloudCredential);
        String resourceGroupName = createResourceGroupName(envName);
        Map<String, String> subnetCidrMap = createSubnetCidrMap(subnetCidrs, envName);
        Network network = azureClient.createNetwork(envName, region, resourceGroupName, networkCidr, subnetCidrMap);
        return new CreatedCloudNetwork(network.name(), network.subnets().keySet(), createProperties(resourceGroupName));
    }

    @Override
    public CreatedCloudNetwork deleteNetworkWithSubnets(CloudCredential cloudCredential) {
        return null;
    }

    @Override
    public Platform platform() {
        return AzureConstants.PLATFORM;
    }

    @Override
    public Variant variant() {
        return AzureConstants.VARIANT;
    }

    private String createResourceGroupName(String envName) {
        return String.join("-", envName, randomAlphabetic(TOKEN_LENGTH).toLowerCase());
    }

    private Map<String, String> createSubnetCidrMap(Set<String> subnetCidrs, String envName) {
        Map<String, String> subnetMap = new HashMap<>();
        for (int i = 0; i < subnetCidrs.size(); i++) {
            subnetMap.put(String.join("-", envName, String.valueOf(i)), subnetCidrs.iterator().next());
        }
        return subnetMap;
    }

    private Map<String, Object> createProperties(String resourceGroupName) {
        return Collections.singletonMap("resourceGroupName", resourceGroupName);
    }
}
