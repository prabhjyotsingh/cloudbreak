package com.sequenceiq.redbeams.service.drivers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.DatabaseVendor;

import org.junit.Before;
import org.junit.Test;

public class DriverCacheTest {

    private DriverCache driverCache;

    @Before
    public void setup() {
        driverCache = new DriverCache();
    }

    @Test
    public void testDriverCacheLoader() {
        driverCache.execWithDatabaseDriver("", DatabaseVendor.POSTGRES,
                d -> assertThat(d instanceof org.postgresql.Driver).isTrue());
    }

    @Test(expected = DriverLoadingException.class)
    public void testDriverCacheLoader_loadFailure() {
        driverCache.execWithDatabaseDriver("", DatabaseVendor.MYSQL, d -> {});
    }
}
