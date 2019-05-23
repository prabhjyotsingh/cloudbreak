package com.sequenceiq.distrox.api.distrox.response.cluster.storage;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.distrox.api.distrox.response.cluster.storage.location.StorageLocationV1Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.CloudStorageV4Base;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.FileSystem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CloudStorageV1Response extends CloudStorageV4Base {

    @Valid
    @ApiModelProperty(ClusterModelDescription.LOCATIONS)
    private Set<StorageLocationV1Response> locations = new HashSet<>();

    @NotNull
    @ApiModelProperty(value = FileSystem.NAME, required = true)
    private String name;

    @ApiModelProperty(value = FileSystem.TYPE, required = true)
    private String type;

    public Set<StorageLocationV1Response> getLocations() {
        return locations;
    }

    public void setLocations(Set<StorageLocationV1Response> locations) {
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
