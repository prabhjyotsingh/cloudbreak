package com.sequenceiq.datalake.flow.create.handler;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dyngr.exception.PollerException;
import com.dyngr.exception.PollerStoppedException;
import com.dyngr.exception.UserBreakException;
import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.datalake.flow.create.event.StackCreationFailedEvent;
import com.sequenceiq.datalake.flow.create.event.StackCreationSuccessEvent;
import com.sequenceiq.datalake.flow.create.event.StackCreationWaitRequest;
import com.sequenceiq.datalake.service.sdx.PollingConfig;
import com.sequenceiq.datalake.service.sdx.ProvisionerService;
import com.sequenceiq.flow.reactor.api.handler.EventHandler;

import reactor.bus.Event;
import reactor.bus.EventBus;

@Component
public class StackCreationHandler implements EventHandler<StackCreationWaitRequest> {

    public static final int SLEEP_TIME_IN_SEC = 10;

    public static final int DURATION_IN_MINUTES = 60;

    private static final Logger LOGGER = LoggerFactory.getLogger(StackCreationHandler.class);

    @Inject
    private EventBus eventBus;

    @Inject
    private ProvisionerService provisionerService;

    @Override
    public String selector() {
        return "StackCreationWaitRequest";
    }

    @Override
    public void accept(Event<StackCreationWaitRequest> event) {
        StackCreationWaitRequest stackCreationWaitRequest = event.getData();
        Long sdxId = stackCreationWaitRequest.getResourceId();
        Selectable response;
        try {
            LOGGER.debug("start polling stack creation process for id: {}", sdxId);
            PollingConfig pollingConfig = new PollingConfig(SLEEP_TIME_IN_SEC, TimeUnit.SECONDS, DURATION_IN_MINUTES, TimeUnit.MINUTES);
            provisionerService.waitCloudbreakClusterCreation(sdxId, pollingConfig);
            response = new StackCreationSuccessEvent(sdxId);
        } catch (UserBreakException userBreakException) {
            LOGGER.info("Polling exited before timeout. Cause: ", userBreakException);
            response = new StackCreationFailedEvent(sdxId, userBreakException);
        } catch (PollerStoppedException pollerStoppedException) {
            LOGGER.info("Poller stopped for stack: {}", sdxId);
            response = new StackCreationFailedEvent(sdxId, pollerStoppedException);
        } catch (PollerException exception) {
            LOGGER.info("Polling failed for stack: {}", sdxId);
            response = new StackCreationFailedEvent(sdxId, exception);
        }
        eventBus.notify(response.selector(), new Event<>(event.getHeaders(), response));
    }
}
