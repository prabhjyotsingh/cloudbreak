package com.sequenceiq.freeipa.flow.stack;

import com.sequenceiq.flow.core.CommonContext;
import com.sequenceiq.freeipa.entity.Stack;

public class StackFailureContext extends CommonContext {

    private final Stack stack;

    public StackFailureContext(String flowId, Stack stack) {
        super(flowId);
        this.stack = stack;
    }

    public Stack getStack() {
        return stack;
    }

}
