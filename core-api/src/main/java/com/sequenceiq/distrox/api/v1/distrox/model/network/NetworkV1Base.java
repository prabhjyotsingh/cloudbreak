package com.sequenceiq.distrox.api.v1.distrox.model.network;

import java.io.Serializable;

import com.sequenceiq.cloudbreak.doc.ModelDescriptions.NetworkModelDescription;
import com.sequenceiq.distrox.api.distrox.base.parameter.network.AwsNetworkV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.network.AzureNetworkV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.network.GcpNetworkV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.network.MockNetworkV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.network.OpenStackNetworkV1Parameters;
import com.sequenceiq.distrox.api.distrox.base.parameter.network.YarnNetworkV1Parameters;

import io.swagger.annotations.ApiModelProperty;

public class NetworkV1Base implements Serializable {

    @ApiModelProperty(NetworkModelDescription.AWS_PARAMETERS)
    private AwsNetworkV1Parameters aws;

    @ApiModelProperty(NetworkModelDescription.GCP_PARAMETERS)
    private GcpNetworkV1Parameters gcp;

    @ApiModelProperty(NetworkModelDescription.AZURE_PARAMETERS)
    private AzureNetworkV1Parameters azure;

    @ApiModelProperty(NetworkModelDescription.OPEN_STACK_PARAMETERS)
    private OpenStackNetworkV1Parameters openstack;

    @ApiModelProperty(hidden = true)
    private MockNetworkV1Parameters mock;

    @ApiModelProperty(hidden = true)
    private YarnNetworkV1Parameters yarn;

    public void setMock(MockNetworkV1Parameters mock) {
        this.mock = mock;
    }

    public void setAws(AwsNetworkV1Parameters aws) {
        this.aws = aws;
    }

    public void setGcp(GcpNetworkV1Parameters gcp) {
        this.gcp = gcp;
    }

    public void setAzure(AzureNetworkV1Parameters azure) {
        this.azure = azure;
    }

    public void setOpenstack(OpenStackNetworkV1Parameters openstack) {
        this.openstack = openstack;
    }

    public void setYarn(YarnNetworkV1Parameters yarn) {
        this.yarn = yarn;
    }

    public AwsNetworkV1Parameters getAws() {
        return aws;
    }

    public GcpNetworkV1Parameters getGcp() {
        return gcp;
    }

    public AzureNetworkV1Parameters getAzure() {
        return azure;
    }

    public OpenStackNetworkV1Parameters getOpenstack() {
        return openstack;
    }

    public MockNetworkV1Parameters getMock() {
        return mock;
    }

    public YarnNetworkV1Parameters getYarn() {
        return yarn;
    }
}