package com.sequenceiq.redbeams.service.common;

import java.sql.SQLException;

/**
 * An interface that can throw exceptions.
 */
@FunctionalInterface
public interface SupplierWithSQLException<T> {
    T get() throws SQLException;
}
