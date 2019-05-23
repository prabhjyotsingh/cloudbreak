package com.sequenceiq.distrox.api.distrox.base.parameter.template;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.distrox.api.distrox.base.InstanceTemplateV1ParameterBase;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.common.type.EncryptionType;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AwsInstanceTemplateV1Parameters extends InstanceTemplateV1ParameterBase {

    @ApiModelProperty(TemplateModelDescription.AWS_SPOT_PRICE)
    private Double spotPrice;

    @ApiModelProperty(TemplateModelDescription.ENCRYPTION)
    private AwsEncryptionV1Parameters encryption;

    public AwsEncryptionV1Parameters getEncryption() {
        return encryption;
    }

    public void setEncryption(AwsEncryptionV1Parameters encryption) {
        this.encryption = encryption;
    }

    public Double getSpotPrice() {
        return spotPrice;
    }

    public void setSpotPrice(Double spotPrice) {
        this.spotPrice = spotPrice;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = super.asMap();
        putIfValueNotNull(map, "spotPrice", spotPrice);
        if (encryption != null) {
            putIfValueNotNull(map, "type", encryption.getType());
            putIfValueNotNull(map, "encrypted", encryption.getType() != EncryptionType.NONE);
        }
        return map;
    }

    @Override
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public CloudPlatform getCloudPlatform() {
        return CloudPlatform.AWS;
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
        String spotPrice = getParameterOrNull(parameters, "spotPrice");
        if (spotPrice != null) {
            this.spotPrice = Double.parseDouble(spotPrice);
        }
        AwsEncryptionV1Parameters encription = new AwsEncryptionV1Parameters();
        encription.setKey(getParameterOrNull(parameters, "key"));
        String type = getParameterOrNull(parameters, "type");
        if (type != null) {
            encription.setType(EncryptionType.valueOf(type));
        }
    }
}
