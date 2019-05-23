package com.sequenceiq.distrox.api.distrox.response;

import java.util.Collections;
import java.util.Set;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.responses.GeneralCollectionV4Response;

public class DistroXViewV1Responses extends GeneralCollectionV4Response<DistroXViewV1Response> {

    public DistroXViewV1Responses(Set<DistroXViewV1Response> responses) {
        super(responses);
    }

    public DistroXViewV1Responses() {
        super(Collections.emptyList());
    }
}
