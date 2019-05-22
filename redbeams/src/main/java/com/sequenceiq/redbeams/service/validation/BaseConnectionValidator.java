package com.sequenceiq.redbeams.service.validation;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

public abstract class BaseConnectionValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseConnectionValidator.class);

    protected <T> void validate(SupplierWithSQLException<Connection> connectionFunction, String connectionUrl,
        Errors errors) {
        try (Connection conn = connectionFunction.get()) {
            LOGGER.debug("Connection successful to {}", connectionUrl);
        } catch (SQLException e) {
            String message = "Failed to connect to " + connectionUrl;
            LOGGER.info(message, e);
            errors.reject("connectionfailed", message);
        }
    }
}
