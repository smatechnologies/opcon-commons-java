package com.smatechnologies.opcon.commons.opcon.event.parameter;

import com.smatechnologies.opcon.commons.constants.SystemConstants;
import com.smatechnologies.opcon.commons.opcon.util.ExpressionUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;


public class OpconEventParameterMap extends AbstractOpconEventParameter<Map<String, String>> {

    private static final String ITEM_SEPARATOR = SystemConstants.SEMI_COLON;
    private static final String KEY_VALUE_SEPARATOR = SystemConstants.EQUAL;
    private static final String SPACE_CHAR = " ";
    private static final String ADDITIONAL_INVALID_KEY_CHARS = "|'()\\";

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.AbstractOpconEventParameter#stringToValue(java.lang.String)
     */
    @Override
    protected Map<String, String> stringToValue(String string) throws OpconEventParameterException {
        List<String> stringParts = ExpressionUtil.splitStringWithExpressions(string, ITEM_SEPARATOR.charAt(0));

        Map<String, String> items = new LinkedHashMap<>();
        for (String stringPart: stringParts) {
            List<String> keyValue = ExpressionUtil.splitStringWithExpressions(stringPart, KEY_VALUE_SEPARATOR.charAt(0));

            //Checks if structure is correct
            if (keyValue.size() < 2) {
                OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_CONVERSION, "Invalid parameter structure");
                e.setReference(stringPart);
                throw e;
            }

            items.put(keyValue.get(0), String.join(KEY_VALUE_SEPARATOR, keyValue.subList(1, keyValue.size())));
        }

        return Collections.unmodifiableMap(items);
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.AbstractOpconEventParameter#valueToString(java.lang.Object)
     */
    @Override
    protected String valueToString(Map<String, String> value) throws OpconEventParameterException {
        Objects.requireNonNull(value, "Map can not be null");

        StringBuilder stringBuilder = new StringBuilder();

        for (Entry<String, String> entry : value.entrySet()) {
            //Checks if structure is correct
            String keyWithoutExpressions = ExpressionUtil.removeExpressions(entry.getKey());
            String valueWithoutExpressions = ExpressionUtil.removeExpressions(entry.getValue());
            if (keyWithoutExpressions.contains(ITEM_SEPARATOR) || keyWithoutExpressions.contains(KEY_VALUE_SEPARATOR) || valueWithoutExpressions.contains(ITEM_SEPARATOR)) {
                OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_INVALID_CHAR, "Invalid parameter structure.");
                e.setRuleReference(ITEM_SEPARATOR + " " + KEY_VALUE_SEPARATOR);
                e.setReference(entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue());
                throw e;
            }

            if (!stringBuilder.toString().equals("")) {
                stringBuilder.append(ITEM_SEPARATOR);
            }
            stringBuilder.append(entry.getKey()).append(KEY_VALUE_SEPARATOR).append(entry.getValue());
        }

        return stringBuilder.toString();
    }

    @Override
    public void validateString(String string) throws OpconEventParameterException {
        validateSymmetricConversion(string);

        Map<String, String> value = stringToValue(string);

        for(Entry<String, String> entry: value.entrySet()) {
            try {
                validateMapKey(entry.getKey());
                validateMapValue(entry.getValue());
            } catch (OpconEventParameterException e) {
                e.setReference(entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue());
                throw e;
            }
        }
    }

    public void validateMapKey(String key) throws OpconEventParameterException {
        validateChars(key, (ITEM_SEPARATOR + KEY_VALUE_SEPARATOR + ADDITIONAL_INVALID_KEY_CHARS).split(""));
        validateMapKeyOrValue(key);
    }

    public void validateMapValue(String value) throws OpconEventParameterException {
        validateChars(value, ITEM_SEPARATOR);
        validateMapKeyOrValue(value);
    }

    private void validateMapKeyOrValue(String string) throws OpconEventParameterException {
        if (string.isEmpty()) {
            OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_INVALID_VALUE);
            e.setReference(string);
            throw e;
        }

        if (string.startsWith(SPACE_CHAR) || string.endsWith(SPACE_CHAR)) {
            OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_INVALID_VALUE);
            e.setReference(string);
            throw e;
        }
    }
}
