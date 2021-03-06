package com.sequenceiq.cloudbreak.reactor.handler.cluster;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.cluster.host.HostMetadata;
import com.sequenceiq.flow.event.EventSelectorUtil;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.ClusterStartRequest;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.ClusterStartResult;
import com.sequenceiq.flow.reactor.api.handler.EventHandler;
import com.sequenceiq.cloudbreak.service.cluster.ClusterApiConnectors;
import com.sequenceiq.cloudbreak.service.hostmetadata.HostMetadataService;
import com.sequenceiq.cloudbreak.service.stack.StackService;

import reactor.bus.Event;
import reactor.bus.EventBus;

@Component
public class ClusterStartHandler implements EventHandler<ClusterStartRequest> {
    @Inject
    private ClusterApiConnectors apiConnectors;

    @Inject
    private StackService stackService;

    @Inject
    private EventBus eventBus;

    @Inject
    private HostMetadataService hostMetadataService;

    @Override
    public String selector() {
        return EventSelectorUtil.selector(ClusterStartRequest.class);
    }

    @Override
    public void accept(Event<ClusterStartRequest> event) {
        ClusterStartRequest request = event.getData();
        ClusterStartResult result;
        try {
            Stack stack = stackService.getByIdWithListsInTransaction(request.getResourceId());
            Set<HostMetadata> hostsInCluster = hostMetadataService.findHostsInCluster(stack.getCluster().getId());
            int requestId = apiConnectors.getConnector(stack).startCluster(hostsInCluster);
            result = new ClusterStartResult(request, requestId);
        } catch (Exception e) {
            result = new ClusterStartResult(e.getMessage(), e, request);
        }
        eventBus.notify(result.selector(), new Event<>(event.getHeaders(), result));
    }
}
