package com.sequenceiq.distrox.api.distrox.response.cluster;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sequenceiq.distrox.api.distrox.response.cluster.clouderamanager.ClouderaManagerV1Response;
import com.sequenceiq.distrox.api.distrox.response.cluster.gateway.GatewayV1Response;
import com.sequenceiq.distrox.api.distrox.response.cluster.gateway.topology.ClusterExposedServiceV1Response;
import com.sequenceiq.distrox.api.distrox.response.cluster.storage.CloudStorageV1Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.api.endpoint.v4.blueprint.responses.BlueprintV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.BlueprintModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription;
import com.sequenceiq.cloudbreak.service.secret.model.SecretResponse;
import com.sequenceiq.cloudbreak.structuredevent.json.Base64Deserializer;
import com.sequenceiq.cloudbreak.structuredevent.json.Base64Serializer;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class DistroXClusterV1Response implements JsonEntity {

    @ApiModelProperty(ModelDescriptions.NAME)
    private String name;

    @ApiModelProperty(ClusterModelDescription.STATUS)
    private Status status;

    @ApiModelProperty(ClusterModelDescription.HOURS)
    private int hoursUp;

    @ApiModelProperty(ClusterModelDescription.MINUTES)
    private int minutesUp;

    @ApiModelProperty(ModelDescriptions.DESCRIPTION)
    private String description;

    @ApiModelProperty(ClusterModelDescription.STATUS_REASON)
    private String statusReason;

//    @ApiModelProperty(ClusterModelDescription.LDAP_CONFIG)
//    private LdapV4Response ldap;
//
//    @ApiModelProperty(ClusterModelDescription.DATABASES)
//    private List<DatabaseV4Response> databases;
//
//    @ApiModelProperty(ClusterModelDescription.PROXY_NAME)
//    private ProxyV4Response proxy;

    @ApiModelProperty(ClusterModelDescription.FILESYSTEM)
    private CloudStorageV1Response cloudStorage;

    private ClouderaManagerV1Response cm;

    private GatewayV1Response gateway;

    @ApiModelProperty(ClusterModelDescription.CLUSTER_ATTRIBUTES)
    private Map<String, Object> attributes = new HashMap<>();

    @ApiModelProperty(ClusterModelDescription.CREATION_FINISHED)
    private Long creationFinished;

    @ApiModelProperty(ClusterModelDescription.UPTIME)
    private Long uptime;

    //@ApiModelProperty
    //private KerberosV4Response kerberos;

    @ApiModelProperty(ClusterModelDescription.CLUSTER_EXPOSED_SERVICES)
    private Map<String, Collection<ClusterExposedServiceV1Response>> exposedServices;

//    @ApiModelProperty(ModelDescriptions.WORKSPACE_OF_THE_RESOURCE)
//    private WorkspaceResourceV4Response workspace;

    @ApiModelProperty(StackModelDescription.CM_MANAGEMENT_USERNAME)
    private SecretResponse cmMgmtUser;

    @ApiModelProperty(StackModelDescription.CM_MANAGEMENT_PASSWORD)
    private SecretResponse cmMgmtPassword;

    @ApiModelProperty(ClusterModelDescription.BLUEPRINT)
    private BlueprintV4Response cmTemplate;

    @ApiModelProperty(BlueprintModelDescription.BLUEPRINT)
    @JsonSerialize(using = Base64Serializer.class)
    @JsonDeserialize(using = Base64Deserializer.class)
    private String extendedCmTemplateText;

    @ApiModelProperty(StackModelDescription.SERVER_IP)
    private String serverIp;

    @ApiModelProperty(StackModelDescription.SERVER_URL)
    private String serverUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getHoursUp() {
        return hoursUp;
    }

    public void setHoursUp(int hoursUp) {
        this.hoursUp = hoursUp;
    }

    public int getMinutesUp() {
        return minutesUp;
    }

    public void setMinutesUp(int minutesUp) {
        this.minutesUp = minutesUp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
//
//    public LdapV4Response getLdap() {
//        return ldap;
//    }
//
//    public void setLdap(LdapV4Response ldap) {
//        this.ldap = ldap;
//    }

    public CloudStorageV1Response getCloudStorage() {
        return cloudStorage;
    }

    public void setCloudStorage(CloudStorageV1Response cloudStorage) {
        this.cloudStorage = cloudStorage;
    }

    public ClouderaManagerV1Response getCm() {
        return cm;
    }

    public void setCm(ClouderaManagerV1Response cm) {
        this.cm = cm;
    }

    public GatewayV1Response getGateway() {
        return gateway;
    }

    public void setGateway(GatewayV1Response gateway) {
        this.gateway = gateway;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Long getCreationFinished() {
        return creationFinished;
    }

    public void setCreationFinished(Long creationFinished) {
        this.creationFinished = creationFinished;
    }

    public Long getUptime() {
        return uptime;
    }

    public void setUptime(Long uptime) {
        this.uptime = uptime;
    }

    public SecretResponse getCmMgmtUser() {
        return cmMgmtUser;
    }

    public void setCmMgmtUser(SecretResponse cmMgmtUser) {
        this.cmMgmtUser = cmMgmtUser;
    }

    public SecretResponse getCmMgmtPassword() {
        return cmMgmtPassword;
    }

    public void setCmMgmtPassword(SecretResponse cmMgmtPassword) {
        this.cmMgmtPassword = cmMgmtPassword;
    }

//    public WorkspaceResourceV4Response getWorkspace() {
//        return workspace;
//    }
//
//    public void setWorkspace(WorkspaceResourceV4Response workspace) {
//        this.workspace = workspace;
//    }
//
//    public List<DatabaseV4Response> getDatabases() {
//        return databases;
//    }
//
//    public void setDatabases(List<DatabaseV4Response> databases) {
//        this.databases = databases;
//    }
//
//    public ProxyV4Response getProxy() {
//        return proxy;
//    }
//
//    public void setProxy(ProxyV4Response proxy) {
//        this.proxy = proxy;
//    }
//
//    public CustomContainerV4Response getCustomContainers() {
//        return customContainers;
//    }
//
//    public void setCustomContainers(CustomContainerV4Response customContainers) {
//        this.customContainers = customContainers;
//    }
//
//    public KerberosV4Response getKerberos() {
//        return kerberos;
//    }
//
//    public void setKerberos(KerberosV4Response kerberos) {
//        this.kerberos = kerberos;
//    }

    public Map<String, Collection<ClusterExposedServiceV1Response>> getExposedServices() {
        return exposedServices;
    }

    public void setExposedServices(Map<String, Collection<ClusterExposedServiceV1Response>> exposedServices) {
        this.exposedServices = exposedServices;
    }

    public BlueprintV4Response getCmTemplate() {
        return cmTemplate;
    }

    public void setCmTemplate(BlueprintV4Response cmTemplate) {
        this.cmTemplate = cmTemplate;
    }

    public String getExtendedCmTemplateText() {
        return extendedCmTemplateText;
    }

    public void setExtendedCmTemplateText(String extendedCmTemplateText) {
        this.extendedCmTemplateText = extendedCmTemplateText;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
