package com.sequenceiq.cloudbreak.cm.polling.task;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudera.api.swagger.ToolsResourceApi;
import com.cloudera.api.swagger.client.ApiClient;
import com.cloudera.api.swagger.client.ApiException;
import com.cloudera.api.swagger.model.ApiEcho;
import com.sequenceiq.cloudbreak.cluster.service.ClusterBasedStatusCheckerTask;
import com.sequenceiq.cloudbreak.cm.ClouderaManagerOperationFailedException;
import com.sequenceiq.cloudbreak.cm.polling.ClouderaManagerPollerObject;

@Service
public class ClouderaManagerStartupListenerTask extends ClusterBasedStatusCheckerTask<ClouderaManagerPollerObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClouderaManagerStartupListenerTask.class);

    private static final int[] ERROR_CODES = { 502, 503, 504 };

    @Override
    public boolean checkStatus(ClouderaManagerPollerObject clouderaManagerPollerObject) {
        ApiClient apiClient = clouderaManagerPollerObject.getApiClient();
        ToolsResourceApi toolsResourceApi = new ToolsResourceApi(apiClient);
        try {
            String testMessage = "test";
            ApiEcho testIfRunning = toolsResourceApi.echo(testMessage);
            if (testMessage.equals(testIfRunning.getMessage())) {
                return true;
            } else {
                LOGGER.info("test message is different which is strange, returned message: " + testIfRunning.getMessage());
                return false;
            }
        } catch (ApiException e) {
            if (ArrayUtils.contains(ERROR_CODES, e.getCode())) {
                LOGGER.debug("cloudera manager is not running", e);
                return false;
            } else {
                throw new ClouderaManagerOperationFailedException("Cloudera Manager startup failed", e);
            }
        }
    }

    @Override
    public void handleTimeout(ClouderaManagerPollerObject toolsResourceApi) {
        throw new ClouderaManagerOperationFailedException("Operation timed out. Failed to check Cloudera Manager startup.");
    }

    @Override
    public String successMessage(ClouderaManagerPollerObject toolsResourceApi) {
        return "Cloudera Manager startup finished with success result.";
    }
}
