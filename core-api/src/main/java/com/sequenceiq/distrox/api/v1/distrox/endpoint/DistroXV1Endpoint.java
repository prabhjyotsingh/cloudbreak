package com.sequenceiq.distrox.api.v1.distrox.endpoint;

import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.ClusterOpDescription.PUT_BY_STACK_ID;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.ClusterOpDescription.SET_MAINTENANCE_MODE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.CHECK_IMAGE_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.CREATE_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.DELETE_BY_NAME_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.DELETE_INSTANCE_BY_ID_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.GET_BY_NAME_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.GET_STACK_REQUEST_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.GET_STATUS_BY_NAME;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.LIST_BY_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.POST_STACK_FOR_BLUEPRINT_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.PUT_BY_NAME;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.REPAIR_CLUSTER_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.RETRY_BY_NAME_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.SCALE_BY_NAME_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.START_BY_NAME_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.STOP_BY_NAME_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.DistroXOpDescription.SYNC_BY_NAME_IN_WORKSPACE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.StackOpDescription.DELETE_WITH_KERBEROS_IN_WORKSPACE;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sequenceiq.distrox.api.distrox.request.DistroXImageChangeV1Request;
import com.sequenceiq.distrox.api.distrox.request.DistroXMaintenanceModeV1Request;
import com.sequenceiq.distrox.api.distrox.request.DistroXRepairV1Request;
import com.sequenceiq.distrox.api.distrox.request.DistroXScaleV1Request;
import com.sequenceiq.distrox.api.distrox.request.DistroXUserNamePasswordV1Request;
import com.sequenceiq.distrox.api.v1.distrox.model.DistroXV1Request;
import com.sequenceiq.distrox.api.distrox.request.UpdateDistroXV1Request;
import com.sequenceiq.distrox.api.distrox.response.DistroXStatusV1Response;
import com.sequenceiq.distrox.api.v1.distrox.model.DistroXV1Response;
import com.sequenceiq.distrox.api.distrox.response.DistroXViewV1Responses;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.GeneratedBlueprintV4Response;
import com.sequenceiq.cloudbreak.doc.ContentType;
import com.sequenceiq.cloudbreak.doc.Notes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/v1/distrox")
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/v4/distrox", protocols = "http,https")
public interface DistroXV1Endpoint {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = LIST_BY_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "listDistroXV1")
    DistroXViewV1Responses list(
            @QueryParam("environment") String environment,
            @QueryParam("onlyDatalakes") @DefaultValue("false") Boolean onlyDatalakes);

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = CREATE_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "postDistroXV1")
    DistroXV1Response post(@Valid DistroXV1Request request);

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = GET_BY_NAME_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "getDistroXV1")
    DistroXV1Response get(
            @PathParam("name") String name,
            @QueryParam("entries") Set<String> entries);

    @DELETE
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = DELETE_BY_NAME_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "deleteDistroXV1")
    void delete(
            @PathParam("name") String name,
            @QueryParam("forced") @DefaultValue("false") Boolean forced);

    @PUT
    @Path("{name}/sync")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = SYNC_BY_NAME_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "syncDistroXV1")
    void putSync(@PathParam("name") String name);

    @PUT
    @Path("{name}/retry")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = RETRY_BY_NAME_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.RETRY_STACK_NOTES,
            nickname = "retryDistroXV1")
    void putRetry(@PathParam("name") String name);

    @PUT
    @Path("{name}/stop")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = STOP_BY_NAME_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "stopDistroXV1")
    void putStop(@PathParam("name") String name);

    @PUT
    @Path("{name}/start")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = START_BY_NAME_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "startDistroXV1")
    void putStart(@PathParam("name") String name);

    @PUT
    @Path("{name}/scaling")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = SCALE_BY_NAME_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "putScalingDistroXInWorkspaceV4")
    void putScaling(
            @PathParam("name") String name,
            @Valid DistroXScaleV1Request updateRequest);

    @POST
    @Path("{name}/manual_repair")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = REPAIR_CLUSTER_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.CLUSTER_REPAIR_NOTES,
            nickname = "repairDistroXV1")
    void repairCluster(
            @PathParam("name") String name,
            @Valid DistroXRepairV1Request clusterRepairRequest);

    @POST
    @Path("{name}/blueprint")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = POST_STACK_FOR_BLUEPRINT_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "postDistroXForBlueprintV1")
    GeneratedBlueprintV4Response postStackForBlueprint(
            @PathParam("name") String name,
            @Valid DistroXV1Request stackRequest);

    @PUT
    @Path("{name}/change_image")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = CHECK_IMAGE_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "changeImageDistroXV1")
    void changeImage(
            @PathParam("name") String name,
            @Valid DistroXImageChangeV1Request stackImageChangeRequest);

    @GET
    @Path("{name}/request")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = GET_STACK_REQUEST_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "getDistroXRequestFromNameV1")
    DistroXV1Request getRequestfromName(@PathParam("name") String name);

    @GET
    @Path("{name}/status")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = GET_STATUS_BY_NAME, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "statusDistroXV1")
    DistroXStatusV1Response getStatusByName(@PathParam("name") String name);

    @DELETE
    @Path("{name}/instance")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = DELETE_INSTANCE_BY_ID_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "deleteInstanceDistroXV1")
    void deleteInstance(
            @PathParam("name") String name,
            @QueryParam("forced") @DefaultValue("false") Boolean forced,
            @QueryParam("instanceId") String instanceId);

    @PUT
    @Path("{name}/ambari_password")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = PUT_BY_NAME, produces = ContentType.JSON, notes = Notes.STACK_NOTES,
            nickname = "putpasswordDistroXV1")
    void putPassword(
            @PathParam("name") String name,
            @Valid DistroXUserNamePasswordV1Request userNamePasswordJson);

    @PUT
    @Path("{name}/maintenance")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = SET_MAINTENANCE_MODE, produces = ContentType.JSON, notes = Notes.MAINTENANCE_NOTES,
            nickname = "setDistroXMaintenanceMode")
    void setClusterMaintenanceMode(
            @PathParam("name") String name,
            @NotNull DistroXMaintenanceModeV1Request maintenanceMode);

    @PUT
    @Path("{name}/cluster")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = PUT_BY_STACK_ID, produces = ContentType.JSON, notes = Notes.CLUSTER_NOTES,
            nickname = "putDistroXV1")
    void putCluster(
            @PathParam("name") String name,
            @Valid UpdateDistroXV1Request updateJson);

    @DELETE
    @Path("{name}/cluster")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = DELETE_WITH_KERBEROS_IN_WORKSPACE, produces = ContentType.JSON, notes = Notes.CLUSTER_NOTES,
            nickname = "deleteWithKerberosDistroXV1")
    void deleteWithKerberos(@PathParam("name") String name);
}
