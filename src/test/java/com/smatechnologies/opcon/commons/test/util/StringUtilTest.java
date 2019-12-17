package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.StringUtil;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StringUtilTest {

    @Test
    public void test01HasContent() {
        Assert.assertEquals(false, StringUtil.hasContent(null));
        Assert.assertEquals(false, StringUtil.hasContent(""));
        Assert.assertEquals(true, StringUtil.hasContent("foo"));
    }

    @Test
    public void test02IsNullOrEmpty() {
        Assert.assertEquals(true, StringUtil.isNullOrEmpty(null));
        Assert.assertEquals(true, StringUtil.isNullOrEmpty(""));
        Assert.assertEquals(false, StringUtil.isNullOrEmpty("foo"));
    }

    @Test
    public void test03nullToEmpty() {
        Assert.assertEquals("", StringUtil.nullToEmpty(null));
        Assert.assertEquals("", StringUtil.nullToEmpty(""));
        Assert.assertEquals("foo", StringUtil.nullToEmpty("foo"));
    }

    @Test
    public void test04emptyToNull() {
        Assert.assertEquals(null, StringUtil.emptyToNull(null));
        Assert.assertEquals(null, StringUtil.emptyToNull(""));
        Assert.assertEquals("foo", StringUtil.emptyToNull("foo"));
    }

    @Test
    public void test05ContainsIllegals() {
        try {
            StringUtil.containsIllegals(null, "ghijkl");
            Assert.fail("Should throw exception");
        } catch (NullPointerException e) {
        }
        try {
            Assert.assertEquals(false, StringUtil.containsIllegals(null, null));
            Assert.fail("Should throw exception");
        } catch (NullPointerException e) {
        }
        Assert.assertEquals(false, StringUtil.containsIllegals("abcdef", null));
        Assert.assertEquals(false, StringUtil.containsIllegals("abcdef", "ghijkl"));
        Assert.assertEquals(true, StringUtil.containsIllegals("abcdef", "ahijkl"));
        Assert.assertEquals(true, StringUtil.containsIllegals("abcdef", "ghickl"));
        Assert.assertEquals(true, StringUtil.containsIllegals("abcdef", "ghijkf"));
    }

    @Test
    public void test06CapitalizeFirstLetter() {
        Assert.assertEquals(null, StringUtil.capitalizeFirstLetter(null));
        Assert.assertEquals("", StringUtil.capitalizeFirstLetter(""));
        Assert.assertEquals("H", StringUtil.capitalizeFirstLetter("h"));
        Assert.assertEquals("H", StringUtil.capitalizeFirstLetter("H"));
        Assert.assertEquals("Hello", StringUtil.capitalizeFirstLetter("Hello"));
        Assert.assertEquals("Hello", StringUtil.capitalizeFirstLetter("hello"));
        Assert.assertEquals("HeLlO", StringUtil.capitalizeFirstLetter("heLlO"));
        Assert.assertEquals("HeLlO", StringUtil.capitalizeFirstLetter("heLlO"));
    }

    @Test
    public void test07ContainsDosWildcard() {
        Assert.assertEquals(false, StringUtil.containsDosWildcard(null, false));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("", false));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("hi", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("=", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("*", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("?", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("=a", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("*a", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("?a", false));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("a=", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("a*", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("a?", false));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("a=b", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("a*b", false));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("a?b", false));

        Assert.assertEquals(false, StringUtil.containsDosWildcard(null, true));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("", true));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("hi", true));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("=", true));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("*", true));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("?", true));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("=a", true));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("*a", true));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("?a", true));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("a=", true));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("a*", true));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("a?", true));
        Assert.assertEquals(false, StringUtil.containsDosWildcard("a=b", true));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("a*b", true));
        Assert.assertEquals(true, StringUtil.containsDosWildcard("a?b", true));
    }

    @Test
    public void test08ConvertDosWildcardStringToSqlServerLikeString(){
        Assert.assertEquals(null, StringUtil.convertDosWildcardStringToSqlServerLikeString(null, '\\', false));

        Assert.assertEquals("%", StringUtil.convertDosWildcardStringToSqlServerLikeString("", '\\', false));
        Assert.assertEquals("%abcdef%", StringUtil.convertDosWildcardStringToSqlServerLikeString("abcdef", '\\', false));
        Assert.assertEquals("%\\%\\_\\[\\]%", StringUtil.convertDosWildcardStringToSqlServerLikeString("%_[]", '\\', false));
        Assert.assertEquals("%abc%def%", StringUtil.convertDosWildcardStringToSqlServerLikeString("abc*def", '\\', false));
        Assert.assertEquals("%abc_def%", StringUtil.convertDosWildcardStringToSqlServerLikeString("abc?def", '\\', false));
        Assert.assertEquals("%%\\\\%%", StringUtil.convertDosWildcardStringToSqlServerLikeString("*\\*", '\\', false));

        Assert.assertEquals("", StringUtil.convertDosWildcardStringToSqlServerLikeString("=", '\\', false));
        Assert.assertEquals("abcdef", StringUtil.convertDosWildcardStringToSqlServerLikeString("=abcdef", '\\', false));
        Assert.assertEquals("\\%\\_\\[\\]", StringUtil.convertDosWildcardStringToSqlServerLikeString("=%_[]", '\\', false));
        Assert.assertEquals("abc%def", StringUtil.convertDosWildcardStringToSqlServerLikeString("=abc*def", '\\', false));
        Assert.assertEquals("abc_def", StringUtil.convertDosWildcardStringToSqlServerLikeString("=abc?def", '\\', false));
        Assert.assertEquals("%\\\\%", StringUtil.convertDosWildcardStringToSqlServerLikeString("=*\\*", '\\', false));



        Assert.assertEquals(null, StringUtil.convertDosWildcardStringToSqlServerLikeString(null, '\\', true));

        Assert.assertEquals("", StringUtil.convertDosWildcardStringToSqlServerLikeString("", '\\', true));
        Assert.assertEquals("abcdef", StringUtil.convertDosWildcardStringToSqlServerLikeString("abcdef", '\\', true));
        Assert.assertEquals("\\%\\_\\[\\]", StringUtil.convertDosWildcardStringToSqlServerLikeString("%_[]", '\\', true));
        Assert.assertEquals("abc%def", StringUtil.convertDosWildcardStringToSqlServerLikeString("abc*def", '\\', true));
        Assert.assertEquals("abc_def", StringUtil.convertDosWildcardStringToSqlServerLikeString("abc?def", '\\', true));
        Assert.assertEquals("%\\\\%", StringUtil.convertDosWildcardStringToSqlServerLikeString("*\\*", '\\', true));

        Assert.assertEquals("=", StringUtil.convertDosWildcardStringToSqlServerLikeString("=", '\\', true));
        Assert.assertEquals("=abcdef", StringUtil.convertDosWildcardStringToSqlServerLikeString("=abcdef", '\\', true));
        Assert.assertEquals("=\\%\\_\\[\\]", StringUtil.convertDosWildcardStringToSqlServerLikeString("=%_[]", '\\', true));
        Assert.assertEquals("=abc%def", StringUtil.convertDosWildcardStringToSqlServerLikeString("=abc*def", '\\', true));
        Assert.assertEquals("=abc_def", StringUtil.convertDosWildcardStringToSqlServerLikeString("=abc?def", '\\', true));
        Assert.assertEquals("=%\\\\%", StringUtil.convertDosWildcardStringToSqlServerLikeString("=*\\*", '\\', true));
    }

    @Test
    public void test09ConvertDosWildcardStringToRegex() {
        Assert.assertEquals(null, StringUtil.convertDosWildcardStringToRegex(null, false));

        Assert.assertEquals(true, "".matches(StringUtil.convertDosWildcardStringToRegex("", false)));
        Assert.assertEquals(true, "xx".matches(StringUtil.convertDosWildcardStringToRegex("", false)));

        Assert.assertEquals(true, "te.st".matches(StringUtil.convertDosWildcardStringToRegex("te.st", false)));
        Assert.assertEquals(true, "xte.stx".matches(StringUtil.convertDosWildcardStringToRegex("te.st", false)));
        Assert.assertEquals(false, "teXst".matches(StringUtil.convertDosWildcardStringToRegex("te.st", false)));

        Assert.assertEquals(true, "teXst".matches(StringUtil.convertDosWildcardStringToRegex("te?st", false)));
        Assert.assertEquals(true, "xteXstx".matches(StringUtil.convertDosWildcardStringToRegex("te?st", false)));
        Assert.assertEquals(true, "te?st".matches(StringUtil.convertDosWildcardStringToRegex("te?st", false)));
        Assert.assertEquals(true, "xte?stx".matches(StringUtil.convertDosWildcardStringToRegex("te?st", false)));
        Assert.assertEquals(false, "test".matches(StringUtil.convertDosWildcardStringToRegex("te?st", false)));
        Assert.assertEquals(false, "teXXst".matches(StringUtil.convertDosWildcardStringToRegex("te?st", false)));

        Assert.assertEquals(true, "testtest".matches(StringUtil.convertDosWildcardStringToRegex("test*test", false)));
        Assert.assertEquals(true, "xtesttestx".matches(StringUtil.convertDosWildcardStringToRegex("test*test", false)));
        Assert.assertEquals(true, "testsomethingtest".matches(StringUtil.convertDosWildcardStringToRegex("test*test", false)));
        Assert.assertEquals(true, "xtestsomethingtestx".matches(StringUtil.convertDosWildcardStringToRegex("test*test", false)));
        Assert.assertEquals(true, "test.somethingtest".matches(StringUtil.convertDosWildcardStringToRegex("test.*test", false)));
        Assert.assertEquals(true, "xtest.somethingtestx".matches(StringUtil.convertDosWildcardStringToRegex("test.*test", false)));
        Assert.assertEquals(false, "testXsomethingtest".matches(StringUtil.convertDosWildcardStringToRegex("test.*test", false)));


        Assert.assertEquals(true, "".matches(StringUtil.convertDosWildcardStringToRegex("=", false)));
        Assert.assertEquals(false, "xx".matches(StringUtil.convertDosWildcardStringToRegex("=", false)));

        Assert.assertEquals(true, "te.st".matches(StringUtil.convertDosWildcardStringToRegex("=te.st", false)));
        Assert.assertEquals(false, "xte.stx".matches(StringUtil.convertDosWildcardStringToRegex("=te.st", false)));
        Assert.assertEquals(false, "teXst".matches(StringUtil.convertDosWildcardStringToRegex("=te.st", false)));

        Assert.assertEquals(true, "teXst".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", false)));
        Assert.assertEquals(false, "xteXstx".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", false)));
        Assert.assertEquals(true, "te?st".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", false)));
        Assert.assertEquals(false, "xte?stx".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", false)));
        Assert.assertEquals(false, "test".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", false)));
        Assert.assertEquals(false, "teXXst".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", false)));

        Assert.assertEquals(true, "testtest".matches(StringUtil.convertDosWildcardStringToRegex("=test*test", false)));
        Assert.assertEquals(false, "xtesttestx".matches(StringUtil.convertDosWildcardStringToRegex("=test*test", false)));
        Assert.assertEquals(true, "testsomethingtest".matches(StringUtil.convertDosWildcardStringToRegex("=test*test", false)));
        Assert.assertEquals(false, "xtestsomethingtestx".matches(StringUtil.convertDosWildcardStringToRegex("=test*test", false)));
        Assert.assertEquals(true, "test.somethingtest".matches(StringUtil.convertDosWildcardStringToRegex("=test.*test", false)));
        Assert.assertEquals(false, "xtest.somethingtestx".matches(StringUtil.convertDosWildcardStringToRegex("=test.*test", false)));
        Assert.assertEquals(false, "testXsomethingtest".matches(StringUtil.convertDosWildcardStringToRegex("=test.*test", false)));



        Assert.assertEquals(null, StringUtil.convertDosWildcardStringToRegex(null, true));

        Assert.assertEquals(true, "".matches(StringUtil.convertDosWildcardStringToRegex("", true)));
        Assert.assertEquals(false, "xx".matches(StringUtil.convertDosWildcardStringToRegex("", true)));

        Assert.assertEquals(true, "te.st".matches(StringUtil.convertDosWildcardStringToRegex("te.st", true)));
        Assert.assertEquals(false, "xte.stx".matches(StringUtil.convertDosWildcardStringToRegex("te.st", true)));
        Assert.assertEquals(false, "teXst".matches(StringUtil.convertDosWildcardStringToRegex("te.st", true)));

        Assert.assertEquals(true, "teXst".matches(StringUtil.convertDosWildcardStringToRegex("te?st", true)));
        Assert.assertEquals(false, "xteXstx".matches(StringUtil.convertDosWildcardStringToRegex("te?st", true)));
        Assert.assertEquals(true, "te?st".matches(StringUtil.convertDosWildcardStringToRegex("te?st", true)));
        Assert.assertEquals(false, "xte?stx".matches(StringUtil.convertDosWildcardStringToRegex("te?st", true)));
        Assert.assertEquals(false, "test".matches(StringUtil.convertDosWildcardStringToRegex("te?st", true)));
        Assert.assertEquals(false, "teXXst".matches(StringUtil.convertDosWildcardStringToRegex("te?st", true)));

        Assert.assertEquals(true, "testtest".matches(StringUtil.convertDosWildcardStringToRegex("test*test", true)));
        Assert.assertEquals(false, "xtesttestx".matches(StringUtil.convertDosWildcardStringToRegex("test*test", true)));
        Assert.assertEquals(true, "testsomethingtest".matches(StringUtil.convertDosWildcardStringToRegex("test*test", true)));
        Assert.assertEquals(false, "xtestsomethingtestx".matches(StringUtil.convertDosWildcardStringToRegex("test*test", true)));
        Assert.assertEquals(true, "test.somethingtest".matches(StringUtil.convertDosWildcardStringToRegex("test.*test", true)));
        Assert.assertEquals(false, "xtest.somethingtestx".matches(StringUtil.convertDosWildcardStringToRegex("test.*test", true)));
        Assert.assertEquals(false, "testXsomethingtest".matches(StringUtil.convertDosWildcardStringToRegex("test.*test", true)));


        Assert.assertEquals(false, "".matches(StringUtil.convertDosWildcardStringToRegex("=", true)));
        Assert.assertEquals(false, "xx".matches(StringUtil.convertDosWildcardStringToRegex("=", true)));

        Assert.assertEquals(false, "te.st".matches(StringUtil.convertDosWildcardStringToRegex("=te.st", true)));
        Assert.assertEquals(false, "xte.stx".matches(StringUtil.convertDosWildcardStringToRegex("=te.st", true)));
        Assert.assertEquals(false, "teXst".matches(StringUtil.convertDosWildcardStringToRegex("=te.st", true)));

        Assert.assertEquals(false, "teXst".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", true)));
        Assert.assertEquals(false, "xteXstx".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", true)));
        Assert.assertEquals(false, "te?st".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", true)));
        Assert.assertEquals(false, "xte?stx".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", true)));
        Assert.assertEquals(false, "test".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", true)));
        Assert.assertEquals(false, "teXXst".matches(StringUtil.convertDosWildcardStringToRegex("=te?st", true)));

        Assert.assertEquals(false, "testtest".matches(StringUtil.convertDosWildcardStringToRegex("=test*test", true)));
        Assert.assertEquals(false, "xtesttestx".matches(StringUtil.convertDosWildcardStringToRegex("=test*test", true)));
        Assert.assertEquals(false, "testsomethingtest".matches(StringUtil.convertDosWildcardStringToRegex("=test*test", true)));
        Assert.assertEquals(false, "xtestsomethingtestx".matches(StringUtil.convertDosWildcardStringToRegex("=test*test", true)));
        Assert.assertEquals(false, "test.somethingtest".matches(StringUtil.convertDosWildcardStringToRegex("=test.*test", true)));
        Assert.assertEquals(false, "xtest.somethingtestx".matches(StringUtil.convertDosWildcardStringToRegex("=test.*test", true)));
        Assert.assertEquals(false, "testXsomethingtest".matches(StringUtil.convertDosWildcardStringToRegex("=test.*test", true)));
    }

    @Test
    public void test10ConvertDosWildcardStringToOpconApiString() {
        Assert.assertEquals(null, StringUtil.convertDosWildcardStringToOpconApiString(null, false));

        Assert.assertEquals("*", StringUtil.convertDosWildcardStringToOpconApiString("", false));

        Assert.assertEquals("*abcdef*", StringUtil.convertDosWildcardStringToOpconApiString("abcdef", false));
        Assert.assertEquals("*abc*def*", StringUtil.convertDosWildcardStringToOpconApiString("abc*def", false));
        Assert.assertEquals("*abc?def*", StringUtil.convertDosWildcardStringToOpconApiString("abc?def", false));

        Assert.assertEquals("abcdef", StringUtil.convertDosWildcardStringToOpconApiString("=abcdef", false));
        Assert.assertEquals("abc*def", StringUtil.convertDosWildcardStringToOpconApiString("=abc*def", false));
        Assert.assertEquals("abc?def", StringUtil.convertDosWildcardStringToOpconApiString("=abc?def", false));



        Assert.assertEquals(null, StringUtil.convertDosWildcardStringToOpconApiString(null, true));

        Assert.assertEquals("", StringUtil.convertDosWildcardStringToOpconApiString("", true));

        Assert.assertEquals("abcdef", StringUtil.convertDosWildcardStringToOpconApiString("abcdef", true));
        Assert.assertEquals("abc*def", StringUtil.convertDosWildcardStringToOpconApiString("abc*def", true));
        Assert.assertEquals("abc?def", StringUtil.convertDosWildcardStringToOpconApiString("abc?def", true));

        Assert.assertEquals("=abcdef", StringUtil.convertDosWildcardStringToOpconApiString("=abcdef", true));
        Assert.assertEquals("=abc*def", StringUtil.convertDosWildcardStringToOpconApiString("=abc*def", true));
        Assert.assertEquals("=abc?def", StringUtil.convertDosWildcardStringToOpconApiString("=abc?def", true));
    }

    @Test
    public void test11urlEncode() {
        Assert.assertEquals("", StringUtil.urlEncode(""));
        Assert.assertEquals("%20", StringUtil.urlEncode(" "));
        Assert.assertEquals(null, StringUtil.urlEncode(null));
        Assert.assertEquals("Hello%20world%21", StringUtil.urlEncode("Hello world!"));
        Assert.assertEquals("%20%3C%3E%27%23%25%7B%7D%7C%22%5E%2C%7E%5B%5D%60%3B%2F%3F%3A%40%3D%26%5C%2B%24", StringUtil.urlEncode(" <>'#%{}|\"^,~[]`;/?:@=&\\+$"));
    }


    @Test
    public void test12urlEncodeCurlyBraces() {
        Assert.assertEquals(" ", StringUtil.urlEncodeCurlyBraces(" "));
        Assert.assertEquals(null, StringUtil.urlEncodeCurlyBraces(null));
        Assert.assertEquals("%7B", StringUtil.urlEncodeCurlyBraces("{"));
        Assert.assertEquals("%7D", StringUtil.urlEncodeCurlyBraces("}"));
        Assert.assertEquals("%7B%7B", StringUtil.urlEncodeCurlyBraces("{{"));
        Assert.assertEquals("%7D%7D", StringUtil.urlEncodeCurlyBraces("}}"));
        Assert.assertEquals("%7Ba%7D", StringUtil.urlEncodeCurlyBraces("{a}"));
        Assert.assertEquals("%7B%7Bb%7D%7D", StringUtil.urlEncodeCurlyBraces("{{b}}"));
        Assert.assertEquals("8NAZK_%60~%21%40%23%24%25%5E%26%28%29-%2B%5B%5D%7B%7D%5C%7C%3B%3A%E2%80%99%E2%80%9D%3C%3E%2C.%2F", StringUtil.urlEncodeCurlyBraces("8NAZK_%60~%21%40%23%24%25%5E%26%28%29-%2B%5B%5D{}%5C%7C%3B%3A%E2%80%99%E2%80%9D%3C%3E%2C.%2F"));
    }

    @Test
    public void test13isInteger() {
        Assert.assertEquals(false, StringUtil.isInteger(null));
        Assert.assertEquals(false, StringUtil.isInteger(""));
        Assert.assertEquals(false, StringUtil.isInteger("a"));
        Assert.assertEquals(false, StringUtil.isInteger("1a"));
        Assert.assertEquals(false, StringUtil.isInteger("a2"));
        Assert.assertEquals(false, StringUtil.isInteger("1a2"));
        Assert.assertEquals(false, StringUtil.isInteger("1.2"));
        Assert.assertEquals(false, StringUtil.isInteger("1,2"));

        Assert.assertEquals(true, StringUtil.isInteger("1"));
        Assert.assertEquals(true, StringUtil.isInteger("2"));
        Assert.assertEquals(true, StringUtil.isInteger("12"));
    }
}
