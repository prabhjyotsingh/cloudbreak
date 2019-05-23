package com.sequenceiq.distrox.api.distrox.base.parameter.instancegroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AzureInstanceGroupV1Parameters extends InstanceGroupV1ParametersBase {

    @ApiModelProperty
    private AzureAvailabiltySetV1 availabilitySet;

    public AzureAvailabiltySetV1 getAvailabilitySet() {
        return availabilitySet;
    }

    public void setAvailabilitySet(AzureAvailabiltySetV1 availabilitySet) {
        this.availabilitySet = availabilitySet;
    }
}
