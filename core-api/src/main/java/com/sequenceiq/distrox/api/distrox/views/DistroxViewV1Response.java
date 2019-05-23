package com.sequenceiq.distrox.api.distrox.views;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.api.endpoint.v4.blueprint.responses.BlueprintV4ViewResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class DistroxViewV1Response implements JsonEntity {
    @ApiModelProperty(ClusterModelDescription.STATUS)
    private Status status;

    @ApiModelProperty(ClusterModelDescription.SECURE)
    private boolean secure;

    @ApiModelProperty(ClusterModelDescription.HOSTGROUPS)
    private Set<HostGroupViewV1Response> hostGroups = new HashSet<>();

    @ApiModelProperty(ClusterModelDescription.KERBEROSCONFIG_NAME)
    private String kerberosName;

    @ApiModelProperty(ClusterModelDescription.BLUEPRINT)
    private BlueprintV4ViewResponse blueprint;

    @ApiModelProperty(ModelDescriptions.StackModelDescription.SERVER_IP)
    private String serverIp;

    @ApiModelProperty(value = ModelDescriptions.NAME)
    private String name;

    @ApiModelProperty(ModelDescriptions.DESCRIPTION)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public Set<HostGroupViewV1Response> getHostGroups() {
        return hostGroups;
    }

    public void setHostGroups(Set<HostGroupViewV1Response> hostGroups) {
        this.hostGroups = hostGroups;
    }

    public String getKerberosName() {
        return kerberosName;
    }

    public void setKerberosName(String kerberosName) {
        this.kerberosName = kerberosName;
    }

    public BlueprintV4ViewResponse getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(BlueprintV4ViewResponse blueprint) {
        this.blueprint = blueprint;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}
