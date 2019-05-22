package com.sequenceiq.redbeams.service.connectivity;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.DatabaseVendor;
import com.sequenceiq.cloudbreak.util.DatabaseCommon;
import com.sequenceiq.redbeams.domain.DatabaseConfig;
import com.sequenceiq.redbeams.domain.DatabaseServerConfig;
import com.sequenceiq.redbeams.service.drivers.DriverCache;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * This class provides functions for taking a database config object and produces database connections.
 */
@Service
public class DatabaseConnectionService {

    @Inject
    private DriverCache driverCache;

    /**
     * Connects to the database using a database config object.
     *
     * @param dbConfig the database config object
     * @return the database connection
     */
    public Connection getConnection(DatabaseConfig databaseConfig) throws SQLException {
        return getConnection(databaseConfig.getConnectorJarUrl(), databaseConfig.getDatabaseVendor(),
                databaseConfig.getConnectionURL(), databaseConfig.getConnectionUserName().getRaw(),
                databaseConfig.getConnectionPassword().getRaw());
    }

    public Connection getConnection(DatabaseServerConfig server) throws SQLException {
        String connectionUrl = DatabaseCommon.getConnectionURL(server.getDatabaseVendor().jdbcUrlDriverId(),
                server.getHost(), server.getPort(), Optional.empty());

        return getConnection(server.getConnectorJarUrl(), server.getDatabaseVendor(), connectionUrl,
                server.getConnectionUserName(), server.getConnectionPassword());
    }

    /**
     * Connects to a database using the given driver and credentials. It's the responsibility of the caller to handle
     * all connections and exceptions.
     *
     * @param connectorJarUrl    the URL to the connector jar
     * @param vendor             the database vendor
     * @param driver             the driver to use to connect to the database
     * @param connectionUrl      the URL to connect to the database
     * @param connectionUserName the username to use to connect to the database
     * @param connectionPassword the password to use to connect to do the database
     * @return the database connection
     * @throws SQLException if there was a problem connecting to the database
     */
    public Connection getConnection(String connectorJarUrl, DatabaseVendor vendor, String connectionUrl,
        String connectionUserName, String connectionPassword) throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.setProperty("user", connectionUserName);
        connectionProps.setProperty("password", connectionPassword);

        return driverCache.getDriver(connectorJarUrl, vendor).connect(connectionUrl, connectionProps);
    }
}
