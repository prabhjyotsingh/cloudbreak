package com.sequenceiq.environment.credential.v1;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.response.CredentialPrerequisitesResponse;
import com.sequenceiq.environment.api.v1.credential.endpoint.CredentialEndpoint;
import com.sequenceiq.environment.api.v1.credential.model.request.CredentialRequest;
import com.sequenceiq.environment.api.v1.credential.model.response.CredentialResponse;
import com.sequenceiq.environment.api.v1.credential.model.response.CredentialResponses;
import com.sequenceiq.environment.api.v1.credential.model.response.InteractiveCredentialResponse;
import com.sequenceiq.environment.configuration.security.ThreadLocalUserCrnProvider;
import com.sequenceiq.environment.credential.domain.Credential;
import com.sequenceiq.environment.credential.service.CredentialService;
import com.sequenceiq.environment.credential.v1.converter.CredentialToCredentialV1ResponseConverter;
import com.sequenceiq.notification.NotificationController;
import com.sequenceiq.notification.ResourceEvent;

@Component
public class CredentialV1Controller extends NotificationController implements CredentialEndpoint {

    private final CredentialService credentialService;

    private final CredentialToCredentialV1ResponseConverter credentialConverter;

    private final ThreadLocalUserCrnProvider threadLocalUserCrnProvider;

    public CredentialV1Controller(
            CredentialService credentialService,
            CredentialToCredentialV1ResponseConverter credentialConverter,
            ThreadLocalUserCrnProvider threadLocalUserCrnProvider) {
        this.credentialService = credentialService;
        this.credentialConverter = credentialConverter;
        this.threadLocalUserCrnProvider = threadLocalUserCrnProvider;
    }

    @Override
    public CredentialResponses list() {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        return new CredentialResponses(
                credentialService.listAvailablesByAccountId(accountId)
                        .stream()
                        .map(c -> credentialConverter.convert(c))
                        .collect(Collectors.toSet()));
    }

    @Override
    public CredentialResponse get(String credentialName) {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        return credentialConverter.convert(credentialService.getByNameForAccountId(credentialName, accountId));
    }

    @Override
    public CredentialResponse post(@Valid CredentialRequest request) {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        Credential credential = credentialConverter.convert(request);
        notify(ResourceEvent.CREDENTIAL_CREATED);
        return credentialConverter.convert(credentialService.create(credential, accountId));
    }

    @Override
    public CredentialResponse delete(String name) {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        Credential credential = credentialService.deleteByNameFromWorkspace(name, accountId);
        notify(ResourceEvent.CREDENTIAL_DELETED);
        return credentialConverter.convert(credential);
    }

    @Override
    public CredentialResponses deleteMultiple(Set<String> names) {
        // TODO: implement
        Set<Credential> credentials = Set.of();
        notify(ResourceEvent.CREDENTIAL_DELETED);
        return new CredentialResponses(credentials
                .stream()
                .map(c -> credentialConverter.convert(c))
                .collect(Collectors.toSet()));
    }

    @Override
    public CredentialResponse put(@Valid CredentialRequest credentialRequest) {
        Credential credential = credentialService.updateByAccountId(credentialConverter.convert(credentialRequest), threadLocalUserCrnProvider.getAccountId());
        notify(ResourceEvent.CREDENTIAL_MODIFIED);
        return credentialConverter.convert(credential);
    }

    @Override
    public InteractiveCredentialResponse interactiveLogin(@Valid CredentialRequest credentialRequest) {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        Map<String, String> result = credentialService.interactiveLogin(accountId, credentialConverter.convert(credentialRequest));
        return new InteractiveCredentialResponse(result.get("user_code"), result.get("verification_url"));
    }

    @Override
    public CredentialPrerequisitesResponse getPrerequisitesForCloudPlatform(String platform, String deploymentAddress) {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        return credentialService.getPrerequisites(accountId, platform, deploymentAddress);
    }

    @Override
    public Response initCodeGrantFlow(CredentialRequest credentialRequest) {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        String loginURL = credentialService.initCodeGrantFlow(accountId, credentialConverter.convert(credentialRequest));
        return Response.status(Status.FOUND).header("Referrer-Policy", "origin-when-cross-origin").header("Location", loginURL).build();
    }

    @Override
    public Response initCodeGrantFlowOnExisting(String name) {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        String loginURL = credentialService.initCodeGrantFlow(accountId, name);
        return Response.status(Status.FOUND).header("Referrer-Policy", "origin-when-cross-origin").header("Location", loginURL).build();
    }

    @Override
    public CredentialResponse authorizeCodeGrantFlow(String platform, String code, String state) {
        String accountId = threadLocalUserCrnProvider.getAccountId();
        Credential credential = credentialService.authorizeCodeGrantFlow(code, state, accountId, platform);
        notify(ResourceEvent.CREDENTIAL_CREATED);
        return credentialConverter.convert(credential);
    }
}
