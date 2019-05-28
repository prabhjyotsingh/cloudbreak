package com.sequenceiq.redbeams.service.common;

import java.sql.SQLException;

/**
 * Consumer that can throw SQL exceptions.
 */
@FunctionalInterface
public interface ConsumerWithSQLException<T> {
    void accept(T obj) throws SQLException;
}
