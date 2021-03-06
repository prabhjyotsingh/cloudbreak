package com.sequenceiq.it.cloudbreak.testcase.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.testng.annotations.Test;

import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.responses.SimpleEnvironmentV4Response;
import com.sequenceiq.it.cloudbreak.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.assertion.environment.EnvironmentTestAssertion;
import com.sequenceiq.it.cloudbreak.client.CredentialTestClient;
import com.sequenceiq.it.cloudbreak.client.EnvironmentTestClient;
import com.sequenceiq.it.cloudbreak.context.Description;
import com.sequenceiq.it.cloudbreak.context.RunningParameter;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.credential.CredentialTestDto;
import com.sequenceiq.it.cloudbreak.dto.environment.EnvironmentTestDto;
import com.sequenceiq.it.cloudbreak.exception.TestFailException;
import com.sequenceiq.it.cloudbreak.testcase.AbstractIntegrationTest;

public class EnvironmentTest extends AbstractIntegrationTest {

    private static final Set<String> INVALID_REGION = new HashSet<>(Collections.singletonList("MockRegion"));

    @Inject
    private CredentialTestClient credentialTestClient;

    @Inject
    private EnvironmentTestClient environmentTestClient;

    @Override
    protected void setupTest(TestContext testContext) {
        createDefaultUser(testContext);
        createDefaultCredential(testContext);
        initializeDefaultBlueprints(testContext);
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is a running cloudbreak",
            when = "valid create environment request is sent",
            then = "environment should be created")
    public void testCreateEnvironment(TestContext testContext) {
        testContext
                .given(EnvironmentTestDto.class)
                .when(environmentTestClient.createV4())
                .then(this::checkCredentialAttachedToEnv)
                .when(environmentTestClient.listV4())
                .then(this::checkEnvIsListed)
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is a running cloudbreak",
            when = "a create environment request is sent with an invalid region in it",
            then = "a BadRequestException should be returned")
    public void testCreateEnvironmentInvalidRegion(TestContext testContext) {
        String forbiddenKey = resourcePropertyProvider().getName();
        testContext
                .init(EnvironmentTestDto.class)
                .withRegions(INVALID_REGION)
                .when(environmentTestClient.createV4(), RunningParameter.key(forbiddenKey))
                .expect(BadRequestException.class, RunningParameter.key(forbiddenKey))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is a running cloudbreak",
            when = "a create environment request is sent with no region in it",
            then = "a BadRequestException should be returned")
    public void testCreateEnvironmentNoRegion(TestContext testContext) {
        String forbiddenKey = resourcePropertyProvider().getName();
        testContext
                .init(EnvironmentTestDto.class)
                .withRegions(null)
                .when(environmentTestClient.createV4(), RunningParameter.key(forbiddenKey))
                .expect(BadRequestException.class, RunningParameter.key(forbiddenKey))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is a running cloudbreak",
            when = "a create environment request with reference to a non-existing credential is sent",
            then = "a BadRequestException should be returned")
    public void testCreateEnvironmentNotExistCredential(TestContext testContext) {
        String forbiddenKey = resourcePropertyProvider().getName();
        testContext
                .init(EnvironmentTestDto.class)
                .withCredentialName("notexistingcredendital")
                .when(environmentTestClient.createV4(), RunningParameter.key(forbiddenKey))
                .expect(BadRequestException.class, RunningParameter.key(forbiddenKey))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is an available environment",
            when = "a delete request is sent for the environment",
            then = "the environment should be deleted")
    public void testDeleteEnvironment(TestContext testContext) {
        testContext
                .init(EnvironmentTestDto.class)
                .when(environmentTestClient.createV4())
                .then(this::checkCredentialAttachedToEnv)
                .when(environmentTestClient.listV4())
                .then(this::checkEnvIsListed)
                .when(environmentTestClient.deleteV4())
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is a running cloudbreak",
            when = "a delete request is sent for a non-existing environment",
            then = "a NotFoundException should be returned")
    public void testDeleteEnvironmentNotExist(TestContext testContext) {
        String forbiddenKey = resourcePropertyProvider().getName();
        testContext
                .init(EnvironmentTestDto.class)
                .when(environmentTestClient.deleteV4(), RunningParameter.key(forbiddenKey))
                .expect(NotFoundException.class, RunningParameter.key(forbiddenKey))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is an available environment",
            when = "a modifyV4 credential request (with cred name) is sent for the environment",
            then = "the credential should change to the new credential in the environment")
    public void testCreateEnvironmentChangeCredWithCredName(TestContext testContext) {
        String cred1 = resourcePropertyProvider().getName();
        testContext
                .given(EnvironmentTestDto.class)
                .when(environmentTestClient.createV4())
                .given(cred1, CredentialTestDto.class)
                .withName(cred1)
                .when(credentialTestClient.createV4())
                .given(EnvironmentTestDto.class)
                .withCredentialName(cred1)
                .when(environmentTestClient.changeCredential())
                .then(new EnvironmentTestAssertion(cred1))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is an available environment",
            when = "a modifyV4 credential request (with cred request) is sent for the environment",
            then = "the credential should change to the new credential in the environment")
    public void testCreateEnvironmentChangeCredWithCredRequest(TestContext testContext) {
        String cred1 = resourcePropertyProvider().getName();
        testContext
                .given(EnvironmentTestDto.class)
                .when(environmentTestClient.createV4())
                .given(cred1, CredentialTestDto.class)
                .withName(cred1)
                .given(EnvironmentTestDto.class)
                .withCredentialName(null)
                .withCredential(cred1)
                .when(environmentTestClient.changeCredential())
                .then(new EnvironmentTestAssertion(cred1))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is an available environment",
            when = "a modifyV4 credential request is sent for the environment with the same credential",
            then = "the credential should not change in the environment")
    public void testCreateEnvironmentChangeCredForSame(TestContext testContext) {
        testContext
                .given(EnvironmentTestDto.class)
                .when(environmentTestClient.createV4())
                .withCredentialName(testContext.get(CredentialTestDto.class).getName())
                .when(environmentTestClient.changeCredential())
                .then(this::checkCredentialAttachedToEnv)
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is an available environment",
            when = "a modifyV4 credential request is sent for the environment with a non-existing credential",
            then = "a NotFoundException should be returned")
    public void testCreateEnvironmentChangeCredNonExistingName(TestContext testContext) {
        String notExistingCred = resourcePropertyProvider().getName();
        String forbiddenKey = resourcePropertyProvider().getName();
        testContext
                .given(EnvironmentTestDto.class)
                .when(environmentTestClient.createV4())
                .given(EnvironmentTestDto.class)
                .withCredentialName(notExistingCred)
                .when(environmentTestClient.changeCredential(), RunningParameter.key(forbiddenKey))
                .expect(NotFoundException.class, RunningParameter.key(forbiddenKey))
                .validate();
    }

    private EnvironmentTestDto checkCredentialAttachedToEnv(TestContext testContext, EnvironmentTestDto environment, CloudbreakClient cloudbreakClient) {
        String credentialName = environment.getResponse().getCredentialName();
        if (!credentialName.equals(testContext.get(CredentialTestDto.class).getName())) {
            throw new TestFailException("Credential is not attached to environment");
        }
        return environment;
    }

    private EnvironmentTestDto checkEnvIsListed(TestContext testContext, EnvironmentTestDto environment, CloudbreakClient cloudbreakClient) {
        Collection<SimpleEnvironmentV4Response> simpleEnvironmentV4Respons = environment.getResponseSimpleEnvSet();
        List<SimpleEnvironmentV4Response> result = simpleEnvironmentV4Respons.stream()
                .filter(env -> environment.getName().equals(env.getName()))
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new TestFailException("Environment is not listed");
        }
        return environment;
    }
}