package com.sequenceiq.distrox.api.distrox.response.cluster.storage.gcs;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.sequenceiq.distrox.api.distrox.response.cluster.storage.CloudStorageParametersV1Response;
import com.sequenceiq.cloudbreak.common.type.filesystem.FileSystemType;
import com.sequenceiq.cloudbreak.validation.ValidGcsCloudStorageParameters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@ValidGcsCloudStorageParameters
public class GcsCloudStorageParametersV1Response implements CloudStorageParametersV1Response {

    @ApiModelProperty
    @NotNull
    private String serviceAccountEmail;

    public String getServiceAccountEmail() {
        return serviceAccountEmail;
    }

    public void setServiceAccountEmail(String serviceAccountEmail) {
        this.serviceAccountEmail = serviceAccountEmail;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public FileSystemType getType() {
        return FileSystemType.GCS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GcsCloudStorageParametersV1Response)) {
            return false;
        }
        GcsCloudStorageParametersV1Response that = (GcsCloudStorageParametersV1Response) o;
        return Objects.equals(serviceAccountEmail, that.serviceAccountEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceAccountEmail);
    }

}
