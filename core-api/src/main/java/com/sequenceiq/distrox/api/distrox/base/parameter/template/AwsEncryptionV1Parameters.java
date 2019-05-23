package com.sequenceiq.distrox.api.distrox.base.parameter.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.distrox.api.distrox.base.parameter.EncryptionParametersV1Base;

import io.swagger.annotations.ApiModel;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class AwsEncryptionV1Parameters extends EncryptionParametersV1Base {

}
