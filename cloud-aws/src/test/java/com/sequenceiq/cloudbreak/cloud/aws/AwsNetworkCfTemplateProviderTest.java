package com.sequenceiq.cloudbreak.cloud.aws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.sequenceiq.cloudbreak.cloud.exception.CloudConnectorException;
import com.sequenceiq.cloudbreak.util.FreeMarkerTemplateUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@RunWith(MockitoJUnitRunner.class)
public class AwsNetworkCfTemplateProviderTest {

    public static final String VPC_CIDR = "0.0.0.0/16";

    public static final Set<String> SUBNET_CIRDS = Set.of("1.1.1.1/8", "1.1.1.2/8");

    public static final String TEMPLATE_PATH = "templates/aws-cf-network.ftl";

    @InjectMocks
    private AwsNetworkCfTemplateProvider underTest;

    @Mock
    private FreeMarkerTemplateUtils freeMarkerTemplateUtils;

    @Before
    public void before() throws IOException, TemplateException {
        FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
        factoryBean.setPreferFileSystemAccess(false);
        factoryBean.setTemplateLoaderPath("classpath:/");
        factoryBean.afterPropertiesSet();
        ReflectionTestUtils.setField(underTest, "freemarkerConfiguration", factoryBean.getObject());
        ReflectionTestUtils.setField(underTest, "cloudFormationNetworkTemplatePath", TEMPLATE_PATH);
    }

    @Test
    public void testProvideShouldReturnTheTemplate() throws IOException, TemplateException {
        String templateString = "template";

        when(freeMarkerTemplateUtils.processTemplateIntoString(any(Template.class), anyMap())).thenReturn(templateString);

        String actual = underTest.provide(VPC_CIDR, SUBNET_CIRDS);

        verify(freeMarkerTemplateUtils).processTemplateIntoString(any(Template.class), anyMap());
        assertEquals(templateString, actual);
    }

    @Test(expected = CloudConnectorException.class)
    public void testProvideShouldThrowExceptionWhenTemplateProcessHasFailed() throws IOException, TemplateException {
        when(freeMarkerTemplateUtils.processTemplateIntoString(any(Template.class), anyMap())).thenThrow(TemplateException.class);

        underTest.provide(VPC_CIDR, SUBNET_CIRDS);

        verify(freeMarkerTemplateUtils).processTemplateIntoString(any(Template.class), anyMap());
    }

}