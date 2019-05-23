package com.sequenceiq.distrox.api.distrox.request;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.distrox.api.distrox.request.cluster.repository.RepositoryV1Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.StatusRequest;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateDistroXV1Request implements JsonEntity {

    @ApiModelProperty(ClusterModelDescription.HOSTGROUP_ADJUSTMENT)
    private HostGroupAdjustmentV1Request hostGroupAdjustment;

    @ApiModelProperty(value = ClusterModelDescription.STATUS_REQUEST, allowableValues = "SYNC,FULL_SYNC,REPAIR_FAILED_NODES,STOPPED,STARTED")
    private StatusRequest status;

    @ApiModelProperty(ClusterModelDescription.USERNAME_PASSWORD)
    private DistroXUserNamePasswordV1Request userNamePassword;

    @ApiModelProperty(ClusterModelDescription.BLUEPRINT_ID)
    private String blueprintName;

    @ApiModelProperty(ClusterModelDescription.HOSTGROUPS)
    private Set<HostGroupV1Request> hostgroups;

    @Valid
    @ApiModelProperty(ClusterModelDescription.AMBARI_STACK_DETAILS)
    private RepositoryV1Request repository;

    @ApiModelProperty(StackModelDescription.KERBEROS_PASSWORD)
    @Size(max = 50, min = 5, message = "The length of the Kerberos password has to be in range of 5 to 50")
    private String kerberosPassword;

    @ApiModelProperty(StackModelDescription.KERBEROS_PRINCIPAL)
    private String kerberosPrincipal;

    public HostGroupAdjustmentV1Request getHostGroupAdjustment() {
        return hostGroupAdjustment;
    }

    public void setHostGroupAdjustment(HostGroupAdjustmentV1Request hostGroupAdjustment) {
        this.hostGroupAdjustment = hostGroupAdjustment;
    }

    public StatusRequest getStatus() {
        return status;
    }

    public void setStatus(StatusRequest status) {
        this.status = status;
    }

    public DistroXUserNamePasswordV1Request getUserNamePassword() {
        return userNamePassword;
    }

    public void setUserNamePassword(DistroXUserNamePasswordV1Request userNamePassword) {
        this.userNamePassword = userNamePassword;
    }

    public String getBlueprintName() {
        return blueprintName;
    }

    public void setBlueprintName(String blueprintName) {
        this.blueprintName = blueprintName;
    }

    public Set<HostGroupV1Request> getHostgroups() {
        return hostgroups;
    }

    public void setHostgroups(Set<HostGroupV1Request> hostgroups) {
        this.hostgroups = hostgroups;
    }

    public String getKerberosPassword() {
        return kerberosPassword;
    }

    public void setKerberosPassword(String kerberosPassword) {
        this.kerberosPassword = kerberosPassword;
    }

    public String getKerberosPrincipal() {
        return kerberosPrincipal;
    }

    public void setKerberosPrincipal(String kerberosPrincipal) {
        this.kerberosPrincipal = kerberosPrincipal;
    }

    public RepositoryV1Request getRepository() {
        return repository;
    }

    public void setRepository(RepositoryV1Request repository) {
        this.repository = repository;
    }
}
