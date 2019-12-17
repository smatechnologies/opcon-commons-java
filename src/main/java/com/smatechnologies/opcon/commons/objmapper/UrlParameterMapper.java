package com.smatechnologies.opcon.commons.objmapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @author Pierre PINON
 */
public class UrlParameterMapper {

    private static String ENCODING = "UTF-8";
    private static String PARAMETERS_SEPARATOR = "&";
    private static String PARAMETER_KEY_VALUE_SEPARATOR = "=";
    private static Pattern WITH_ANCHOR_PATTERN = Pattern.compile("^[^#]*#(.*)$");
    private static Pattern WITHOUT_ANCHOR_PATTERN = Pattern.compile("^([^#]*)(?:#|$)");
    private static Pattern QUESTION_MARK_PATTERN = Pattern.compile("^[^?]*\\?(.*)$");
    private static Pattern PARAMETER_KEY_VALUE_PATTERN = Pattern.compile("^([^" + PARAMETER_KEY_VALUE_SEPARATOR + "]*)" + PARAMETER_KEY_VALUE_SEPARATOR + "(.*)$");

    private ObjMapper objMapper = new ObjMapper();

    public ObjMapper getObjMapper() {
        return objMapper;
    }

    public void setObjMapper(ObjMapper objMapper) {
        this.objMapper = Objects.requireNonNull(objMapper, "ObjMapper cannot be null");
    }

    public <T> String serialize(T object) throws ObjMapperException {
        Map<String, String[]> map = objMapper.serialize(object).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> new String[] { entry.getValue() },
                        (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        },
                        LinkedHashMap::new));

        return mapToUrlParameters(map, true);

    }

    public <T> T deserialize(Class<T> clazz, String string) throws ObjMapperException {
        Map<String, String> map = new LinkedHashMap<>();

        urlParametersToMap(string, true).forEach((encodedKey, encodeValue) -> {
            //Cannot collect map because value can be null
            String key = urlDecode(encodedKey);
            String value = null;
            if (encodeValue != null && encodeValue.length != 0) {
                value = urlDecode(encodeValue[0]);
            }

            map.put(key, value);
        });

        return objMapper.deserializer(clazz, map);
    }

    public static String getUrlParameters(String url, boolean anchor) {
        if (url == null) {
            return null;
        }

        Matcher matcherAnchor = (anchor ? WITH_ANCHOR_PATTERN : WITHOUT_ANCHOR_PATTERN).matcher(url);

        if (matcherAnchor.find()) {
            url = matcherAnchor.group(1);
        } else {
            return "";
        }

        Matcher matcher = QUESTION_MARK_PATTERN.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return url;
        }
    }

    public static Map<String, String[]> urlParametersToMap(String urlParameters, boolean urlDecode) {
        if (urlParameters == null) {
            return null;
        }

        Map<String, String[]> map = new LinkedHashMap<>();

        Arrays.stream(urlParameters.split(PARAMETERS_SEPARATOR))
                .map(urlParameter -> {
                    Matcher matcher = PARAMETER_KEY_VALUE_PATTERN.matcher(urlParameter);

                    if (matcher.find()) {
                        return new String[] { matcher.group(1), matcher.group(2) };
                    } else {
                        return new String[] { urlParameter, null };
                    }
                })
                .filter(keyAndValue -> !keyAndValue[0].isEmpty())
                .forEach(keyAndValue -> {
                    //Cannot collect map because value can be null
                    String key = urlDecode ? urlDecode(keyAndValue[0]) : keyAndValue[0];
                    String value = urlDecode ? urlDecode(keyAndValue[1]) : keyAndValue[1];

                    String[] values = map.get(key);
                    if (values == null) {
                        map.put(key, new String[] { value });
                    } else {
                        String[] newValues = new String[values.length + 1];
                        System.arraycopy(values, 0, newValues, 0, values.length);
                        newValues[newValues.length - 1] = value;

                        map.put(key, newValues);
                    }

                });

        return map;
    }

    public static String mapToUrlParameters(Map<String, String[]> map, boolean urlEncode) {
        if (map == null) {
            return null;
        }

        return map.entrySet().stream()
                .map(entry -> {
                    String key = urlEncode ? urlEncode(entry.getKey()) : entry.getKey();
                    String[] values = entry.getValue();

                    String keyParameters = null;

                    for (String value : values) {
                        if (keyParameters == null) {
                            keyParameters = "";
                        } else {
                            keyParameters += PARAMETERS_SEPARATOR;
                        }

                        if (value == null) {
                            keyParameters += key;
                        } else {
                            if (urlEncode) {
                                value = urlEncode(value);
                            }

                            keyParameters += key + PARAMETER_KEY_VALUE_SEPARATOR + value;
                        }
                    }

                    return keyParameters;
                })
                .collect(Collectors.joining(PARAMETERS_SEPARATOR));
    }

    public static String urlEncode(String string) {
        if (string == null) {
            return null;
        }
        try {
            return URLEncoder.encode(string, ENCODING);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

    public static String urlDecode(String string) {
        if (string == null) {
            return null;
        }
        try {
            return URLDecoder.decode(string, ENCODING);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }
}
