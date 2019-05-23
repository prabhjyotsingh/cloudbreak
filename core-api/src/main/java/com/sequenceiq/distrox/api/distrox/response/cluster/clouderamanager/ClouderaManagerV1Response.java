package com.sequenceiq.distrox.api.distrox.response.cluster.clouderamanager;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClouderaManagerV1Response implements JsonEntity {

    @Valid
    @ApiModelProperty(ModelDescriptions.ClusterModelDescription.CM_REPO_DETAILS)
    private ClouderaManagerRepositoryV1Response repository;

    @Valid
    @ApiModelProperty(ModelDescriptions.ClusterModelDescription.CM_PRODUCT_DETAILS)
    private List<ClouderaManagerProductV1Response> products;

    public ClouderaManagerRepositoryV1Response getRepository() {
        return repository;
    }

    public void setRepository(ClouderaManagerRepositoryV1Response repository) {
        this.repository = repository;
    }

    public List<ClouderaManagerProductV1Response> getProducts() {
        return products;
    }

    public void setProducts(List<ClouderaManagerProductV1Response> products) {
        this.products = products;
    }

    public ClouderaManagerV1Response withRepository(ClouderaManagerRepositoryV1Response repository) {
        setRepository(repository);
        return this;
    }

    public ClouderaManagerV1Response withProducts(List<ClouderaManagerProductV1Response> products) {
        setProducts(products);
        return this;
    }
}
