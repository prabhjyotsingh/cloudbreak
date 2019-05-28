package com.sequenceiq.redbeams.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Connection;
import java.util.Optional;

import com.sequenceiq.cloudbreak.util.DatabaseCommon;
import com.sequenceiq.redbeams.domain.DatabaseServerConfig;
import com.sequenceiq.redbeams.service.connectivity.DatabaseConnectionService;
import com.sequenceiq.redbeams.service.drivers.DriverCache;

import javax.inject.Inject;

@Component
public class DatabaseServerConnectionValidator extends BaseConnectionValidator implements Validator {

    @Inject
    private DriverCache driverCache;

    @Inject
    private DatabaseConnectionService dbConnectionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return DatabaseServerConfig.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DatabaseServerConfig server = (DatabaseServerConfig) target;
        String connectionUrl = DatabaseCommon.getConnectionURL(server.getDatabaseVendor().jdbcUrlDriverId(),
            server.getHost(), server.getPort(), Optional.empty());
        dbConnectionService.execWithConnection();
        validate(driverCache.execWithDatabaseDriver(server.getConnectorJarUrl(), server.getDatabaseVendor(), driver ->
            dbConnectionService.getConnection(driver, connectionUrl, server.getConnectionUserName(),
                    server.getConnectionPassword())), connectionUrl, errors);
    }

}
