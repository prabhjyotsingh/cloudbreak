/*
 * YARN Simplified API layer for services
 * Bringing a new service on YARN today is not a simple experience. The APIs of existing frameworks are either too low level (native YARN), require writing new code (for frameworks with programmatic APIs) or writing a complex spec (for declarative frameworks).  This simplified REST API can be used to create and manage the lifecycle of YARN services. In most cases, the application owner will not be forced to make any changes to their applications. This is primarily true if the application is packaged with containerization technologies like Docker.  This document describes the API specifications (aka. YarnFile) for deploying/managing containerized services on YARN. The same JSON spec can be used for both REST API and CLI to manage the services.
 *
 * OpenAPI spec version: 1.0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.apache.cb.yarn.service.api.records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Set of configuration properties that can be injected into the service components via envs, files and custom pluggable helper docker containers. Files of several standard formats like xml, properties, json, yaml and templates will be supported.
 */
@ApiModel(description = "Set of configuration properties that can be injected into the service components via envs, files and custom pluggable helper docker containers. Files of several standard formats like xml, properties, json, yaml and templates will be supported.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-08-08T16:59:40.572+02:00")
public class ModelConfiguration {
    @JsonProperty("properties")
    private Map<String, String> properties = null;

    @JsonProperty("env")
    private Map<String, String> env = null;

    @JsonProperty("files")
    private List<ConfigFile> files = null;

    public ModelConfiguration properties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public ModelConfiguration putPropertiesItem(String key, String propertiesItem) {
        if (this.properties == null) {
            this.properties = new HashMap<String, String>();
        }
        this.properties.put(key, propertiesItem);
        return this;
    }

    /**
     * A blob of key-value pairs for configuring the YARN service AM.
     *
     * @return properties
     **/
    @ApiModelProperty(value = "A blob of key-value pairs for configuring the YARN service AM.")
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public ModelConfiguration env(Map<String, String> env) {
        this.env = env;
        return this;
    }

    public ModelConfiguration putEnvItem(String key, String envItem) {
        if (this.env == null) {
            this.env = new HashMap<String, String>();
        }
        this.env.put(key, envItem);
        return this;
    }

    /**
     * A blob of key-value pairs which will be appended to the default system properties and handed off to the service at start time. All placeholder references to properties will be substituted before injection.
     *
     * @return env
     **/
    @ApiModelProperty(value = "A blob of key-value pairs which will be appended to the default system properties and handed off to the service at start time. All placeholder references to properties will be substituted before injection.")
    public Map<String, String> getEnv() {
        return env;
    }

    public void setEnv(Map<String, String> env) {
        this.env = env;
    }

    public ModelConfiguration files(List<ConfigFile> files) {
        this.files = files;
        return this;
    }

    public ModelConfiguration addFilesItem(ConfigFile filesItem) {
        if (this.files == null) {
            this.files = new ArrayList<ConfigFile>();
        }
        this.files.add(filesItem);
        return this;
    }

    /**
     * Array of list of files that needs to be created and made available as volumes in the service component containers.
     *
     * @return files
     **/
    @ApiModelProperty(value = "Array of list of files that needs to be created and made available as volumes in the service component containers.")
    public List<ConfigFile> getFiles() {
        return files;
    }

    public void setFiles(List<ConfigFile> files) {
        this.files = files;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelConfiguration _configuration = (ModelConfiguration) o;
        return Objects.equals(this.properties, _configuration.properties) &&
                Objects.equals(this.env, _configuration.env) &&
                Objects.equals(this.files, _configuration.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties, env, files);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ModelConfiguration {\n");

        sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
        sb.append("    env: ").append(toIndentedString(env)).append("\n");
        sb.append("    files: ").append(toIndentedString(files)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

