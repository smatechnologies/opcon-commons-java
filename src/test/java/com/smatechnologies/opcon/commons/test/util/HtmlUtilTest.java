package com.smatechnologies.opcon.commons.test.util;

import com.smatechnologies.opcon.commons.util.HtmlUtil;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HtmlUtilTest {

    @Test
    public void test01ConvertTextToHtml() {
        Assert.assertEquals("azerty &eacute;&agrave;&egrave;<br/>&lt;tag&gt;", HtmlUtil.convertTextToHtml("azerty éàè\n<tag>"));
    }

    @Test
    public void test02ConvertTextWithUrlsToHtml() {
        Assert.assertEquals(null, HtmlUtil.convertTextWithUrlsToHtml(null, null));
        Assert.assertEquals("", HtmlUtil.convertTextWithUrlsToHtml("", null));

        Assert.assertEquals("<a href=\"https://exam-ple.com\">https://exam-ple.com</a>", HtmlUtil.convertTextWithUrlsToHtml("https://exam-ple.com", null));
        Assert.assertEquals("<a href=\"HTTP://exam-ple.com\">HTTP://exam-ple.com</a>", HtmlUtil.convertTextWithUrlsToHtml("HTTP://exam-ple.com", null));
        Assert.assertEquals("<a href=\"ftp://exam-ple.com\">ftp://exam-ple.com</a>", HtmlUtil.convertTextWithUrlsToHtml("ftp://exam-ple.com", null));

        Assert.assertEquals("azerty &eacute;&agrave;&egrave;<br/>&lt;tag&gt;", HtmlUtil.convertTextWithUrlsToHtml("azerty éàè\n<tag>", null));

        Assert.assertEquals("<a href=\"ftps://google.com\">ftps://google.com</a> <a href=\"https://exam-ple.com\">https://exam-ple.com</a><br/><a href=\"ftp://exam-ple.com\">ftp://exam-ple.com</a>", HtmlUtil.convertTextWithUrlsToHtml("ftps://google.com https://exam-ple.com\nftp://exam-ple.com", null));

        Assert.assertEquals("Something <a href=\"https://exam-ple.com\">https://exam-ple.com</a>", HtmlUtil.convertTextWithUrlsToHtml("Something https://exam-ple.com", null));
        Assert.assertEquals("<a href=\"https://exam-ple.com\">https://exam-ple.com</a> Something", HtmlUtil.convertTextWithUrlsToHtml("https://exam-ple.com Something", null));
        Assert.assertEquals("Something <a href=\"https://exam-ple.com\">https://exam-ple.com</a> Something", HtmlUtil.convertTextWithUrlsToHtml("Something https://exam-ple.com Something", null));
    }

    @Test
    public void test03GetHtmlTag() {
        Assert.assertEquals("<span>", HtmlUtil.getHtmlTag(HtmlUtil.TAG_SPAN));
        Assert.assertEquals("<span>", HtmlUtil.getHtmlTag(HtmlUtil.TAG_SPAN, false));
        Assert.assertEquals("</span>", HtmlUtil.getHtmlTag(HtmlUtil.TAG_SPAN, true));

        Assert.assertEquals("<span class=\"my-class\">", HtmlUtil.getHtmlTag(HtmlUtil.TAG_SPAN, HtmlUtil.attribute(HtmlUtil.ATTR_CLASS, "my-class")));
        Assert.assertEquals("<span class=\"my-class\" style=\"color:red\">", HtmlUtil.getHtmlTag(HtmlUtil.TAG_SPAN, HtmlUtil.attribute(HtmlUtil.ATTR_CLASS, "my-class"), HtmlUtil.attribute(HtmlUtil.ATTR_STYLE, "color:red")));
    }

    @Test
    public void test04br() {
        Assert.assertEquals("<br/>", HtmlUtil.BR);
    }

    @Test
    public void test05span() {
        Assert.assertEquals("<span>something</span>", HtmlUtil.span("something"));
    }

    @Test
    public void test06underline() {
        Assert.assertEquals("<u>something</u>", HtmlUtil.underline("something"));
    }

    @Test
    public void test07italic() {
        Assert.assertEquals("<i>something</i>", HtmlUtil.italic("something"));
    }

    @Test
    public void test08bold() {
        Assert.assertEquals("<b>something</b>", HtmlUtil.bold("something"));
    }

    @Test
    public void test09a() {
        Assert.assertEquals("<a href=\"https://example.com\" target=\"new\">My Link</a>", HtmlUtil.a("My Link", "https://example.com", "new"));
    }

    @Test
    public void test10GetHtml() {
        Assert.assertEquals("<span>My Content</span>", HtmlUtil.getHtml(HtmlUtil.TAG_SPAN, "My Content"));
        Assert.assertEquals("<span class=\"my-class\">My Content</span>", HtmlUtil.getHtml(HtmlUtil.TAG_SPAN, "My Content", HtmlUtil.attribute(HtmlUtil.ATTR_CLASS, "my-class")));
        Assert.assertEquals("<span class=\"my-class\" style=\"color:red\">My Content</span>", HtmlUtil.getHtml(HtmlUtil.TAG_SPAN, "My Content", HtmlUtil.attribute(HtmlUtil.ATTR_CLASS, "my-class"), HtmlUtil.attribute(HtmlUtil.ATTR_STYLE, "color:red")));
    }

    @Test
    public void test11DoubleUrlEncodeSlashOnly() {
        Assert.assertEquals(null, HtmlUtil.doubleUrlEncodeSlash(null));
        Assert.assertEquals("", HtmlUtil.doubleUrlEncodeSlash(""));
        Assert.assertEquals("Hi World%252FEarth", HtmlUtil.doubleUrlEncodeSlash("Hi World/Earth"));
        Assert.assertEquals("Hi World%252F%252FEarth", HtmlUtil.doubleUrlEncodeSlash("Hi World//Earth"));
        Assert.assertEquals("Hi%252FWorld%252FEarth", HtmlUtil.doubleUrlEncodeSlash("Hi/World/Earth"));
    }
}
