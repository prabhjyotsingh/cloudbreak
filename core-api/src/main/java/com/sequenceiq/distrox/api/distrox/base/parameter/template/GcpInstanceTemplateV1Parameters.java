package com.sequenceiq.distrox.api.distrox.base.parameter.template;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceTemplateV4ParameterBase;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.KeyEncryptionMethod;
import com.sequenceiq.cloudbreak.common.type.EncryptionType;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class GcpInstanceTemplateV1Parameters extends InstanceTemplateV4ParameterBase {

    @ApiModelProperty(TemplateModelDescription.ENCRYPTION)
    private GcpEncryptionV1Parameters encryption;

    @ApiModelProperty
    private Boolean preemptible;

    public GcpEncryptionV1Parameters getEncryption() {
        return encryption;
    }

    public void setEncryption(GcpEncryptionV1Parameters encryption) {
        this.encryption = encryption;
    }

    public Boolean getPreemptible() {
        return preemptible;
    }

    public void setPreemptible(Boolean preemptible) {
        this.preemptible = preemptible;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = super.asMap();
        if (encryption != null) {
            putIfValueNotNull(map, "keyEncryptionMethod", encryption.getKeyEncryptionMethod());
            putIfValueNotNull(map, "type", encryption.getType());
        }
        putIfValueNotNull(map, "preemptible", preemptible);
        return map;
    }

    @Override
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public CloudPlatform getCloudPlatform() {
        return CloudPlatform.GCP;
    }

    @Override
    public Map<String, Object> asSecretMap() {
        Map<String, Object> secretMap = super.asSecretMap();
        if (encryption != null) {
            secretMap.put("key", encryption.getKey());
        }
        return secretMap;
    }

    @Override
    public void parse(Map<String, Object> parameters) {
        GcpEncryptionV1Parameters encryption = new GcpEncryptionV1Parameters();
        encryption.setKey(getParameterOrNull(parameters, "key"));
        String keyEncryptionMethod = getParameterOrNull(parameters, "keyEncryptionMethod");
        if (keyEncryptionMethod != null) {
            encryption.setKeyEncryptionMethod(KeyEncryptionMethod.valueOf(keyEncryptionMethod));
        }
        String type = getParameterOrNull(parameters, "type");
        if (type != null) {
            encryption.setType(EncryptionType.valueOf(type));
            this.encryption = encryption;
        }
        String preemptible = getParameterOrNull(parameters, "preemptible");
        if (preemptible != null) {
            this.preemptible = Boolean.valueOf(preemptible);
        }
    }
}
