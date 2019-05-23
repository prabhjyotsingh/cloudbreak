package com.sequenceiq.distrox.api.distrox.views;


import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.HostGroupModelDescription.METADATA;
import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.NAME;

import java.util.HashSet;
import java.util.Set;

import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;

import io.swagger.annotations.ApiModelProperty;

public class HostGroupViewV1Response implements JsonEntity {

    @ApiModelProperty(value = NAME, required = true)
    private String name;

    @ApiModelProperty(METADATA)
    private Set<HostMetadataViewV1Response> metadata = new HashSet<>();

    public Set<HostMetadataViewV1Response> getMetadata() {
        return metadata;
    }

    public void setMetadata(Set<HostMetadataViewV1Response> metadata) {
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
