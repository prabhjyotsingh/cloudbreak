package com.sequenceiq.distrox.api.v1.distrox.model.instancegroup.template;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;
import com.sequenceiq.distrox.api.distrox.base.parameter.template.AwsInstanceTemplateV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.template.AzureInstanceTemplateV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.template.GcpInstanceTemplateV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.template.MockInstanceTemplateV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.template.OpenStackInstanceTemplateV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.template.YarnInstanceTemplateV1Parameters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class InstanceTemplateV1Base implements Serializable {

    @ApiModelProperty(TemplateModelDescription.AWS_PARAMETERS)
    private AwsInstanceTemplateV1Parameters aws;

    @ApiModelProperty(TemplateModelDescription.AZURE_PARAMETERS)
    private AzureInstanceTemplateV1Parameters azure;

    @ApiModelProperty(TemplateModelDescription.GCP_PARAMETERS)
    private GcpInstanceTemplateV1Parameters gcp;

    @ApiModelProperty(TemplateModelDescription.OPENSTACK_PARAMETERS)
    private OpenStackInstanceTemplateV1Parameters openstack;

    @ApiModelProperty(TemplateModelDescription.YARN_PARAMETERS)
    private YarnInstanceTemplateV1Parameters yarn;

    @ApiModelProperty(TemplateModelDescription.YARN_PARAMETERS)
    private MockInstanceTemplateV1Parameters mock;

    @ApiModelProperty(TemplateModelDescription.INSTANCE_TYPE)
    private String instanceType;

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public AwsInstanceTemplateV1Parameters getAws() {
        return aws;
    }

    public void setAws(AwsInstanceTemplateV1Parameters aws) {
        this.aws = aws;
    }

    public void setAzure(AzureInstanceTemplateV1Parameters azure) {
        this.azure = azure;
    }

    public void setGcp(GcpInstanceTemplateV1Parameters gcp) {
        this.gcp = gcp;
    }

    public void setOpenstack(OpenStackInstanceTemplateV1Parameters openstack) {
        this.openstack = openstack;
    }

    public void setYarn(YarnInstanceTemplateV1Parameters yarn) {
        this.yarn = yarn;
    }

    public void setMock(MockInstanceTemplateV1Parameters mock) {
        this.mock = mock;
    }

    public AzureInstanceTemplateV1Parameters getAzure() {
        return azure;
    }

    public GcpInstanceTemplateV1Parameters getGcp() {
        return gcp;
    }

    public OpenStackInstanceTemplateV1Parameters getOpenstack() {
        return openstack;
    }

    public YarnInstanceTemplateV1Parameters getYarn() {
        return yarn;
    }

    public MockInstanceTemplateV1Parameters getMock() {
        return mock;
    }
}
