package com.sequenceiq.distrox.api.distrox.request;

import java.util.HashSet;
import java.util.Set;

import com.sequenceiq.distrox.api.v1.distrox.model.instancegroup.InstanceGroupV1Request;
import com.sequenceiq.distrox.api.v1.distrox.model.network.NetworkV1Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DistroXValidationV4Request implements JsonEntity {

    @ApiModelProperty(value = ClusterModelDescription.HOSTGROUPS, required = true)
    private Set<HostGroupV1Request> hostGroups = new HashSet<>();

    @ApiModelProperty(value = StackModelDescription.INSTANCE_GROUPS, required = true)
    private Set<InstanceGroupV1Request> instanceGroups = new HashSet<>();

    @ApiModelProperty(ClusterModelDescription.BLUEPRINT_NAME)
    private String blueprintName;

    @ApiModelProperty(StackModelDescription.NETWORK_ID)
    private Long networkId;

    @ApiModelProperty(StackModelDescription.NETWORK)
    private NetworkV1Request network;

    @ApiModelProperty(StackModelDescription.ENVIRONMENT)
    private String environmentName;

    @ApiModelProperty(StackModelDescription.CREDENTIAL_NAME)
    private String credentialName;

    @ApiModelProperty(StackModelDescription.FILESYSTEM)
    private FileSystemValidationV1Request fileSystem;

    public Set<HostGroupV1Request> getHostGroups() {
        return hostGroups;
    }

    public void setHostGroups(Set<HostGroupV1Request> hostGroups) {
        this.hostGroups = hostGroups;
    }

    public Set<InstanceGroupV1Request> getInstanceGroups() {
        return instanceGroups;
    }

    public void setInstanceGroups(Set<InstanceGroupV1Request> instanceGroups) {
        this.instanceGroups = instanceGroups;
    }

    public String getBlueprintName() {
        return blueprintName;
    }

    public void setBlueprintName(String blueprintName) {
        this.blueprintName = blueprintName;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public NetworkV1Request getNetwork() {
        return network;
    }

    public void setNetwork(NetworkV1Request network) {
        this.network = network;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public FileSystemValidationV1Request getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystemValidationV1Request fileSystem) {
        this.fileSystem = fileSystem;
    }
}
