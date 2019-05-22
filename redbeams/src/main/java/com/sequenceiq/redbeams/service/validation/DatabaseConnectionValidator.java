package com.sequenceiq.redbeams.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sequenceiq.redbeams.domain.DatabaseConfig;
import com.sequenceiq.redbeams.service.connectivity.DatabaseConnectionService;

import javax.inject.Inject;

@Component
public class DatabaseConnectionValidator extends BaseConnectionValidator implements Validator {

    @Inject
    private DatabaseConnectionService dbConnectionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return DatabaseConfig.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DatabaseConfig database = (DatabaseConfig) target;
        String connectionUrl = database.getConnectionURL();

        validate(() -> dbConnectionService.getConnection(database.getConnectorJarUrl(),
                database.getDatabaseVendor(), connectionUrl, database.getConnectionUserName().getRaw(),
                database.getConnectionPassword().getRaw()), connectionUrl, errors);
    }

}
