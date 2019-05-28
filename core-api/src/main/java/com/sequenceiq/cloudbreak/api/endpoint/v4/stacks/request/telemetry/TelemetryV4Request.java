/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.telemetry;

import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.telemetry.logging.LoggingV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.telemetry.workload.WorkloadAnalyticsV4Request;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TelemetryV4Request implements JsonEntity {

    @ApiModelProperty(ModelDescriptions.StackModelDescription.TELEMETRY_LOGGING)
    private LoggingV4Request logging;

    @ApiModelProperty(ModelDescriptions.StackModelDescription.TELEMETRY_WA)
    private WorkloadAnalyticsV4Request workloadAnalytics;

    public LoggingV4Request getLogging() {
        return logging;
    }

    public void setLogging(LoggingV4Request logging) {
        this.logging = logging;
    }

    public WorkloadAnalyticsV4Request getWorkloadAnalytics() {
        return workloadAnalytics;
    }

    public void setWorkloadAnalytics(WorkloadAnalyticsV4Request workloadAnalytics) {
        this.workloadAnalytics = workloadAnalytics;
    }
}
