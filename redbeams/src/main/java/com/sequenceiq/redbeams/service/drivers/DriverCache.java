package com.sequenceiq.redbeams.service.drivers;

import static java.util.Objects.requireNonNull;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.DatabaseVendor;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Caches and retrieves database drivers.
 */
@Service
public class DriverCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverCache.class);

    private final LoadingCache<UrlAndVendor, Driver> driverCache = Caffeine.newBuilder()
            .build(new DriverCacheLoader());

    /**
     * Retrieves a database driver given a connector Jar URL and a vendor.
     *
     * @param connectorJarUrl the URL to the connector jar
     * @param vendor          the database vendor
     * @return the corresponding driver
     * @throws DriverLoadingException if there was a problem loading the driver
     */
    public Driver getDriver(String connectorJarUrl, DatabaseVendor vendor) {
        return driverCache.get(new UrlAndVendor(connectorJarUrl,  vendor));
    }

    /**
     * Executes the given function with the associated driver.
     *
     * @param connectorJarUrl the URL to the connector jar
     * @param vendor          the database vendor
     * @param consumer        the function to execute
     * @throws DriverLoadingException if there was a problem loading the driver
     */
    public void execWithDatabaseDriver(String connectorJarUrl, DatabaseVendor vendor, Consumer<Driver> consumer)
            throws  DriverLoadingException {
        consumer.accept(getDriver(connectorJarUrl, vendor));
    }

    /**
     * Loads drivers given a connector jar URL and vendor.
     */
    private static class DriverCacheLoader implements CacheLoader<UrlAndVendor, Driver> {
        @Override
        public @Nullable Driver load(@NonNull UrlAndVendor urlAndVendor) throws Exception {
            String connectorJarUrl = urlAndVendor.getConnectorJarUrl();
            DatabaseVendor databaseVendor = urlAndVendor.getDatabaseVendor();

            if (StringUtils.isEmpty(connectorJarUrl)) {
                if (databaseVendor == DatabaseVendor.POSTGRES) {
                    return new org.postgresql.Driver();
                } else {
                    throw new DriverLoadingException("connectorJarUrl", "missingjarurl",
                            "Only PostgreSQL can be validated without a connector JAR URL");
                }
            }

            try {
                URL[] urls = { new URL("jar:" + connectorJarUrl + "!/") };
                String driverClassName = databaseVendor.connectionDriver();
                try (URLClassLoader cl = URLClassLoader.newInstance(urls)) {
                    return (Driver) cl.loadClass(driverClassName).getConstructor().newInstance();
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

    /**
     * A connector JAR URL and vendor pair.
     */
    public static class UrlAndVendor implements Serializable {

        private final String connectorJarUrl;

        private final DatabaseVendor databaseVendor;

        public UrlAndVendor(String connectorJarUrl, DatabaseVendor databaseVendor) {
            this.connectorJarUrl = requireNonNull(connectorJarUrl, "connectorJarUrl is null");
            this.databaseVendor = requireNonNull(databaseVendor, "databaseVendor is null");
        }

        /**
         * Gets the connector JAR URL.
         *
         * @return the connecto JAR URL
         */
        public String getConnectorJarUrl() {
            return connectorJarUrl;
        }

        /**
         * Gets the database vendor.
         *
         * @return the database vendor
         */
        public DatabaseVendor getDatabaseVendor() {
            return databaseVendor;
        }
    }
}
