package com.sequenceiq.distrox.api.distrox.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.distrox.api.distrox.base.parameter.MappableBase;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class InstanceTemplateV1ParameterBase extends MappableBase implements JsonEntity {
}
