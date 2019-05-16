package com.sequenceiq.environment.api.environment.model.response;

public enum EnvironmentStatus {
    CREATION_INITIATED,
    NETWORK_CREATION_IN_PROGRESS,
    RDBMS_CREATION_IN_PROGRESS,
    FREEIPA_CREATION_IN_PROGRESS,
    AVAILABLE,
    ARCHIVED,
    CORRUPTED
}