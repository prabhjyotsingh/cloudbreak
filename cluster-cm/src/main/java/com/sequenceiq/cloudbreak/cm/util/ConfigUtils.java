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
package com.sequenceiq.cloudbreak.cm.util;

import java.util.Map;

import com.cloudera.api.swagger.model.ApiConfig;
import com.cloudera.api.swagger.model.ApiConfigList;

public class ConfigUtils {

    public static ApiConfigList makeApiConfigList(Map<String, String> keyValues) {
        final ApiConfigList configList = new ApiConfigList();
        for (Map.Entry<String, String> entry : keyValues.entrySet()){
            ApiConfig config = makeApiConfig(entry.getKey(), entry.getValue());
            configList.addItemsItem(config);
        }
        return configList;
    }

    public static ApiConfig makeApiConfig(String name, String value) {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setName(name);
        apiConfig.setValue(value);
        return apiConfig;
    }
}
