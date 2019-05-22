package com.sequenceiq.redbeams.api.endpoint.v4.database.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.Database;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A request for creating a database on a database server.
 */
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateDatabaseV4Request implements JsonEntity {

    @ApiModelProperty(Database.NAME)
    private String existingDatabaseServerName;

    @ApiModelProperty(Database.DATABASE_NAME)
    private String databaseName;

    @ApiModelProperty(Database.RDSTYPE)
    private String type;

    /**
     * Gets the name of the existing database on which to create the schema.
     *
     * @return the existing database name
     */
    public String getExistingDatabaseServerName() {
        return existingDatabaseServerName;
    }

    /**
     * Sets the name of the existing database on which to create the schema.
     *
     * @param existingDatabaseServerName the existing database name
     */
    public void setExistingDatabaseServerName(String existingDatabaseServerName) {
        this.existingDatabaseServerName = existingDatabaseServerName;
    }

    /**
     * Gets the database name to create.
     *
     * @return the database name
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Sets the database name to create.
     *
     * @param databaseName the database name
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * Gets the database type.
     *
     * @return the database type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the database type.
     *
     * @param type the database type
     */
    public void setType(String type) {
        this.type = type;
    }
}
