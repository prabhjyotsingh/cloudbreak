package com.sequenceiq.redbeams.service.connectivity;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.DatabaseVendor;
import com.sequenceiq.cloudbreak.util.DatabaseCommon;
import com.sequenceiq.redbeams.domain.DatabaseConfig;
import com.sequenceiq.redbeams.domain.DatabaseServerConfig;
import com.sequenceiq.redbeams.service.common.SupplierWithSQLException;
import com.sequenceiq.redbeams.service.drivers.DriverCache;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

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
     * @param driver   the driver to connect with
     * @param dbConfig the database config object
     */
    public void execWithConnection(DatabaseConfig databaseConfig, Consumer<Connection> execWithConnection) throws SQLException {
        execWithConnection(databaseConfig.getConnectorJarUrl(), databaseConfig.getDatabaseVendor(),
                databaseConfig.getConnectionURL(), databaseConfig.getConnectionUserName().getRaw(),
                databaseConfig.getConnectionPassword().getRaw(), execWithConnection);
    }

    public void execWithConnection(DatabaseServerConfig server, Consumer<Connection> execWithConnection) throws SQLException {
        String connectionUrl = DatabaseCommon.getConnectionURL(server.getDatabaseVendor().jdbcUrlDriverId(),
                server.getHost(), server.getPort(), Optional.empty());

        execWithConnection(server.getConnectorJarUrl(), server.getDatabaseVendor(), connectionUrl, server.getConnectionUserName(), server.getConnectionPassword(), execWithConnection);
    }

    /**
     * Connects to a database using the given driver and credentials. It's the responsibility of the caller to handle
     * all connections and exceptions. Executes the given function.
     *
     * @param connectorJarUrl    the URL to the connector jar
     * @param vendor             the database vendor
     * @param connectionUrl      the URL to connect to the database
     * @param connectionUserName the username to use to connect to the database
     * @param connectionPassword the password to use to connect to do the database
     * @param execWithConnection the function to execute with a connection.
     * @return the database connection
     * @throws SQLException if there was a problem connecting to the database
     */
    public void execWithConnection(String connectorJarUrl, DatabaseVendor vendor,
                                   String connectionUrl, String connectionUserName, String connectionPassword,
                                   Consumer<Connection> execWithConnection) throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.setProperty("user", connectionUserName);
        connectionProps.setProperty("password", connectionPassword);

        driverCache.execWithDatabaseDriver(connectorJarUrl, vendor, driver -> {
            try (Connection conn = driver.connect(connectionUrl, connectionProps)) {
                execWithConnection.accept(conn);
            } catch (SQLException e) {
                // Do nothing
            }
        });
    }
}
