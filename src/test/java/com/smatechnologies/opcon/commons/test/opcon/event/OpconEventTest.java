package com.smatechnologies.opcon.commons.test.opcon.event;

import com.smatechnologies.opcon.commons.opcon.event.OpconEvent;
import com.smatechnologies.opcon.commons.opcon.event.OpconEventException;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpconEventTest extends AbstractOpconEventParameterTemplateTest {

    @Test(expected = NullPointerException.class)
    public void test01Null() throws OpconEventException {
        new OpconEvent(null);
    }

    @Test(expected = NullPointerException.class)
    public void test02Null() throws OpconEventException {
        new OpconEvent(null, null);
    }

    @Test
    public void test03NoParameter() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT1:ACT1"));

        Assert.assertEquals("$CAT1:ACT1", event.toString());
        event.validate();
    }

    @Test
    public void test04NoParameter() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT1:ACT1"), null);

        Assert.assertEquals("$CAT1:ACT1", event.toString());
        event.validate();
    }

    @Test
    public void test05NoParameter() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT1:ACT1"), new ArrayList<>());

        Assert.assertEquals("$CAT1:ACT1", event.toString());
        event.validate();
    }

    @Test(expected = OpconEventException.class)
    public void test06NoParameterInvalid() throws OpconEventException {
        new OpconEvent(registry.getEventTemplateByCommand("$CAT1:ACT1"), Arrays.asList("param1"));
    }

    @Test
    public void test07EmptyParameter() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT1:ACT2"));

        Assert.assertEquals("$CAT1:ACT2", event.toString());
        event.validate();
    }

    @Test
    public void test08EmptyParameter() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT1:ACT2"), null);

        Assert.assertEquals("$CAT1:ACT2", event.toString());
        event.validate();
    }

    @Test
    public void test09EmptyParameter() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT1:ACT2"), new ArrayList<>());

        Assert.assertEquals("$CAT1:ACT2", event.toString());
        event.validate();
    }

    @Test(expected = OpconEventException.class)
    public void test10EmptyParameterInvalid() throws OpconEventException {
        new OpconEvent(registry.getEventTemplateByCommand("$CAT1:ACT2"), Arrays.asList("param1"));
    }

    @Test(expected = OpconEventException.class)
    public void test11ParametersBadNumber() throws OpconEventException {
        new OpconEvent(registry.getEventTemplateByCommand("$CAT2:ACT1"), null);
    }

    @Test(expected = OpconEventException.class)
    public void test12ParametersBadNumber() throws OpconEventException {
        new OpconEvent(registry.getEventTemplateByCommand("$CAT2:ACT1"), new ArrayList<>());
    }

    @Test(expected = OpconEventException.class)
    public void test13ParametersBadNumber() throws OpconEventException {
        new OpconEvent(registry.getEventTemplateByCommand("$CAT2:ACT1"), Arrays.asList("param1", "param2", "param3", "param4"));
    }

    @Test
    public void test14Parameters() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT2:ACT1"));

        Assert.assertEquals("$CAT2:ACT1,,,", event.toString());
        try {
            event.validate();
            Assert.fail("Should throw Exception");
        } catch (OpconEventParameterException e) {
        }

        try {
            event.getParameter(-1).setString("parameter");
            Assert.fail("Should throw Exception");
        } catch (IndexOutOfBoundsException e) {
        }
        Assert.assertEquals("", event.getParameter(0).toString());
        Assert.assertEquals("", event.getParameter(1).toString());
        Assert.assertEquals("", event.getParameter(2).toString());
        try {
            event.getParameter(3).setString("parameter");
            Assert.fail("Should throw Exception");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void test15Parameters() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT2:ACT1"), Arrays.asList("param1", "param2"));

        Assert.assertEquals("$CAT2:ACT1,param1,param2,", event.toString());
        event.validate();

        try {
            event.getParameter(-1).setString("parameter");
            Assert.fail("Should throw Exception");
        } catch (IndexOutOfBoundsException e) {
        }
        Assert.assertEquals("param1", event.getParameter(0).toString());
        Assert.assertEquals("param2", event.getParameter(1).toString());
        Assert.assertEquals("", event.getParameter(2).toString());
        try {
            event.getParameter(3).setString("parameter");
            Assert.fail("Should throw Exception");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void test16Parameters() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT2:ACT1"), Arrays.asList("param1", "param2", "param3"));

        Assert.assertEquals("$CAT2:ACT1,param1,param2,param3", event.toString());
        event.validate();

        try {
            event.getParameter(-1).setString("parameter");
            Assert.fail("Should throw Exception");
        } catch (IndexOutOfBoundsException e) {
        }
        Assert.assertEquals("param1", event.getParameter(0).toString());
        Assert.assertEquals("param2", event.getParameter(1).toString());
        Assert.assertEquals("param3", event.getParameter(2).toString());
        try {
            event.getParameter(3).setString("parameter");
            Assert.fail("Should throw Exception");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test(expected = OpconEventParameterException.class)
    public void test17ParametersBad() throws OpconEventException {
        new OpconEvent(registry.getEventTemplateByCommand("$CAT2:ACT1"), Arrays.asList("param1", "param2,", "param3"));
    }

    @Test
    public void test18ParametersBad() throws OpconEventException {
        OpconEvent event = new OpconEvent(registry.getEventTemplateByCommand("$CAT2:ACT1"), Arrays.asList("param1", "param2", "param3"));

        event.getParameter(1).setString("param2,");

        Assert.assertEquals("$CAT2:ACT1,param1,param2,,param3", event.toString());
        try {
            event.validate();
            Assert.fail("Should throw Exception");
        } catch (OpconEventParameterException e) {
        }

        try {
            event.getParameter(-1).setString("parameter");
            Assert.fail("Should throw Exception");
        } catch (IndexOutOfBoundsException e) {
        }
        Assert.assertEquals("param1", event.getParameter(0).toString());
        Assert.assertEquals("param2,", event.getParameter(1).toString());
        Assert.assertEquals("param3", event.getParameter(2).toString());
        try {
            event.getParameter(3).setString("parameter");
            Assert.fail("Should throw Exception");
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
