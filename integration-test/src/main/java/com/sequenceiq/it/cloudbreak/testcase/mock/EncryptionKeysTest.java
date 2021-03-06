package com.sequenceiq.it.cloudbreak.testcase.mock;

import static com.sequenceiq.it.cloudbreak.context.RunningParameter.key;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sequenceiq.it.cloudbreak.client.ConnectorTestClient;
import com.sequenceiq.it.cloudbreak.client.CredentialTestClient;
import com.sequenceiq.it.cloudbreak.context.Description;
import com.sequenceiq.it.cloudbreak.context.MockedTestContext;
import com.sequenceiq.it.cloudbreak.context.TestCaseDescription;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.connector.PlatformEncryptionKeysTestDto;
import com.sequenceiq.it.cloudbreak.dto.credential.CredentialTestDto;
import com.sequenceiq.it.cloudbreak.testcase.AbstractIntegrationTest;

public class EncryptionKeysTest extends AbstractIntegrationTest {

    @Inject
    private CredentialTestClient credentialTestClient;

    @Inject
    private ConnectorTestClient connectorTestClient;

    @BeforeMethod
    public void beforeMethod(Object[] data) {
        createDefaultUser((TestContext) data[0]);
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is a prepared MOCK credential with encryption keys",
            when = "calling get encryption keys endpoint",
            then = "the MOCK related encryption keys should be returned")
    public void getPlatformEncryptionKeysWithMockCredentialThenReturnWithPlatformRelatedKeys(MockedTestContext testContext) {
        String credentialName = resourcePropertyProvider().getName();
        testContext
                .given(CredentialTestDto.class)
                .withName(credentialName)
                .when(credentialTestClient.createV4(), key(credentialName))
                .given(PlatformEncryptionKeysTestDto.class)
                .withCredentialName(credentialName)
                .when(connectorTestClient.encryptionKeys(), key(credentialName))
                .validate();
    }

    @Test(dataProvider = "contextWithCredentialNameAndException")
    public void getPlatformEncryptionKeysWithMockCredentialThenReturnWithPlatformRelatedKeys(
            MockedTestContext testContext,
            String credentialName,
            Class<Exception> exception,
            @Description TestCaseDescription testCaseDescription) {
        String generatedKey = resourcePropertyProvider().getName();
        testContext
                .given(PlatformEncryptionKeysTestDto.class)
                .withCredentialName(credentialName)
                .when(connectorTestClient.encryptionKeys(), key(generatedKey))
                .expect(exception, key(generatedKey))
                .validate();
    }

    @DataProvider(name = "contextWithCredentialNameAndException")
    public Object[][] provideInvalidAttributes() {
        return new Object[][]{
                {
                        getBean(MockedTestContext.class),
                        "",
                        BadRequestException.class,
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling get encryption keys endpoint with empty credential name")
                                .then("a BadRequestException should be returned")
                },
                {
                        getBean(MockedTestContext.class),
                        null,
                        BadRequestException.class,
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling get encryption keys endpoint with 'null' credential name")
                                .then("a BadRequestException should be returned")
                },
                {
                        getBean(MockedTestContext.class),
                        "andNowForSomethingCompletelyDifferent",
                        NotFoundException.class,
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling get encryption keys endpoint with a non-existent credential name")
                                .then("a NotFoundException should be returned")
                }
        };
    }

}
