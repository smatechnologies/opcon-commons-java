package com.smatechnologies.opcon.commons.test.opcon.util;

import com.smatechnologies.opcon.commons.opcon.util.VariableResolver;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VariableResolverUtilTest {

    private static final Map<String, String> VARS = new HashMap<>();
    static {
        VARS.put("foo", "bar");
        VARS.put("bar", "foo");
    }

    @Test
    public void test01SimpleSubstitutions() throws IOException {
        final String text = "I am ${bar} that likes to drink in ${foo}s";
        final String result = VariableResolver.substituteVariables(text, VARS);

        Assert.assertEquals("I am foo that likes to drink in bars", result);
    }

    @Test
    public void test02SimpleSubstitutions01() throws IOException {
        final String text = "${bar}${foo}s";
        final String result = VariableResolver.substituteVariables(text, VARS);

        Assert.assertEquals("foobars", result);
    }

    @Test
    public void test03UndefinedSubstitutions() throws IOException {
        Map<String, String> emptyMap = Collections.emptyMap();
        String text = "Does ${foo} = ${bar}?";
        final String result = VariableResolver.substituteVariables(text, emptyMap);
        Assert.assertEquals("Does ${foo} = ${bar}?", result);

        Map<String, String> onevar = Collections.singletonMap("bar", "margaritas");
        text = "Does ${foo} = ${bar}?";
        Assert.assertEquals("Does ${foo} = margaritas?", VariableResolver.substituteVariables(text, onevar));

        Map<String, String> allVars = new HashMap<>(onevar);
        allVars.put("foo", "Laurent");
        text = "Does ${foo} = ${bar}?";
        Assert.assertEquals("Does Laurent = margaritas?", VariableResolver.substituteVariables(text, allVars));
    }

    @Test
    public void test04FancyVarNames01() throws IOException {
        Map<String, String> onevar = Collections.singletonMap("replace this variable", "OK");
        String text = "${replace this variable}";
        Assert.assertEquals("OK", VariableResolver.substituteVariables(text, onevar));
    }

    @Test
    public void test05FancyVarNames02() throws IOException {
        Map<String, String> onevar = Collections.singletonMap("$tricky$", "OK");
        String text = "${$tricky$}";
        Assert.assertEquals("OK", VariableResolver.substituteVariables(text, onevar));
    }

    @Test
    public void test06FancyVarNames03() throws IOException {
        Map<String, String> onevar = Collections.singletonMap("$$$ tricky !@#$%^&*()", "OK");
        String text = "${$$$ tricky !@#$%^&*()}";
        Assert.assertEquals("OK", VariableResolver.substituteVariables(text, onevar));
    }

    @Test
    public void test07FancyVarNames04() throws IOException {
        Map<String, String> onevar = Collections.singletonMap("!@#$%^&*()_+{|:\"<>?~`", "OK");
        String text = "${!@#$%^&*()_+{|:\"<>?~`}";
        Assert.assertEquals("OK", VariableResolver.substituteVariables(text, onevar));
    }

    @Test
    public void test08FancyVarNames05() throws IOException {
        Map<String, String> onevar = Collections.singletonMap("12/34/56/78/9", "OK");
        String text = "${12/34/56/78/9}";
        Assert.assertEquals("OK", VariableResolver.substituteVariables(text, onevar));
    }

    @Test
    public void test09FancyVarNames06() throws IOException {
        Map<String, String> onevar = Collections.singletonMap("!@#$%^&*()_+{|:\"<>?~`", "OK");
        String text = "!@#$%^&*()_+{|:\"<>?~` ${!@#$%^&*()_+{|:\"<>?~`} !@#$%^&*()_+{|:\"<>?~`";
        Assert.assertEquals("!@#$%^&*()_+{|:\"<>?~` OK !@#$%^&*()_+{|:\"<>?~`", VariableResolver.substituteVariables(text, onevar));
    }

    @Test
    public void test10GetVariableExpression() {
        Assert.assertEquals(null, VariableResolver.getVariableExpression(null));
        Assert.assertEquals("", VariableResolver.getVariableExpression(""));
        Assert.assertEquals("${test}", VariableResolver.getVariableExpression("test"));
    }

    @Test
    public void test11GetVariableNames() {
        Assert.assertEquals(Collections.emptySet(), VariableResolver.getVariableNames(""));
        Assert.assertEquals(Collections.emptySet(), VariableResolver.getVariableNames("hi, how a you"));
        Assert.assertEquals(Stream.of("test1", "test2").collect(Collectors.toSet()), VariableResolver.getVariableNames("hi ${test1}, how a you, ${test2}?"));
    }

    @Test
    public void test12GetVariableNamesCollection() {
        Assert.assertEquals(Collections.emptySet(), VariableResolver.getVariableNames(Collections.emptyList()));
        Assert.assertEquals(Stream.of("test1", "test2", "test3").collect(Collectors.toSet()), VariableResolver.getVariableNames(Stream.of("test1", "hi ${test1}, how a you, ${test2}?", "${test3}").collect(Collectors.toSet())));
    }

    @Test
    public void test13SubstituteVariables() {
        Map<String, String> map = new HashMap<String, String>() {{
            put("test1", "red"); put("test3","white");
        }};

        Assert.assertEquals("", VariableResolver.substituteVariables("", map));
        Assert.assertEquals("hi, how a you?", VariableResolver.substituteVariables("hi, how a you?", map));
        Assert.assertEquals("hi red, how a you ${test2}", VariableResolver.substituteVariables("hi ${test1}, how a you ${test2}", map));
    }

    @Test
    public void test14SubstituteVariablesList() {
        Map<String, String> map = new HashMap<String, String>() {{
            put("test1", "red"); put("test3","white");
        }};

        Assert.assertEquals(Collections.emptyList(), VariableResolver.substituteVariables(Collections.emptyList(), map));
        Assert.assertEquals(Arrays.asList("hi, how a you?"), VariableResolver.substituteVariables(Collections.singletonList("hi, how a you?"), map));
        Assert.assertEquals(Arrays.asList("hi, how a you?", "hi red, how a you ${test2}", "white"), VariableResolver.substituteVariables(Arrays.asList("hi, how a you?", "hi ${test1}, how a you ${test2}", "${test3}"), map));
    }
}
