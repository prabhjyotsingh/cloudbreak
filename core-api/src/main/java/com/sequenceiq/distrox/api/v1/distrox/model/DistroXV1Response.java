package com.sequenceiq.distrox.api.v1.distrox.model;

import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription.PLACEMENT_SETTINGS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.distrox.api.distrox.response.DistroXDetailsV1Response;
import com.sequenceiq.distrox.api.v1.distrox.model.authentication.StackAuthenticationV1Response;
import com.sequenceiq.distrox.api.distrox.response.cluster.DistroXClusterV1Response;
import com.sequenceiq.distrox.api.distrox.response.cluster.sharedservice.SdxV1Response;
import com.sequenceiq.distrox.api.distrox.response.customdomain.CustomDomainSettingsV1Response;
import com.sequenceiq.distrox.api.distrox.response.environment.EnvironmentSettingsV4Response;
import com.sequenceiq.distrox.api.distrox.response.environment.placement.PlacementSettingsV1Response;
import com.sequenceiq.distrox.api.distrox.response.hardware.HardwareInfoGroupV1Response;
import com.sequenceiq.distrox.api.v1.distrox.model.image.DistroXImageV1Response;
import com.sequenceiq.distrox.api.v1.distrox.model.instancegroup.InstanceGroupV1Response;
import com.sequenceiq.distrox.api.v1.distrox.model.network.NetworkV1Response;
import com.sequenceiq.distrox.api.distrox.response.tags.TagsV1Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status;
import com.sequenceiq.cloudbreak.api.endpoint.v4.events.responses.CloudbreakEventV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.workspace.responses.WorkspaceResourceV4Response;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class DistroXV1Response extends DistroXV1Base {

    @ApiModelProperty(StackModelDescription.STACK_ID)
    private Long id;

    private EnvironmentSettingsV4Response environment;

    @ApiModelProperty(StackModelDescription.STACK_STATUS)
    private Status status;

    @ApiModelProperty(StackModelDescription.CLUSTER)
    private DistroXClusterV1Response cluster;

    @ApiModelProperty(StackModelDescription.STATUS_REASON)
    private String statusReason;

    @ApiModelProperty(StackModelDescription.NETWORK)
    private NetworkV1Response network;

    @Valid
    @ApiModelProperty
    private List<InstanceGroupV1Response> instanceGroups = new ArrayList<>();

    @ApiModelProperty(StackModelDescription.CREATED)
    private Long created;

    @ApiModelProperty(StackModelDescription.TERMINATED)
    private Long terminated;

    @ApiModelProperty(StackModelDescription.GATEWAY_PORT)
    private Integer gatewayPort;

    @ApiModelProperty(StackModelDescription.IMAGE)
    private DistroXImageV1Response image;

    @ApiModelProperty(StackModelDescription.CLOUDBREAK_DETAILS)
    private DistroXDetailsV1Response cloudbreakDetails;

    @ApiModelProperty(StackModelDescription.AUTHENTICATION)
    private StackAuthenticationV1Response authentication;

    @ApiModelProperty(StackModelDescription.NODE_COUNT)
    private Integer nodeCount;

    @ApiModelProperty(StackModelDescription.HARDWARE_INFO_RESPONSE)
    private Set<HardwareInfoGroupV1Response> hardwareInfoGroups = new HashSet<>();

    @ApiModelProperty(StackModelDescription.EVENTS)
    private List<CloudbreakEventV4Response> cloudbreakEvents = new ArrayList<>();

    @ApiModelProperty(StackModelDescription.TAGS)
    private TagsV1Response tags;

    @ApiModelProperty(ModelDescriptions.WORKSPACE_OF_THE_RESOURCE)
    private WorkspaceResourceV4Response workspace;

    @ApiModelProperty
    private CustomDomainSettingsV1Response customDomains;

    @NotNull
    @ApiModelProperty(value = PLACEMENT_SETTINGS, required = true)
    @Valid
    private PlacementSettingsV1Response placement;

    @ApiModelProperty(ClusterModelDescription.SHARED_SERVICE_REQUEST)
    private SdxV1Response sharedService;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DistroXClusterV1Response getCluster() {
        return cluster;
    }

    public void setCluster(DistroXClusterV1Response cluster) {
        this.cluster = cluster;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public NetworkV1Response getNetwork() {
        return network;
    }

    public void setNetwork(NetworkV1Response network) {
        this.network = network;
    }

    public List<InstanceGroupV1Response> getInstanceGroups() {
        return instanceGroups;
    }

    public void setInstanceGroups(List<InstanceGroupV1Response> instanceGroups) {
        this.instanceGroups = instanceGroups;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getTerminated() {
        return terminated;
    }

    public void setTerminated(Long terminated) {
        this.terminated = terminated;
    }

    public Integer getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(Integer gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    public DistroXImageV1Response getImage() {
        return image;
    }

    public void setImage(DistroXImageV1Response image) {
        this.image = image;
    }

    public DistroXDetailsV1Response getCloudbreakDetails() {
        return cloudbreakDetails;
    }

    public void setCloudbreakDetails(DistroXDetailsV1Response cloudbreakDetails) {
        this.cloudbreakDetails = cloudbreakDetails;
    }

    public StackAuthenticationV1Response getAuthentication() {
        return authentication;
    }

    public void setAuthentication(StackAuthenticationV1Response authentication) {
        this.authentication = authentication;
    }

    public Integer getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    public Set<HardwareInfoGroupV1Response> getHardwareInfoGroups() {
        return hardwareInfoGroups;
    }

    public void setHardwareInfoGroups(Set<HardwareInfoGroupV1Response> hardwareInfoGroups) {
        this.hardwareInfoGroups = hardwareInfoGroups;
    }

    public List<CloudbreakEventV4Response> getCloudbreakEvents() {
        return cloudbreakEvents;
    }

    public void setCloudbreakEvents(List<CloudbreakEventV4Response> cloudbreakEvents) {
        this.cloudbreakEvents = cloudbreakEvents;
    }

    public WorkspaceResourceV4Response getWorkspace() {
        return workspace;
    }

    public void setWorkspace(WorkspaceResourceV4Response workspace) {
        this.workspace = workspace;
    }

    public EnvironmentSettingsV4Response getEnvironment() {
        return environment;
    }

    public void setEnvironment(EnvironmentSettingsV4Response environment) {
        this.environment = environment;
    }

    public TagsV1Response getTags() {
        return tags;
    }

    public void setTags(TagsV1Response tags) {
        this.tags = tags;
    }

    public CustomDomainSettingsV1Response getCustomDomains() {
        return customDomains;
    }

    public void setCustomDomains(CustomDomainSettingsV1Response customDomains) {
        this.customDomains = customDomains;
    }

    public SdxV1Response getSharedService() {
        return sharedService;
    }

    public void setSharedService(SdxV1Response sharedService) {
        this.sharedService = sharedService;
    }

    public PlacementSettingsV1Response getPlacement() {
        return placement;
    }

    public void setPlacement(PlacementSettingsV1Response placement) {
        this.placement = placement;
    }
}
