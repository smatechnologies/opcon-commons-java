package com.smatechnologies.opcon.commons.test.opcon.event;

import com.smatechnologies.opcon.commons.opcon.event.OpconEventException;
import com.smatechnologies.opcon.commons.opcon.event.OpconEventParser;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpconEventParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void test01CategoryAndActionToCommandNullCategoty() throws OpconEventException {
        OpconEventParser.categoryAndActionToCommand(null, "ACT2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test02CategoryAndActionToCommandNullAction() throws OpconEventException {
        OpconEventParser.categoryAndActionToCommand("CAT1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test03CategoryAndActionToCommandEmptyCategory() throws OpconEventException {
        OpconEventParser.categoryAndActionToCommand("", "ACT2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test04CategoryAndActionToCommandEmptyAction() throws OpconEventException {
        OpconEventParser.categoryAndActionToCommand("CAT1", "");
    }

    @Test
    public void test05CategoryAndActionToCommand() throws OpconEventException {
        Assert.assertEquals("$CAT1:ACT2", OpconEventParser.categoryAndActionToCommand("CAT1", "ACT2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test06ParameterToStringNullName() throws OpconEventException {
        OpconEventParser.parameterToString(null, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test07ParameterToStringEmptyName() throws OpconEventException {
        OpconEventParser.parameterToString("", false);
    }

    @Test
    public void test08ParameterToStringRequired() throws OpconEventException {
        Assert.assertEquals("<param1>", OpconEventParser.parameterToString("param1", false));
    }

    @Test
    public void test09ParameterToStringOptional() throws OpconEventException {
        Assert.assertEquals("[param1]", OpconEventParser.parameterToString("param1", true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test10CommandAndParametersToStringNullCommand() throws OpconEventException {
        OpconEventParser.commandAndParametersToString(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test11CommandAndParametersToStringEmptyCommand() throws OpconEventException {
        OpconEventParser.commandAndParametersToString("", null);
    }

    @Test
    public void test12CommandAndParametersToString() throws OpconEventException {
        Assert.assertEquals("$CAT1:ACT2", OpconEventParser.commandAndParametersToString("$CAT1:ACT2", null));
    }

    @Test
    public void test13CommandAndParametersToString() throws OpconEventException {
        Assert.assertEquals("$CAT1:ACT2", OpconEventParser.commandAndParametersToString("$CAT1:ACT2", Arrays.asList()));
    }

    @Test
    public void test14CommandAndParametersToString() throws OpconEventException {
        Assert.assertEquals("$CAT1:ACT2,<param1>,[param2]", OpconEventParser.commandAndParametersToString("$CAT1:ACT2", Arrays.asList("<param1>", "[param2]")));
    }

    @Test(expected = OpconEventException.class)
    public void test15ParserInvalidCommand() throws OpconEventException {
        new OpconEventParser("CAT1:ACT2");
    }

    @Test
    public void test16ParserNoParameter() throws OpconEventException {
        OpconEventParser opconEventParser = new OpconEventParser("$CAT1:ACT2");

        Assert.assertEquals("$CAT1:ACT2", opconEventParser.getCommand());
        Assert.assertEquals(null, opconEventParser.getParameters());
    }

    @Test
    public void test17ParserEmptyParameter() throws OpconEventException {
        OpconEventParser opconEventParser = new OpconEventParser("$CAT1:ACT2,");

        Assert.assertEquals("$CAT1:ACT2", opconEventParser.getCommand());
        Assert.assertEquals(1, opconEventParser.getParameters().size());
    }

    @Test
    public void test18ParserParameter() throws OpconEventException {
        OpconEventParser opconEventParser = new OpconEventParser("$CAT1:ACT2,param1");

        Assert.assertEquals("$CAT1:ACT2", opconEventParser.getCommand());
        Assert.assertEquals(1, opconEventParser.getParameters().size());
    }

    @Test
    public void test19ParserParameters() throws OpconEventException {
        OpconEventParser opconEventParser = new OpconEventParser("$CAT1:ACT2,param1,param2");

        Assert.assertEquals("$CAT1:ACT2", opconEventParser.getCommand());
        Assert.assertEquals(2, opconEventParser.getParameters().size());
    }
}
