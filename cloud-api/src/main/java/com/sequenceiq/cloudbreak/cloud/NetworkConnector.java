package com.sequenceiq.cloudbreak.cloud;

import java.util.Set;

import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;
import com.sequenceiq.cloudbreak.cloud.model.CreatedCloudNetwork;
import com.sequenceiq.cloudbreak.cloud.model.Region;

/**
 * Network connectors.
 */
public interface NetworkConnector extends CloudPlatformAware {

    CreatedCloudNetwork createNetworkWithSubnets(String envName, CloudCredential cloudCredential, Region region, String networkCidr, Set<String> subnetCidrs);

    CreatedCloudNetwork deleteNetworkWithSubnets(CloudCredential cloudCredential);
}
