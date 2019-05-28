package com.sequenceiq.redbeams.service.drivers;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.DatabaseVendor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Caches and retrieves database drivers.
 */
@Service
public class DriverCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverCache.class);

    /**
     * Executes the given function with the associated driver.
     *
     * @param connectorJarUrl the URL to the connector jar
     * @param databaseVendor  the database vendor
     * @param consumer        the function to execute
     * @throws DriverLoadingException if there was a problem loading the driver
     */
    public void execWithDatabaseDriver(String connectorJarUrl, DatabaseVendor databaseVendor, Consumer<Driver> consumer) {
        if (StringUtils.isEmpty(connectorJarUrl)) {
            if (databaseVendor == DatabaseVendor.POSTGRES) {
                consumer.accept(new org.postgresql.Driver());
            } else {
                throw new DriverLoadingException("connectorJarUrl", "missingjarurl",
                        "Only PostgreSQL can be validated without a connector JAR URL");
            }
        }

        try {
            URL[] urls = { new URL("jar:" + connectorJarUrl + "!/") };
            String driverClassName = databaseVendor.connectionDriver();
            try (URLClassLoader cl = URLClassLoader.newInstance(urls)) {
                Driver driver = (Driver) cl.loadClass(driverClassName).getConstructor().newInstance();
                consumer.accept(driver);
            } catch (ClassNotFoundException e) {
                String message = "Could not locate driver class " + driverClassName + " in connector JAR URL " + connectorJarUrl;
                LOGGER.info(message, e);
                throw new DriverLoadingException("connectorJarUrl", "classnotfound", message);
            } catch (IOException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                String message = "Could not load driver class " + driverClassName + " from connector JAR URL " + connectorJarUrl;
                LOGGER.info(message, e);
                throw new DriverLoadingException("connectorJarUrl", "couldnotload", message);
            }
        } catch (MalformedURLException e) {
            String message = "Malformed connector JAR URL " + connectorJarUrl;
            LOGGER.info(message, e);
            throw new DriverLoadingException("connectorJarUrl", "malformedurl", message);
        }
    }
}
