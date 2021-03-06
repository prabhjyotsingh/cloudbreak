package com.sequenceiq.redbeams.api.endpoint.v4.database.request;

import static com.sequenceiq.redbeams.doc.ModelDescriptions.Database;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.redbeams.api.endpoint.v4.database.base.DatabaseV4Base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// TODO add validation
//@ValidRds
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatabaseV4Request extends DatabaseV4Base {

    @NotNull
    @ApiModelProperty(value = Database.USERNAME, required = true)
    private String connectionUserName;

    @NotNull
    @ApiModelProperty(value = Database.PASSWORD, required = true)
    private String connectionPassword;

    public String getConnectionUserName() {
        return connectionUserName;
    }

    public void setConnectionUserName(String connectionUserName) {
        this.connectionUserName = connectionUserName;
    }

    public String getConnectionPassword() {
        return connectionPassword;
    }

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

}
