package com.sequenceiq.cloudbreak.cm;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ClouderaManagerMgmtTelemetryServiceTest {

    private ClouderaManagerMgmtTelemetryService underTest;

    @Before
    public void setUp() {
        underTest = new ClouderaManagerMgmtTelemetryService();
    }

    @Test
    public void testTrimAndReplace() {
        // GIVEN
        String rawPrivateKey = "BEGIN\nline1\nline2\nlastline";
        // WHEN
        String result = underTest.trimAndReplacePrivateKey(rawPrivateKey.toCharArray());
        // THEN
        assertEquals("BEGIN\\nline1\\nline2\\nlastline", result);
    }
}
