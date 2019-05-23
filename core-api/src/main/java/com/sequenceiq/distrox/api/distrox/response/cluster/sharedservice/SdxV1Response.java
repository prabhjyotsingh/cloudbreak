package com.sequenceiq.distrox.api.distrox.response.cluster.sharedservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;

import io.swagger.annotations.ApiModel;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SdxV1Response implements JsonEntity {

    private String sdxCrn;

    public String getSdxCrn() {
        return sdxCrn;
    }

    public void setSdxCrn(String sdxCrn) {
        this.sdxCrn = sdxCrn;
    }
}
