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
package com.sequenceiq.cloudbreak.cm;

import static com.sequenceiq.cloudbreak.cm.util.ConfigUtils.makeApiConfigList;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cloudera.api.swagger.ExternalAccountsResourceApi;
import com.cloudera.api.swagger.client.ApiClient;
import com.cloudera.api.swagger.client.ApiException;
import com.cloudera.api.swagger.model.ApiConfigList;
import com.cloudera.api.swagger.model.ApiExternalAccount;

@Service
public class ClouderaManagerExternalAccountService {

    public ApiExternalAccount createExternalAccount(String accountName, String displayName, String typeName,
            Map<String, String> configs, ApiClient client) throws ApiException {
        ExternalAccountsResourceApi externalAccountsResourceApi = new ExternalAccountsResourceApi(client);
        ApiExternalAccount apiExternalAccount = new ApiExternalAccount();
        apiExternalAccount.setName(accountName);
        apiExternalAccount.setDisplayName(displayName);
        apiExternalAccount.setTypeName(typeName);
        ApiConfigList accountConfigList = makeApiConfigList(configs);
        apiExternalAccount.setAccountConfigs(accountConfigList);
        externalAccountsResourceApi.createAccount(apiExternalAccount);
        return apiExternalAccount;
    }

}
