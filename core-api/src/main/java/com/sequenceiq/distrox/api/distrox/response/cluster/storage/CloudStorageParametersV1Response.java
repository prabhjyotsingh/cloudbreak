package com.sequenceiq.distrox.api.distrox.response.cluster.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.common.type.filesystem.FileSystemType;

@JsonIgnoreProperties(ignoreUnknown = true, value = "type")
public interface CloudStorageParametersV1Response extends JsonEntity {

    FileSystemType getType();

}
