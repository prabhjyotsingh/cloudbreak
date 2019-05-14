package com.sequenceiq.cloudbreak.cloud.event.platform;

import java.util.Set;

import com.sequenceiq.cloudbreak.cloud.event.CloudPlatformRequest;
import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;
import com.sequenceiq.cloudbreak.cloud.model.ExtendedCloudCredential;

public class CloudNetworkCreationRequest extends CloudPlatformRequest<CloudNetworkCreationResult> {

    private final String envName;

    private final ExtendedCloudCredential extendedCloudCredential;

    private final String variant;

    private final String region;

    private final String networkCidr;

    private final Set<String> subnetCidrs;

    public CloudNetworkCreationRequest(
            String envName,
            CloudCredential cloudCredential,
            ExtendedCloudCredential extendedCloudCredential,
            String variant,
            String region,
            String networkCidr,
            Set<String> subnetCidrs) {
        super(null, cloudCredential);
        this.envName = envName;
        this.extendedCloudCredential = extendedCloudCredential;
        this.subnetCidrs = subnetCidrs;
        this.region = region;
        this.variant = variant;
        this.networkCidr = networkCidr;
    }

    public String getEnvName() {
        return envName;
    }

    public String getRegion() {
        return region;
    }

    public String getNetworkCidr() {
        return networkCidr;
    }

    public Set<String> getSubnetCidrs() {
        return subnetCidrs;
    }

    public ExtendedCloudCredential getExtendedCloudCredential() {
        return extendedCloudCredential;
    }

    public String getVariant() {
        return variant;
    }
}
