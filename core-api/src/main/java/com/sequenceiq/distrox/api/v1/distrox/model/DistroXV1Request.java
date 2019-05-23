package com.sequenceiq.distrox.api.v1.distrox.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.distrox.api.distrox.request.environment.DistroXEnvironmentV1Request;
import com.sequenceiq.distrox.api.v1.distrox.model.authentication.DistroXAuthenticationV1Request;
import com.sequenceiq.distrox.api.v1.distrox.model.cluster.DistroXClusterV1Request;
import com.sequenceiq.distrox.api.v1.distrox.model.image.DistroXImageV1Request;
import com.sequenceiq.distrox.api.v1.distrox.model.instancegroup.InstanceGroupV1Request;
import com.sequenceiq.distrox.api.v1.distrox.model.network.NetworkV1Request;

import io.swagger.annotations.ApiModel;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class DistroXV1Request implements Serializable {

    private String name;

    private DistroXEnvironmentV1Request environment;

    private Set<InstanceGroupV1Request> instanceGroups;

    private DistroXAuthenticationV1Request authentication;

    private DistroXImageV1Request image;

    private NetworkV1Request network;

    private DistroXClusterV1Request cluster;

    private CloudPlatform cloudPlatform;

    private Map<String, Object> inputs = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DistroXEnvironmentV1Request getEnvironment() {
        return environment;
    }

    public void setEnvironment(DistroXEnvironmentV1Request environment) {
        this.environment = environment;
    }

    public Set<InstanceGroupV1Request> getInstanceGroups() {
        return instanceGroups;
    }

    public void setInstanceGroups(Set<InstanceGroupV1Request> instanceGroups) {
        this.instanceGroups = instanceGroups;
    }

    public DistroXAuthenticationV1Request getAuthentication() {
        return authentication;
    }

    public void setAuthentication(DistroXAuthenticationV1Request authentication) {
        this.authentication = authentication;
    }

    public DistroXImageV1Request getImage() {
        return image;
    }

    public void setImage(DistroXImageV1Request image) {
        this.image = image;
    }

    public NetworkV1Request getNetwork() {
        return network;
    }

    public void setNetwork(NetworkV1Request network) {
        this.network = network;
    }

    public DistroXClusterV1Request getCluster() {
        return cluster;
    }

    public void setCluster(DistroXClusterV1Request cluster) {
        this.cluster = cluster;
    }

    public Map<String, Object> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, Object> inputs) {
        this.inputs = inputs;
    }

    public CloudPlatform getCloudPlatform() {
        return cloudPlatform;
    }

    public void setCloudPlatform(CloudPlatform cloudPlatform) {
        this.cloudPlatform = cloudPlatform;
    }
}
