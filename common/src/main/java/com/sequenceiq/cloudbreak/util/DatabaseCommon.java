package com.sequenceiq.cloudbreak.util;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common database functions.
 */
public class DatabaseCommon {
    private static final String JDBC_REGEX =
            "^(?:jdbc:(oracle|mysql|mariadb|postgresql)(?::[^:]+)?):(?:@|//)(.*?):(\\d*)[:/]?(\\w[-\\w]*)?(?:[?](?:[^=&]*=[^&=]*&?)*)?";
    //                |_______________________________||__________|         |___| |____|     |__________|       |___________________|
    //                               |                      |                 |     |             |                       |
    //                    database type / driver ID         |                host  port     database name (optional)      |
    //                                     additional driver specifiers (e.g., "thin")             URL query parameters (e.g., SSL options)

    private static final Pattern URL_PATTERN = Pattern.compile(JDBC_REGEX);

    private static final int VENDOR_DRIVER_ID_GROUP = 1;

    private static final int HOST_GROUP = 2;

    private static final int PORT_GROUP = 3;

    private static final int DATABASE_GROUP = 4;

    private DatabaseCommon() {
        // Do nothing
    }

    public static boolean isValidJdbcConnectionUrl(String connectionUrl) {
        try {
            parseJdbcConnectionUrl(connectionUrl);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static JdbcConnectionUrlFields parseJdbcConnectionUrl(String connectionUrl) {
        Matcher matcher = URL_PATTERN.matcher(connectionUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid JDBC connection URL: " + connectionUrl);
        }

        String vendorDriverId = matcher.group(VENDOR_DRIVER_ID_GROUP);
        String host = matcher.group(HOST_GROUP);
        int port = Integer.parseInt(matcher.group(PORT_GROUP));
        Optional<String> database = Optional.ofNullable(matcher.group(DATABASE_GROUP));
        return new JdbcConnectionUrlFields(vendorDriverId, host, port, database);
    }

    public static String getJdbcConnectionUrl(String vendorDriverId, String host, int port, Optional<String> database) {
        return getJdbcConnectionUrl(new JdbcConnectionUrlFields(vendorDriverId, host, port, database));
    }

    public static String getJdbcConnectionUrl(JdbcConnectionUrlFields fields) {
        String url;
        switch (fields.getVendorDriverId()) {
            case "postgresql":
                url = String.format("jdbc:postgresql://%s:%d/", fields.getHost(), fields.getPort());
                if (fields.getDatabase().isPresent()) {
                    url += fields.getDatabase().get();
                }
                break;
            case "mysql":
                // this includes mariadb
                url = String.format("jdbc:mysql://%s:%d", fields.getHost(), fields.getPort());
                if (fields.getDatabase().isPresent()) {
                    url += "/" + fields.getDatabase().get();
                }
                break;
            case "oracle":
                // using sid format, not service format
                url = String.format("jdbc:oracle:thin:@%s:%d", fields.getHost(), fields.getPort());
                if (fields.getDatabase().isPresent()) {
                    url += ":" + fields.getDatabase().get();
                }
                break;
            default:
                throw new UnsupportedOperationException("Don't know how to form a connection URL for JDBC driver " + fields.getVendorDriverId());
        }
        return url;
    }

    public static class JdbcConnectionUrlFields {
        private final String vendorDriverId;

        private final String host;

        private final int port;

        private final Optional<String> database;

        private final String hostAndPort;

        public JdbcConnectionUrlFields(String vendorDriverId, String host, int port, Optional<String> database) {
            this.vendorDriverId = requireNonNull(vendorDriverId);
            this.host = requireNonNull(host);
            this.port = port;
            this.database = requireNonNull(database);

            hostAndPort = String.format("%s:%d", host, port);
        }

        public String getVendorDriverId() {
            return vendorDriverId;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getHostAndPort() {
            return hostAndPort;
        }

        public Optional<String> getDatabase() {
            return database;
        }
    }
}
