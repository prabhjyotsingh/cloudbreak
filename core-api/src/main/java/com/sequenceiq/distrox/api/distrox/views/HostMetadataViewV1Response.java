package com.sequenceiq.distrox.api.distrox.views;

import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.HostMetadataModelDescription.STATE;
import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.NAME;

import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;

import io.swagger.annotations.ApiModelProperty;

public class HostMetadataViewV1Response implements JsonEntity {

    @ApiModelProperty(value = NAME, required = true)
    private String name;

    @ApiModelProperty(STATE)
    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
