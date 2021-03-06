package com.sequenceiq.it.cloudbreak.action.v4.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.it.cloudbreak.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.action.Action;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.util.TagSpecificationsTestDto;

public class TagSpecificationsAction implements Action<TagSpecificationsTestDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagSpecificationsAction.class);

    @Override
    public TagSpecificationsTestDto action(TestContext testContext, TagSpecificationsTestDto testDto, CloudbreakClient cloudbreakClient) throws Exception {
        String logInitMessage = "Obtaining tag specifications";
        LOGGER.info("{}", logInitMessage);
        testDto.setResponse(cloudbreakClient.getCloudbreakClient().connectorV4Endpoint().getTagSpecifications(cloudbreakClient.getWorkspaceId()));
        LOGGER.info("{} was successful", logInitMessage);
        return testDto;
    }
}
