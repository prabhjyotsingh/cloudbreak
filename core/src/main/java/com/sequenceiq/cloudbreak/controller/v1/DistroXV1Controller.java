package com.sequenceiq.cloudbreak.controller.v1;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;

import com.sequenceiq.distrox.api.v1.distrox.endpoint.DistroXV1Endpoint;
import com.sequenceiq.distrox.api.distrox.request.DistroXImageChangeV1Request;
import com.sequenceiq.distrox.api.distrox.request.DistroXMaintenanceModeV1Request;
import com.sequenceiq.distrox.api.distrox.request.DistroXRepairV1Request;
import com.sequenceiq.distrox.api.distrox.request.DistroXScaleV1Request;
import com.sequenceiq.distrox.api.distrox.request.DistroXUserNamePasswordV1Request;
import com.sequenceiq.distrox.api.v1.distrox.model.DistroXV1Request;
import com.sequenceiq.distrox.api.distrox.request.UpdateDistroXV1Request;
import com.sequenceiq.distrox.api.distrox.response.DistroXStatusV1Response;
import com.sequenceiq.distrox.api.v1.distrox.model.DistroXV1Response;
import com.sequenceiq.distrox.api.distrox.response.DistroXViewV1Response;
import com.sequenceiq.distrox.api.distrox.response.DistroXViewV1Responses;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.ClusterRepairV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.StackImageChangeV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.StackScaleV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.StackV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.UpdateClusterV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.GeneratedBlueprintV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.StackV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.StackViewV4Response;
import com.sequenceiq.cloudbreak.api.util.ConverterUtil;
import com.sequenceiq.cloudbreak.common.user.CloudbreakUser;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.service.CloudbreakRestRequestThreadLocalService;
import com.sequenceiq.cloudbreak.service.ClusterCommonService;
import com.sequenceiq.cloudbreak.service.StackCommonService;
import com.sequenceiq.cloudbreak.service.stack.StackApiViewService;
import com.sequenceiq.cloudbreak.service.stack.StackService;
import com.sequenceiq.cloudbreak.service.user.UserService;
import com.sequenceiq.cloudbreak.service.workspace.WorkspaceService;
import com.sequenceiq.cloudbreak.workspace.model.User;
import com.sequenceiq.cloudbreak.workspace.model.Workspace;

@Controller
public class DistroXV1Controller implements DistroXV1Endpoint {

    private static long DEFAULT_WORKSPACE = 0L;

    @Inject
    private StackCommonService stackCommonService;

    @Inject
    private UserService userService;

    @Inject
    private CloudbreakRestRequestThreadLocalService restRequestThreadLocalService;

    @Inject
    private WorkspaceService workspaceService;

    @Inject
    private StackService stackService;

    @Inject
    private ClusterCommonService clusterCommonService;

    @Inject
    private ConverterUtil converterUtil;

    @Inject
    private StackApiViewService stackApiViewService;

    @Override
    public DistroXViewV1Responses list(String environment, Boolean onlyDatalakes) {
        Set<StackViewV4Response> stackViewResponses = converterUtil.convertAllAsSet(
                stackApiViewService.retrieveStackViewsByWorkspaceId(DEFAULT_WORKSPACE, environment, onlyDatalakes),
                StackViewV4Response.class
        );
        Set<DistroXViewV1Response> distroXViewV1Responses =
                converterUtil.convertAllAsSet(stackViewResponses, DistroXViewV1Response.class);
        return new DistroXViewV1Responses(distroXViewV1Responses);
    }

    @Override
    public DistroXV1Response post(@Valid DistroXV1Request request) {
        CloudbreakUser cloudbreakUser = restRequestThreadLocalService.getCloudbreakUser();
        User user = userService.getOrCreate(cloudbreakUser);
        Workspace workspace = workspaceService.get(DEFAULT_WORKSPACE, user);
        StackV4Request stackV4Request = converterUtil.convert(request, StackV4Request.class);
        StackV4Response inWorkspace = stackCommonService.createInWorkspace(stackV4Request, user, workspace);
        return converterUtil.convert(inWorkspace, DistroXV1Response.class);
    }

    @Override
    public DistroXV1Response get(String name, Set<String> entries) {
        StackV4Response stackV4Response = stackCommonService.findStackByNameAndWorkspaceId(name, DEFAULT_WORKSPACE, entries);
        return converterUtil.convert(stackV4Response, DistroXV1Response.class);
    }

    @Override
    public void delete(String name, Boolean forced) {
        User user = userService.getOrCreate(restRequestThreadLocalService.getCloudbreakUser());
        stackCommonService.deleteInWorkspace(name, DEFAULT_WORKSPACE, forced, false, user);
    }

    @Override
    public void putSync(String name) {
        stackCommonService.putSyncInWorkspace(name, DEFAULT_WORKSPACE);
    }

    @Override
    public void putRetry(String name) {
        stackCommonService.retryInWorkspace(name, DEFAULT_WORKSPACE);
    }

    @Override
    public void putStop(String name) {
        stackCommonService.putStopInWorkspace(name, DEFAULT_WORKSPACE);
    }

    @Override
    public void putStart(String name) {
        stackCommonService.putStartInWorkspace(name, DEFAULT_WORKSPACE);
    }

    @Override
    public void putScaling(String name, @Valid DistroXScaleV1Request distroXV1Request) {
        StackScaleV4Request stackScaleV4Request = converterUtil.convert(distroXV1Request, StackScaleV4Request.class);
        stackCommonService.putScalingInWorkspace(name, DEFAULT_WORKSPACE, stackScaleV4Request);
    }

    @Override
    public void repairCluster(String name, @Valid DistroXRepairV1Request distroXRepairV1Request) {
        ClusterRepairV4Request clusterRepairRequest = converterUtil.convert(distroXRepairV1Request, ClusterRepairV4Request.class);
        stackCommonService.repairCluster(DEFAULT_WORKSPACE, name, clusterRepairRequest);
    }

    @Override
    public GeneratedBlueprintV4Response postStackForBlueprint(String name, @Valid DistroXV1Request distroXV1Request) {
        StackV4Request stackV4Request = converterUtil.convert(distroXV1Request, StackV4Request.class);
        return stackCommonService.postStackForBlueprint(stackV4Request);
    }

    @Override
    public void changeImage(String name, @Valid DistroXImageChangeV1Request imageChangeRequest) {
        StackImageChangeV4Request stackImageChangeV4Request = converterUtil.convert(imageChangeRequest, StackImageChangeV4Request.class);
        stackCommonService.changeImageByNameInWorkspace(name, DEFAULT_WORKSPACE, stackImageChangeV4Request);
    }

    @Override
    public void deleteWithKerberos(String name) {
        stackCommonService.deleteWithKerbereosInWorkspace(name, DEFAULT_WORKSPACE, true, false);
    }

    @Override
    public DistroXV1Request getRequestfromName(String name) {
        StackV4Request stackV4Request = stackService.getStackRequestByNameInWorkspaceId(name, DEFAULT_WORKSPACE);
        return converterUtil.convert(stackV4Request, DistroXV1Request.class);
    }

    @Override
    public DistroXStatusV1Response getStatusByName(String name) {
        Stack byNameInWorkspace = stackService.getByNameInWorkspace(name, DEFAULT_WORKSPACE);
        return converterUtil.convert(byNameInWorkspace, DistroXStatusV1Response.class);
    }

    @Override
    public void deleteInstance(String name, Boolean forced, String instanceId) {
        stackCommonService.deleteInstanceByNameInWorkspace(name, DEFAULT_WORKSPACE, instanceId, forced);
    }

    @Override
    public void putPassword(String name, @Valid DistroXUserNamePasswordV1Request userNamePasswordJson) {
        Stack stack = stackService.getByNameInWorkspace(name, DEFAULT_WORKSPACE);
        UpdateClusterV4Request updateClusterJson = converterUtil.convert(userNamePasswordJson, UpdateClusterV4Request.class);
        User user = userService.getOrCreate(restRequestThreadLocalService.getCloudbreakUser());
        Workspace workspace = workspaceService.get(restRequestThreadLocalService.getRequestedWorkspaceId(), user);
        clusterCommonService.put(stack.getId(), updateClusterJson, user, workspace);
    }

    @Override
    public void setClusterMaintenanceMode(String name, @NotNull DistroXMaintenanceModeV1Request maintenanceMode) {
        Stack stack = stackService.getByNameInWorkspace(name, DEFAULT_WORKSPACE);
        clusterCommonService.setMaintenanceMode(stack, maintenanceMode.getStatus());
    }

    @Override
    public void putCluster(String name, @Valid UpdateDistroXV1Request updateJson) {
        Stack stack = stackService.getByNameInWorkspace(name, DEFAULT_WORKSPACE);
        User user = userService.getOrCreate(restRequestThreadLocalService.getCloudbreakUser());
        Workspace workspace = workspaceService.get(restRequestThreadLocalService.getRequestedWorkspaceId(), user);
        UpdateClusterV4Request clusterV4Request = converterUtil.convert(updateJson, UpdateClusterV4Request.class);
        clusterCommonService.put(stack.getId(), clusterV4Request, user, workspace);
    }
}
