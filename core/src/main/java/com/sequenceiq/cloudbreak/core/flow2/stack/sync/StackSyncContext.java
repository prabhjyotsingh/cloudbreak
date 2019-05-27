package com.sequenceiq.cloudbreak.core.flow2.stack.sync;

import java.util.List;

import com.sequenceiq.cloudbreak.cloud.context.CloudContext;
import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;
import com.sequenceiq.flow.core.CommonContext;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.instance.InstanceMetaData;
import com.sequenceiq.flow.core.FlowParameters;

public class StackSyncContext extends CommonContext {

    private final Stack stack;

    private final List<InstanceMetaData> instanceMetaData;

    private final CloudContext cloudContext;

    private final CloudCredential cloudCredential;

    private final Boolean statusUpdateEnabled;

    public StackSyncContext(FlowParameters flowParameters, Stack stack, List<InstanceMetaData> instanceMetaData, CloudContext cloudContext,
            CloudCredential cloudCredential, Boolean statusUpdateEnabled) {
        super(flowParameters);
        this.stack = stack;
        this.instanceMetaData = instanceMetaData;
        this.cloudContext = cloudContext;
        this.cloudCredential = cloudCredential;
        this.statusUpdateEnabled = statusUpdateEnabled;
    }

    public Stack getStack() {
        return stack;
    }

    public List<InstanceMetaData> getInstanceMetaData() {
        return instanceMetaData;
    }

    public CloudContext getCloudContext() {
        return cloudContext;
    }

    public CloudCredential getCloudCredential() {
        return cloudCredential;
    }

    public Boolean isStatusUpdateEnabled() {
        return statusUpdateEnabled;
    }
}
