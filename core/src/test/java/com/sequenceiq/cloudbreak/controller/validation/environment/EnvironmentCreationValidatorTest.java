package com.sequenceiq.cloudbreak.controller.validation.environment;

import static com.sequenceiq.cloudbreak.controller.validation.ValidationResult.State.ERROR;
import static com.sequenceiq.cloudbreak.controller.validation.ValidationResult.State.VALID;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentNetworkV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.LocationV4Request;
import com.sequenceiq.cloudbreak.cloud.model.CloudRegions;
import com.sequenceiq.cloudbreak.controller.validation.ValidationResult;
import com.sequenceiq.cloudbreak.controller.validation.environment.network.EnvironmentNetworkValidator;
import com.sequenceiq.cloudbreak.domain.Credential;
import com.sequenceiq.cloudbreak.domain.environment.Environment;
import com.sequenceiq.cloudbreak.domain.environment.Region;
import com.sequenceiq.cloudbreak.util.EnvironmentUtils;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentCreationValidatorTest {

    @Mock
    private Map<CloudPlatform, EnvironmentNetworkValidator> environmentNetworkValidatorsByCloudPlatform;

    @Spy
    private EnvironmentRegionValidator environmentRegionValidator = new EnvironmentRegionValidator();

    @InjectMocks
    private EnvironmentCreationValidator environmentCreationValidator;

    @Test
    public void testValidationWithMultipleErrors() {
        assertNotNull(environmentRegionValidator);
        Credential credential = new Credential();

        Environment environment = new Environment();
        environment.setCredential(credential);

        Region region1 = new Region();
        region1.setName("region1");
        Region region2 = new Region();
        region2.setName("region2");
        environment.setRegions(Set.of(region1, region2));
        EnvironmentV4Request environmentRequest = new EnvironmentV4Request();
        environmentRequest.setRegions(Set.of("region1", "region2", "region3"));
        LocationV4Request locationRequest = new LocationV4Request();
        locationRequest.setName("region1");
        environmentRequest.setLocation(locationRequest);
        CloudRegions cloudRegions = EnvironmentUtils.getCloudRegions();

        ValidationResult result = environmentCreationValidator.validate(environment, environmentRequest, cloudRegions);

        assertEquals(ERROR, result.getState());
        assertEquals(1L, result.getErrors().size());
        assertTrue(result.getFormattedErrors().contains("[region3]"));
    }

    @Test
    public void testValidationWhenRegionsAreNotSupportedOnCloudProviderButProvided() {
        // GIVEN
        Credential credential = new Credential();
        Environment environment = new Environment();
        environment.setCredential(credential);
        EnvironmentV4Request environmentRequest = new EnvironmentV4Request();
        environmentRequest.setRegions(Set.of("region1", "region2", "region3"));
        LocationV4Request locationRequest = new LocationV4Request();
        locationRequest.setName("region1");
        environmentRequest.setLocation(locationRequest);
        CloudRegions cloudRegions = EnvironmentUtils.getCloudRegions(false);
        // WHEN
        ValidationResult result = environmentCreationValidator.validate(environment, environmentRequest, cloudRegions);
        // THEN
        assertEquals(ERROR, result.getState());
        assertEquals(1L, result.getErrors().size());

        assertThat(result.getErrors().get(0), containsString("Regions are not supporeted on cloudprovider"));
    }

    @Test
    public void testValidationWhenRegionsAreSupportedOnCloudProviderButNotProvided() {
        // GIVEN
        Credential credential = new Credential();
        Environment environment = new Environment();
        environment.setCredential(credential);
        environment.setRegions(Set.of());
        EnvironmentV4Request environmentRequest = new EnvironmentV4Request();
        environment.setRegions(Set.of());
        environmentRequest.setRegions(Collections.emptySet());
        LocationV4Request locationRequest = new LocationV4Request();
        locationRequest.setName("region1");
        locationRequest.setLatitude(1.1);
        locationRequest.setLongitude(-1.1);
        environmentRequest.setLocation(locationRequest);
        CloudRegions cloudRegions = EnvironmentUtils.getCloudRegions();
        // WHEN
        ValidationResult result = environmentCreationValidator.validate(environment, environmentRequest, cloudRegions);
        // THEN
        assertEquals(ERROR, result.getState());
        assertEquals(1L, result.getErrors().size());
        assertThat(result.getErrors().get(0), containsString("Regions are mandatory on cloudprovider"));
    }

    @Test
    public void testSuccessfulValidationWithRegions() {
        // GIVEN
        Credential credential = new Credential();

        Environment environment = new Environment();
        environment.setCredential(credential);
        Region region1 = new Region();
        region1.setName("region1");
        Region region2 = new Region();
        region2.setName("region2");
        environment.setRegions(Set.of(region1, region2));
        environment.setLocation("region1");
        environment.setLatitude(1.1);
        environment.setLongitude(-1.1);

        EnvironmentV4Request environmentRequest = new EnvironmentV4Request();
        environmentRequest.setRegions(Set.of("region1", "region2"));
        LocationV4Request locationRequest = new LocationV4Request();
        locationRequest.setName("region1");
        environmentRequest.setLocation(locationRequest);
        CloudRegions cloudRegions = EnvironmentUtils.getCloudRegions();
        // WHEN
        ValidationResult result = environmentCreationValidator.validate(environment, environmentRequest, cloudRegions);
        // THEN
        assertEquals(VALID, result.getState());
    }

    @Test
    public void testSuccessfulValidationWithoutRegions() {
        // GIVEN
        Credential credential = new Credential();

        Environment environment = new Environment();
        environment.setCredential(credential);
        environment.setLocation("region1");
        environment.setLatitude(1.1);
        environment.setLongitude(-1.1);

        EnvironmentV4Request environmentRequest = new EnvironmentV4Request();
        environmentRequest.setRegions(Collections.emptySet());
        LocationV4Request locationRequest = new LocationV4Request();
        locationRequest.setName("region1");
        locationRequest.setLatitude(1.1);
        locationRequest.setLongitude(-1.1);
        environmentRequest.setLocation(locationRequest);
        CloudRegions cloudRegions = EnvironmentUtils.getCloudRegions(false);

        // WHEN
        ValidationResult result = environmentCreationValidator.validate(environment, environmentRequest, cloudRegions);
        // THEN
        assertEquals(VALID, result.getState());
    }

    @Test
    public void testValidationWhenNetworkIsNotSupportedForPlatform() {
        // GIVEN
        Credential credential = new Credential();
        Environment environment = new Environment();
        environment.setCredential(credential);
        environment.setLocation("region1");
        environment.setLatitude(1.1);
        environment.setLongitude(-1.1);

        EnvironmentV4Request environmentRequest = new EnvironmentV4Request();
        environmentRequest.setRegions(Collections.emptySet());
        LocationV4Request locationRequest = new LocationV4Request();
        locationRequest.setName("region1");
        locationRequest.setLatitude(1.1);
        locationRequest.setLongitude(-1.1);
        environmentRequest.setLocation(locationRequest);
        CloudRegions cloudRegions = EnvironmentUtils.getCloudRegions(false);

        CloudPlatform mock = CloudPlatform.MOCK;
        environment.setCloudPlatform(mock.name());
        environmentRequest.setNetwork(new EnvironmentNetworkV4Request());
        when(environmentNetworkValidatorsByCloudPlatform.get(mock)).thenReturn(null);

        // WHEN
        ValidationResult result = environmentCreationValidator.validate(environment, environmentRequest, cloudRegions);
        // THEN
        assertEquals(ERROR, result.getState());
        assertEquals(1L, result.getErrors().size());
        assertThat(result.getErrors().get(0), containsString("Environment specific network is not supported for cloud platform:"));
    }
}