package com.sequenceiq.distrox.api.distrox.response.cluster.storage.s3;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.sequenceiq.distrox.api.distrox.response.cluster.storage.CloudStorageParametersV1Response;
import com.sequenceiq.cloudbreak.common.type.filesystem.FileSystemType;
import com.sequenceiq.cloudbreak.validation.ValidS3CloudStorageParameters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@ValidS3CloudStorageParameters
public class S3CloudStorageParametersV1Response implements CloudStorageParametersV1Response {

    @ApiModelProperty
    @NotNull
    private String instanceProfile;

    @ApiModelProperty(hidden = true)
    @Override
    public FileSystemType getType() {
        return FileSystemType.S3;
    }

    public String getInstanceProfile() {
        return instanceProfile;
    }

    public void setInstanceProfile(String instanceProfile) {
        this.instanceProfile = instanceProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof S3CloudStorageParametersV1Response)) {
            return false;
        }
        S3CloudStorageParametersV1Response that = (S3CloudStorageParametersV1Response) o;
        return Objects.equals(instanceProfile, that.instanceProfile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceProfile);
    }

}
