package com.sequenceiq.environment.api.v1.environment.model.request;

import com.sequenceiq.environment.api.v1.credential.model.request.CredentialRequest;
import com.sequenceiq.environment.api.doc.environment.EnvironmentModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EnvironmentChangeCredentialV1Request")
public class EnvironmentChangeCredentialRequest implements CredentialAwareEnvRequest {

    @ApiModelProperty(EnvironmentModelDescription.CREDENTIAL_NAME_REQUEST)
    private String credentialName;

    @ApiModelProperty(EnvironmentModelDescription.CREDENTIAL_REQUEST)
    private CredentialRequest credential;

    @Override
    public String getCredentialName() {
        return credentialName;
    }

    @Override
    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    @Override
    public CredentialRequest getCredential() {
        return credential;
    }

    @Override
    public void setCredential(CredentialRequest credential) {
        this.credential = credential;
    }
}
