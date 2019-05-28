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
package com.sequenceiq.cloudbreak.cloud.model;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Logging {

    private final boolean enabled;
    private final LoggingOutputType outputType;
    private final Map<String, Object> attributes;

    public Logging(@JsonProperty("enabled") boolean enabled,
            @JsonProperty("output") LoggingOutputType outputType,
            @JsonProperty("attributess") Map<String, Object> attributes) {
        this.enabled = enabled;
        this.outputType = outputType;
        this.attributes = attributes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LoggingOutputType getOutputType() {
        return outputType;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
