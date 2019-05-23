package com.sequenceiq.distrox.api.distrox.response.hardware;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.RecoveryMode;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.HardwareModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.HostGroupModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class HardwareInfoGroupV1Response implements JsonEntity {

    @ApiModelProperty(HostGroupModelDescription.RECOVERY_MODE)
    private RecoveryMode recoveryMode = RecoveryMode.MANUAL;

    private String name;

    @ApiModelProperty(HardwareModelDescription.METADATA)
    private Set<HardwareInfoV1Response> hardwareInfos = new HashSet<>();

    public RecoveryMode getRecoveryMode() {
        return recoveryMode;
    }

    public void setRecoveryMode(RecoveryMode recoveryMode) {
        this.recoveryMode = recoveryMode;
    }

    public Set<HardwareInfoV1Response> getHardwareInfos() {
        return hardwareInfos;
    }

    public void setHardwareInfos(Set<HardwareInfoV1Response> hardwareInfos) {
        this.hardwareInfos = hardwareInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
