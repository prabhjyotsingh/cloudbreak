package com.sequenceiq.cloudbreak.converter.v4.blueprint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.sequenceiq.cloudbreak.api.endpoint.v4.blueprint.requests.BlueprintV4Request;
import com.sequenceiq.cloudbreak.blueprint.utils.BlueprintUtils;
import com.sequenceiq.cloudbreak.cmtemplate.CmTemplateGeneratorService;
import com.sequenceiq.cloudbreak.cmtemplate.generator.template.domain.GeneratedCmTemplate;
import com.sequenceiq.cloudbreak.common.converter.MissingResourceNameGenerator;
import com.sequenceiq.cloudbreak.common.type.APIResourceType;
import com.sequenceiq.cloudbreak.converter.AbstractJsonConverterTest;
import com.sequenceiq.cloudbreak.domain.Blueprint;
import com.sequenceiq.cloudbreak.exception.BadRequestException;
import com.sequenceiq.cloudbreak.json.JsonHelper;

public class BlueprintV4RequestToBlueprintConverterTest extends AbstractJsonConverterTest<BlueprintV4Request> {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private BlueprintV4RequestToBlueprintConverter underTest;

    @Mock
    private JsonHelper jsonHelper;

    @Spy
    private final BlueprintUtils blueprintUtils = new BlueprintUtils();

    @Mock
    private MissingResourceNameGenerator missingResourceNameGenerator;

    @Mock
    private CmTemplateGeneratorService clusterTemplateGeneratorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        doReturn(2).when(blueprintUtils).countHostGroups(any());
        doReturn("bpname").when(blueprintUtils).getBlueprintName(any());
    }

    @Test
    public void testConvertWhereEveryDataFilledButThereIsNoTagsElementInBlueprintJsonThenItShouldBeEmpty() {
        Blueprint result = underTest.convert(getRequest("blueprint.json"));
        assertAllFieldsNotNull(result);
        assertEquals("{}", result.getTags().getValue());
        assertEquals("HDP", result.getStackType());
        assertEquals("2.3", result.getStackVersion());
    }

    @Test
    public void testConvertWhenInputJsonHasTagsFieldButItsEmpty() {
        Blueprint result = underTest.convert(getRequest("blueprint-empty-tags.json"));
        assertAllFieldsNotNull(result);
        assertEquals("{}", result.getTags().getValue());
        assertEquals("HDP", result.getStackType());
        assertEquals("2.3", result.getStackVersion());
    }

    @Test
    public void testConvertWhenInputJsonHasTagsFieldAndItHasMoreThanOneFieldInIt() {
        Blueprint result = underTest.convert(getRequest("blueprint-filled-tags.json"));
        assertAllFieldsNotNull(result);
        Assert.assertTrue(result.getTags().getMap().size() > 1);
        assertEquals("HDP", result.getStackType());
        assertEquals("2.3", result.getStackVersion());
    }

    @Test
    public void testConvertWhenUrlIsNotEmptyButInvalidThenExceptionWouldCome() {
        String wrongUrl = "some wrong content for url";
        BlueprintV4Request request = getRequest("blueprint.json");
        request.setUrl(wrongUrl);

        thrown.expect(BadRequestException.class);
        thrown.expectMessage(String.format("Cannot download ambari validation from: %s", wrongUrl));

        underTest.convert(request);
    }

    @Test
    public void testConvertWhenUrlIsNotNullButEmptyThenBlueprintTextShouldBeTheProvidedAmbariBlueprint() {
        BlueprintV4Request request = getRequest("blueprint.json");
        request.setUrl("");

        Blueprint result = underTest.convert(request);

        assertEquals(request.getBlueprint(), result.getBlueprintText());
        assertEquals("HDP", result.getStackType());
        assertEquals("2.3", result.getStackVersion());
    }

    @Test
    public void testConvertWhenNameIsFilledThenTheSameShoulBeInTheBlueprintObject() {
        String name = "name";
        BlueprintV4Request request = getRequest("blueprint.json");
        request.setName(name);

        Blueprint result = underTest.convert(request);

        assertEquals(name, result.getName());
        assertEquals("HDP", result.getStackType());
        assertEquals("2.3", result.getStackVersion());
    }

    @Test
    public void testConvertWhenNameIsNullThenGeneratedNameShouldBeRepresentedInTheBlueprintObject() {
        String generatedName = "something generated here";
        BlueprintV4Request request = getRequest("blueprint.json");
        request.setName(null);
        when(missingResourceNameGenerator.generateName(APIResourceType.BLUEPRINT)).thenReturn(generatedName);

        Blueprint result = underTest.convert(request);

        assertEquals(generatedName, result.getName());
        assertEquals("HDP", result.getStackType());
        assertEquals("2.3", result.getStackVersion());
    }

    @Test
    public void testConvertWhenUnableToObtainTheBlueprintNameFromTheProvidedBlueprintTextThenExceptionWouldCome() {
        doAnswer(invocation -> {
            throw new IOException("some message");
        }).when(blueprintUtils).getBlueprintName(any());
        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Invalid blueprint: Failed to parse JSON.");

        underTest.convert(getRequest("blueprint.json"));
    }

    @Test
    public void testConvertWhenUnableToObtainHostGroupCountThenExceptionWouldCome() {
        doAnswer(invocation -> {
            throw new IOException("some message");
        }).when(blueprintUtils).countHostGroups(any());
        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Invalid blueprint: Failed to parse JSON.");

        underTest.convert(getRequest("blueprint.json"));
    }

    @Test
    public void testConvertWhenUnableToObtainTheStackTypeFromTheProvidedBlueprintTextThenExceptionWouldCome() {
        doAnswer(invocation -> {
            throw new IOException("some message");
        }).when(blueprintUtils).getBlueprintStackName(any());
        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Invalid blueprint: Failed to parse JSON.");

        underTest.convert(getRequest("blueprint.json"));
    }

    @Test
    public void testConvertWhenUnableToObtainTheStackVersionFromTheProvidedBlueprintTextThenExceptionWouldCome() {
        doAnswer(invocation -> {
            throw new IOException("some message");
        }).when(blueprintUtils).getBlueprintStackVersion(any());
        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Invalid blueprint: Failed to parse JSON.");

        underTest.convert(getRequest("blueprint.json"));
    }

    @Test
    public void testConvertWhenUnableToCreateJsonFromIncomingTagsThenExceptionWouldCome() {
        BlueprintV4Request request = getRequest("blueprint.json");
        Map<String, Object> invalidTags = new HashMap<>(1);
        invalidTags.put(null, null);
        request.setTags(invalidTags);

        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Invalid tag(s) in the blueprint: Unable to parse JSON.");

        underTest.convert(request);
    }

    @Test
    public void testConvertWhenServiceListIsNull() {
        BlueprintV4Request request = getRequest("blueprint.json");
        request.setServices(null);
        Blueprint result = underTest.convert(request);
        assertAllFieldsNotNull(result);
        assertEquals("{}", result.getTags().getValue());
        assertEquals("HDP", result.getStackType());
        assertEquals("2.3", result.getStackVersion());
    }

    @Test
    public void testConvertWhenServiceListIsEmpty() {
        BlueprintV4Request request = getRequest("blueprint.json");
        request.setServices(new HashSet<>());
        Blueprint result = underTest.convert(request);
        assertAllFieldsNotNull(result);
        assertEquals("{}", result.getTags().getValue());
        assertEquals("HDP", result.getStackType());
        assertEquals("2.3", result.getStackVersion());
    }

    @Test
    public void testConvertWithOneService() {
        BlueprintV4Request request = getRequest("blueprint.json");
        String blueprint = request.getBlueprint();
        Set<String> services = Set.of("DATANODE");
        String platform = "platform";
        GeneratedCmTemplate generatedCmTemplate = mock(GeneratedCmTemplate.class);
        when(generatedCmTemplate.getTemplate()).thenReturn(blueprint);
        when(clusterTemplateGeneratorService.generateTemplateByServices(services, platform)).thenReturn(generatedCmTemplate);
        request.setServices(services);
        request.setPlatform(platform);

        Blueprint result = underTest.convert(request);

        assertAllFieldsNotNull(result);
        assertEquals(blueprint, result.getBlueprintText());
    }

    @Test(expected = BadRequestException.class)
    public void testWithInvalidDashInHostgroupName() {
        underTest.convert(getRequest("blueprint-hostgroup-name-with-dash.json"));
    }

    @Test
    public void testWithInvalidUnderscoreInHostgroupName() {
        Blueprint result = underTest.convert(getRequest("blueprint-hostgroup-name-with-underscore.json"));
        assertNotNull(result);
    }

    @Override
    public Class<BlueprintV4Request> getRequestClass() {
        return BlueprintV4Request.class;
    }

}
