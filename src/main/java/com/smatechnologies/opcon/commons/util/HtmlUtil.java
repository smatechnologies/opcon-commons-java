package com.smatechnologies.opcon.commons.util;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HtmlUtil {

    public static final String URL_SEPARATOR = "/";

    public static final String TAG_META = "meta";
    public static final String TAG_LINK = "link";
    public static final String TAG_BR = "br";
    public static final String TAG_DIV = "div";
    public static final String TAG_SPAN = "span";
    public static final String TAG_MARK = "mark";
    public static final String TAG_A = "a";
    public static final String TAG_UNDERLINE = "u";
    public static final String TAG_BOLD = "b";
    public static final String TAG_ITALIC = "i";

    public static final String ATTR_REL = "rel";
    public static final String ATTR_HREF = "href";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_CONTENT = "content";
    public static final String ATTR_SIZES = "sizes";
    public static final String ATTR_CLASS = "class";
    public static final String ATTR_STYLE = "style";
    public static final String ATTR_TARGET = "target";
    public static final String ATTR_TYPE = "type";

    public static final String ATTR_VALUE_BLANK = "_blank";

    private static final String OPEN_TAG_START = "<";
    private static final String CLOSE_TAG_START = "</";
    private static final String TAG_END = ">";
    private static final String AUTO_CLOSE_TAG_END = "/>";

    private static final String ATTRIBUTE_SEPARATOR = " ";
    private static final String ATTRIBUTE_ASSIGNMENT = "=";
    private static final String ATTRIBUTE_DELIMITER = "\"";

    private static final String HTTP_AND_FTP_URL_PATTERN = "(?:(?:[hH][tT])|[fF])[tT][pP][sS]?://[^ \\n]+";

    public static final String SPACE = "&nbsp;";

    public static final String BR = getHtmlSelfCloseTag(TAG_BR);

    public static String convertTextWithUrlsToHtml(String text, String target) {
        if (text == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(HTTP_AND_FTP_URL_PATTERN);
        Matcher matcher = pattern.matcher(text);

        StringBuilder stringBuilder = new StringBuilder();

        int offset = 0;

        while (matcher.find()) {
            if (matcher.start() > 0) {
                stringBuilder.append(convertTextToHtml(text.substring(offset, matcher.start())));
            }
            stringBuilder.append(a(matcher.group(0), matcher.group(0), target));

            offset = matcher.end();
        }
        if (offset < text.length()) {
            stringBuilder.append(convertTextToHtml(text.substring(offset)));
        }

        return stringBuilder.toString();
    }

    public static String convertTextToHtml(String text) {
        if (text == null) {
            return null;
        }

        return StringEscapeUtils.escapeHtml4(text)
                .replaceAll(StringUtil.CRLF, BR)
                .replaceAll(StringUtil.LF, BR);
    }

    public static String getHtml(String tagName, String content, Attribute... attributes) {
        return getHtmlTag(tagName, attributes) + (content == null ? "" : content) + getHtmlTag(tagName, true);
    }

    public static String getHtmlTag(String tagName, Attribute... attributes) {
        return OPEN_TAG_START + tagName + attributeToString(attributes) + TAG_END;
    }

    public static String getHtmlTag(String tagName, boolean close) {
        return (close ? CLOSE_TAG_START : OPEN_TAG_START) + tagName + TAG_END;
    }

    public static String getHtmlSelfCloseTag(String tagName, Attribute... attributes) {
        return OPEN_TAG_START + tagName + attributeToString(attributes) + AUTO_CLOSE_TAG_END;
    }

    public static String div(String content, Attribute... attributes) {
        return getHtml(TAG_DIV, content, attributes);
    }

    public static String span(String content, Attribute... attributes) {
        return getHtml(TAG_SPAN, content, attributes);
    }

    public static String underline(String content, Attribute... attributes) {
        return getHtml(TAG_UNDERLINE, content, attributes);
    }

    public static String italic(String content, Attribute... attributes) {
        return getHtml(TAG_ITALIC, content, attributes);
    }

    public static String bold(String content, Attribute... attributes) {
        return getHtml(TAG_BOLD, content, attributes);
    }

    public static String mark(String content, Attribute... attributes) {
        return getHtml(TAG_MARK, content, attributes);
    }

    public static String a(String content, String href, String target, Attribute... attributes) {
        List<Attribute> attributeList = new ArrayList<>();
        if (attributes != null) {
            attributeList.addAll(Arrays.asList(attributes));
        }

        if (target != null) {
            attributeList.add(0, HtmlUtil.attribute(HtmlUtil.ATTR_TARGET, target));
        }

        return a(content, href, attributeList.isEmpty() ? null : attributeList.toArray(new Attribute[0]));
    }

    public static String a(String content, String href, Attribute... attributes) {
        List<Attribute> attributeList = new ArrayList<>();
        if (attributes != null) {
            attributeList.addAll(Arrays.asList(attributes));
        }

        if (href != null) {
            attributeList.add(0, HtmlUtil.attribute(HtmlUtil.ATTR_HREF, href));
        }

        return a(content, attributeList.isEmpty() ? null : attributeList.toArray(new Attribute[0]));
    }

    public static String a(String content, Attribute... attributes) {
        return HtmlUtil.getHtml(HtmlUtil.TAG_A, content, attributes);
    }

    private static String attributeToString(Attribute... attributes) {
        String html = "";

        if (attributes != null) {
            for (Attribute attribute : attributes) {
                if (attribute != null) {
                    html += ATTRIBUTE_SEPARATOR;
                    html += attribute.getName() + ATTRIBUTE_ASSIGNMENT + ATTRIBUTE_DELIMITER + attribute.getValue() + ATTRIBUTE_DELIMITER;
                }
            }
        }

        return html;
    }

    public static String doubleUrlEncodeSlash(String url) {
        if (url == null) {
            return null;
        }

        return url.replace(URL_SEPARATOR, StringUtil.urlEncode(StringUtil.urlEncode(URL_SEPARATOR)));
    }

    public static Attribute attribute(String name, String value) {
        return new Attribute() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getValue() {
                return value;
            }
        };
    }

    public interface Attribute {

        String getName();

        String getValue();
    }
}
