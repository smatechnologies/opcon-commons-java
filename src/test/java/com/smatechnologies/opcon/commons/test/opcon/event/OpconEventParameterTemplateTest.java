package com.smatechnologies.opcon.commons.test.opcon.event;

import com.smatechnologies.opcon.commons.opcon.event.OpconEventException;
import com.smatechnologies.opcon.commons.opcon.event.template.OpconEventParameterTemplate;
import com.smatechnologies.opcon.commons.opcon.event.template.OpconEventParameterType;
import com.smatechnologies.opcon.commons.opcon.event.template.OpconEventTemplate;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpconEventParameterTemplateTest extends AbstractOpconEventParameterTemplateTest {

    @Test
    public void test01CountTemplates() throws OpconEventException {
        List<OpconEventTemplate> eventTemplates = registry.getEventTemplates();
        Assert.assertEquals(4, eventTemplates.size());
    }

    @Test
    public void test02CommandNotExists() throws OpconEventException {
        OpconEventTemplate opconEventTemplate = registry.getEventTemplateByCommand("$CAT0:ACT0");

        Assert.assertNull(opconEventTemplate);
    }

    @Test
    public void test03EventTemplateNoParameter() throws OpconEventException {
        OpconEventTemplate opconEventTemplate = registry.getEventTemplateByCommand("$CAT1:ACT1");

        Assert.assertEquals("CAT1", opconEventTemplate.getCategory());
        Assert.assertEquals("ACT1", opconEventTemplate.getAction());
        Assert.assertEquals("$CAT1:ACT1", opconEventTemplate.getCommand());
        Assert.assertEquals(0, opconEventTemplate.getMinimumNumberOfParameters());
        Assert.assertEquals(0, opconEventTemplate.getMaximumNumberOfParameters());
        Assert.assertEquals(null, opconEventTemplate.getParameters());
        Assert.assertEquals("$CAT1:ACT1", opconEventTemplate.toString());
    }

    @Test
    public void test04EventTemplateEmptyParameters() throws OpconEventException {
        OpconEventTemplate opconEventTemplate = registry.getEventTemplateByCommand("$CAT1:ACT2");

        Assert.assertEquals("CAT1", opconEventTemplate.getCategory());
        Assert.assertEquals("ACT2", opconEventTemplate.getAction());
        Assert.assertEquals("$CAT1:ACT2", opconEventTemplate.getCommand());
        Assert.assertEquals(0, opconEventTemplate.getMinimumNumberOfParameters());
        Assert.assertEquals(0, opconEventTemplate.getMaximumNumberOfParameters());
        Assert.assertEquals(null, opconEventTemplate.getParameters());
        Assert.assertEquals("$CAT1:ACT2", opconEventTemplate.toString());
    }

    @Test
    public void test05EventTemplateParameters() throws OpconEventException {
        OpconEventTemplate opconEventTemplate = registry.getEventTemplateByCommand("$CAT2:ACT1");

        Assert.assertEquals("CAT2", opconEventTemplate.getCategory());
        Assert.assertEquals("ACT1", opconEventTemplate.getAction());
        Assert.assertEquals("$CAT2:ACT1", opconEventTemplate.getCommand());
        Assert.assertEquals(1, opconEventTemplate.getMinimumNumberOfParameters());
        Assert.assertEquals(3, opconEventTemplate.getMaximumNumberOfParameters());
        Assert.assertEquals(3, opconEventTemplate.getParameters().size());
        Assert.assertEquals("$CAT2:ACT1,<param1>,[param2],[param3]", opconEventTemplate.toString());

        OpconEventParameterTemplate param1 = opconEventTemplate.getParameter(0);
        Assert.assertEquals("param1", param1.getName());
        Assert.assertEquals(OpconEventParameterType.TEXT, param1.getType());
        Assert.assertEquals(false, param1.isOptional());
        Assert.assertEquals(false, param1.hasChoice());
        Assert.assertEquals(null, param1.getChoices());

        OpconEventParameterTemplate param2 = opconEventTemplate.getParameter(1);
        Assert.assertEquals("param2", param2.getName());
        Assert.assertEquals(OpconEventParameterType.INT, param2.getType());
        Assert.assertEquals(true, param2.isOptional());
        Assert.assertEquals(false, param2.hasChoice());
        Assert.assertEquals(null, param2.getChoices());

        OpconEventParameterTemplate param3 = opconEventTemplate.getParameter(2);
        Assert.assertEquals("param3", param3.getName());
        Assert.assertEquals(OpconEventParameterType.DATE, param3.getType());
        Assert.assertEquals(true, param3.isOptional());
        Assert.assertEquals(false, param3.hasChoice());
        Assert.assertEquals(null, param3.getChoices());
    }

    @Test
    public void test06EventTemplateParametersChoice() throws OpconEventException {
        OpconEventTemplate opconEventTemplate = registry.getEventTemplateByCommand("$CAT2:ACT2");

        Assert.assertEquals("CAT2", opconEventTemplate.getCategory());
        Assert.assertEquals("ACT2", opconEventTemplate.getAction());
        Assert.assertEquals("$CAT2:ACT2", opconEventTemplate.getCommand());
        Assert.assertEquals(2, opconEventTemplate.getMinimumNumberOfParameters());
        Assert.assertEquals(2, opconEventTemplate.getMaximumNumberOfParameters());
        Assert.assertEquals(2, opconEventTemplate.getParameters().size());
        Assert.assertEquals("$CAT2:ACT2,<param1>,<param2>", opconEventTemplate.toString());

        OpconEventParameterTemplate param1 = opconEventTemplate.getParameter(0);
        Assert.assertEquals("param1", param1.getName());
        Assert.assertEquals(OpconEventParameterType.TEXT, param1.getType());
        Assert.assertEquals(false, param1.isOptional());
        Assert.assertEquals(true, param1.hasChoice());
        Assert.assertEquals("[Choice 1=C1, Choice 2=C2, Choice 3=C3]", param1.getChoices().toString());

        OpconEventParameterTemplate param2 = opconEventTemplate.getParameter(1);
        Assert.assertEquals("param2", param2.getName());
        Assert.assertEquals(OpconEventParameterType.TEXT, param2.getType());
        Assert.assertEquals(false, param2.isOptional());
        Assert.assertEquals(false, param2.hasChoice());
        Assert.assertEquals(null, param2.getChoices());
    }
}
