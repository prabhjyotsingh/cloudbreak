package com.sequenceiq.it.cloudbreak.action.v4.environment;

import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentChangeCredentialV4Request;
import com.sequenceiq.it.cloudbreak.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.action.Action;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.environment.EnvironmentTestDto;

public class EnvironmentChangeCredentialAction implements Action<EnvironmentTestDto> {
    @Override
    public EnvironmentTestDto action(TestContext testContext, EnvironmentTestDto testDto, CloudbreakClient cloudbreakClient) throws Exception {
        EnvironmentChangeCredentialV4Request envChangeCredentialRequest = new EnvironmentChangeCredentialV4Request();
        envChangeCredentialRequest.setCredential(testDto.getRequest().getCredential());
        envChangeCredentialRequest.setCredentialName(testDto.getRequest().getCredentialName());
        testDto.setResponse(cloudbreakClient.getCloudbreakClient().environmentV4Endpoint().changeCredential(cloudbreakClient.getWorkspaceId(), testDto.getName(),
                envChangeCredentialRequest)
        );
        return testDto;
    }
}
