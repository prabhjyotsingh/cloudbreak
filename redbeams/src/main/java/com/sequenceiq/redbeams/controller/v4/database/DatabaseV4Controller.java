package com.sequenceiq.redbeams.controller.v4.database;

import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.sequenceiq.cloudbreak.api.util.ConverterUtil;
import com.sequenceiq.cloudbreak.workspace.controller.WorkspaceEntityType;
import com.sequenceiq.redbeams.api.endpoint.v4.database.DatabaseV4Endpoint;
import com.sequenceiq.redbeams.api.endpoint.v4.database.request.DatabaseTestV4Request;
import com.sequenceiq.redbeams.api.endpoint.v4.database.request.DatabaseV4Request;
import com.sequenceiq.redbeams.api.endpoint.v4.database.responses.DatabaseTestV4Response;
import com.sequenceiq.redbeams.api.endpoint.v4.database.responses.DatabaseV4Response;
import com.sequenceiq.redbeams.api.endpoint.v4.database.responses.DatabaseV4Responses;
import com.sequenceiq.redbeams.domain.DatabaseConfig;
import com.sequenceiq.redbeams.service.dbconfig.DatabaseConfigService;

@Controller
@Transactional(Transactional.TxType.NEVER)
@WorkspaceEntityType(DatabaseConfig.class)
@Component
public class DatabaseV4Controller /*extends NotificationController */ implements DatabaseV4Endpoint {

    @Inject
    private ConverterUtil redbeamsConverterUtil;

    @Inject
    private DatabaseConfigService databaseConfigService;

    @Override
    public DatabaseV4Responses list(String environment, Boolean attachGlobal) {
        return new DatabaseV4Responses();
    }

    @Override
    public DatabaseV4Response create(@Valid DatabaseV4Request request) {
        return new DatabaseV4Response();
    }

    @Override
    public DatabaseV4Response register(@Valid DatabaseV4Request request) {
        DatabaseConfig databaseConfig = redbeamsConverterUtil.convert(request, DatabaseConfig.class);
        return redbeamsConverterUtil.convert(databaseConfigService.register(databaseConfig), DatabaseV4Response.class);
    }

    @Override
    public DatabaseV4Response get(String name) {
        return new DatabaseV4Response();
    }

    @Override
    public DatabaseV4Response delete(String name) {
        // TODO special care for registered databases: we should not delete the db, only delete the entry from redbeams db.
        return new DatabaseV4Response();
    }

    @Override
    public DatabaseV4Responses deleteMultiple(Set<String> names) {
        return new DatabaseV4Responses();
    }

    @Override
    public DatabaseV4Request getRequest(String name) {
        return new DatabaseV4Request();
    }

    @Override
    public DatabaseTestV4Response test(@Valid DatabaseTestV4Request databaseTestV4Request) {
        return new DatabaseTestV4Response();
    }

}
