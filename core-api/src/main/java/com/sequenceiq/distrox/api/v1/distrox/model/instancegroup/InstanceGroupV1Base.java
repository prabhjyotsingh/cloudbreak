package com.sequenceiq.distrox.api.v1.distrox.model.instancegroup;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.RecoveryMode;
import com.sequenceiq.cloudbreak.common.type.InstanceGroupType;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.HostGroupModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.InstanceGroupModelDescription;
import com.sequenceiq.distrox.api.distrox.base.parameter.instancegroup.AwsInstanceGroupV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.instancegroup.AzureInstanceGroupV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.instancegroup.GcpInstanceGroupV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.instancegroup.MockInstanceGroupV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.instancegroup.OpenStackInstanceGroupV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.instancegroup.YarnInstanceGroupV1Parameters;

import io.swagger.annotations.ApiModelProperty;

public class InstanceGroupV1Base implements Serializable {

    @Min(value = 0, message = "The node count has to be greater or equals than 0")
    @Max(value = 100000, message = "The node count has to be less than 100000")
    @Digits(fraction = 0, integer = 10, message = "The node count has to be a number")
    @ApiModelProperty(value = InstanceGroupModelDescription.NODE_COUNT, required = true)
    private int nodeCount;

    @NotNull
    @ApiModelProperty(value = InstanceGroupModelDescription.INSTANCE_GROUP_NAME, required = true)
    private String name;

    @ApiModelProperty(value = InstanceGroupModelDescription.INSTANCE_GROUP_TYPE, allowableValues = "CORE,GATEWAY")
    private InstanceGroupType type = InstanceGroupType.CORE;

    @ApiModelProperty(InstanceGroupModelDescription.AZURE_PARAMETERS)
    private AzureInstanceGroupV1Parameters azure;

    @ApiModelProperty(value = InstanceGroupModelDescription.GCP_PARAMETERS, hidden = true)
    private GcpInstanceGroupV1Parameters gcp;

    @ApiModelProperty(InstanceGroupModelDescription.AWS_PARAMETERS)
    private AwsInstanceGroupV1Parameters aws;

    @ApiModelProperty(value = InstanceGroupModelDescription.OPENSTACK_PARAMETERS, hidden = true)
    private OpenStackInstanceGroupV1Parameters openstack;

    @ApiModelProperty(hidden = true)
    private YarnInstanceGroupV1Parameters yarn;

    @ApiModelProperty(hidden = true)
    private MockInstanceGroupV1Parameters mock;

    @ApiModelProperty(value = HostGroupModelDescription.RECOVERY_MODE, allowableValues = "MANUAL,AUTO")
    private RecoveryMode recoveryMode = RecoveryMode.MANUAL;

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InstanceGroupType getType() {
        return type;
    }

    public void setType(InstanceGroupType type) {
        this.type = type;
    }

    public void setAzure(AzureInstanceGroupV1Parameters azure) {
        this.azure = azure;
    }

    public void setGcp(GcpInstanceGroupV1Parameters gcp) {
        this.gcp = gcp;
    }

    public void setAws(AwsInstanceGroupV1Parameters aws) {
        this.aws = aws;
    }

    public void setOpenstack(OpenStackInstanceGroupV1Parameters openstack) {
        this.openstack = openstack;
    }

    public void setYarn(YarnInstanceGroupV1Parameters yarn) {
        this.yarn = yarn;
    }

    public void setMock(MockInstanceGroupV1Parameters mock) {
        this.mock = mock;
    }

    public AzureInstanceGroupV1Parameters getAzure() {
        return azure;
    }

    public GcpInstanceGroupV1Parameters getGcp() {
        return gcp;
    }

    public AwsInstanceGroupV1Parameters getAws() {
        return aws;
    }

    public OpenStackInstanceGroupV1Parameters getOpenstack() {
        return openstack;
    }

    public YarnInstanceGroupV1Parameters getYarn() {
        return yarn;
    }

    public MockInstanceGroupV1Parameters getMock() {
        return mock;
    }

    public RecoveryMode getRecoveryMode() {
        return recoveryMode;
    }

    public void setRecoveryMode(RecoveryMode recoveryMode) {
        this.recoveryMode = recoveryMode;
    }
}
