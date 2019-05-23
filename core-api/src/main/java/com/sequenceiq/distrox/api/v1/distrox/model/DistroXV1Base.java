package com.sequenceiq.distrox.api.v1.distrox.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription;
import com.sequenceiq.distrox.api.distrox.base.parameter.stack.AwsStackV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.stack.AzureStackV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.stack.MockStackV1Parameters;

import io.swagger.annotations.ApiModelProperty;

public abstract class DistroXV1Base implements Serializable {

    @Size(max = 40, min = 5, message = "The length of the name has to be in range of 5 to 40")
    @Pattern(regexp = "(^[a-z][-a-z0-9]*[a-z0-9]$)",
            message = "The name can only contain lowercase alphanumeric characters and hyphens and has start with an alphanumeric character")
    @NotNull
    @ApiModelProperty(value = StackModelDescription.STACK_NAME, required = true)
    private String name;

    @ApiModelProperty(StackModelDescription.AWS_PARAMETERS)
    private AwsStackV1Parameters aws;

    @ApiModelProperty(StackModelDescription.AZURE_PARAMETERS)
    private AzureStackV1Parameters azure;

    @ApiModelProperty(hidden = true)
    private MockStackV1Parameters mock;

    @ApiModelProperty
    private Long timeToLive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAws(AwsStackV1Parameters aws) {
        this.aws = aws;
    }

    public void setAzure(AzureStackV1Parameters azure) {
        this.azure = azure;
    }

    public AwsStackV1Parameters getAws() {
        return aws;
    }

    public AzureStackV1Parameters getAzure() {
        return azure;
    }

    public MockStackV1Parameters getMock() {
        return mock;
    }

    public void setMock(MockStackV1Parameters mock) {
        this.mock = mock;
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }
}
