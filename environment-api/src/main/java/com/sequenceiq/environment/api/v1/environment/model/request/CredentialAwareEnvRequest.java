package com.sequenceiq.environment.api.v1.environment.model.request;

import com.sequenceiq.environment.api.v1.credential.model.request.CredentialRequest;

public interface CredentialAwareEnvRequest {

    String getCredentialName();

    void setCredentialName(String credentialName);

    CredentialRequest getCredential();

    void setCredential(CredentialRequest credential);
}
