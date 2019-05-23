package com.sequenceiq.distrox.api.v1.distrox.model.instancegroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.distrox.api.distrox.response.instancegroup.instancemetadata.InstanceMetaDataV1Response;
import com.sequenceiq.distrox.api.distrox.response.instancegroup.securitygroup.SecurityGroupV1Response;
import com.sequenceiq.distrox.api.distrox.response.instancegroup.template.InstanceTemplateV1Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.recipes.responses.RecipeV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceGroupV4Base;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.InstanceGroupModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class InstanceGroupV1Response extends InstanceGroupV4Base {

    @ApiModelProperty(InstanceGroupModelDescription.METADATA)
    private Set<InstanceMetaDataV1Response> metadata = new HashSet<>();

    @ApiModelProperty(InstanceGroupModelDescription.TEMPLATE)
    private InstanceTemplateV1Response template;

    @ApiModelProperty(InstanceGroupModelDescription.SECURITYGROUP)
    private SecurityGroupV1Response securityGroup;

    private List<RecipeV4Response> recipes;

    public Set<InstanceMetaDataV1Response> getMetadata() {
        return metadata;
    }

    public void setMetadata(Set<InstanceMetaDataV1Response> metadata) {
        this.metadata = metadata;
    }

    public InstanceTemplateV1Response getTemplate() {
        return template;
    }

    public void setTemplate(InstanceTemplateV1Response template) {
        this.template = template;
    }

    public SecurityGroupV1Response getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(SecurityGroupV1Response securityGroup) {
        this.securityGroup = securityGroup;
    }

    public List<RecipeV4Response> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeV4Response> recipes) {
        this.recipes = recipes;
    }
}
