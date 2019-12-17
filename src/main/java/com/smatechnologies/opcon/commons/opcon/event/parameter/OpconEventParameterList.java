package com.smatechnologies.opcon.commons.opcon.event.parameter;

import com.smatechnologies.opcon.commons.constants.SystemConstants;
import com.smatechnologies.opcon.commons.opcon.util.ExpressionUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class OpconEventParameterList extends AbstractOpconEventParameter<List<String>> {

    private static final String ITEM_SEPARATOR = SystemConstants.SEMI_COLON;

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.AbstractOpconEventParameter#stringToValue(java.lang.String)
     */
    @Override
    public List<String> stringToValue(String string) throws OpconEventParameterException {
        List<String> items = ExpressionUtil.splitStringWithExpressions(string, ITEM_SEPARATOR.charAt(0));

        for (String item: items) {
            if (item.isEmpty()) {
                OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_CONVERSION, "Invalid parameter structure");
                e.setReference(item);
                throw e;
            }
        }

        return Collections.unmodifiableList(items);
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.AbstractOpconEventParameter#valueToString(java.lang.Object)
     */
    @Override
    public String valueToString(List<String> list) throws OpconEventParameterException {
        Objects.requireNonNull(list, "List can not be null");

        StringBuilder stringBuilder = new StringBuilder();

        for (String item : list) {
            //Checks if structure is correct
            String valueWithoutExpressions = ExpressionUtil.removeExpressions(item);
            if (valueWithoutExpressions.contains(ITEM_SEPARATOR)) {
                OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_INVALID_CHAR, "Invalid parameter structure.");
                e.setRuleReference(ITEM_SEPARATOR);
                e.setReference(item);
                throw e;
            }

            if (!stringBuilder.toString().equals("")) {
                stringBuilder.append(ITEM_SEPARATOR);
            }
            stringBuilder.append(item);

        }

        return stringBuilder.toString();
    }

    @Override
    public void validateString(String string) throws OpconEventParameterException {
        validateSymmetricConversion(string);

        List<String> value = stringToValue(string);

        for(String item: value) {
            try {
                validateListValue(item);
            } catch (OpconEventParameterException e) {
                e.setReference(item);
                throw e;
            }
        }
    }

    public void validateListValue(String value) throws OpconEventParameterException {
        validateChars(value, ITEM_SEPARATOR);

        if (value.isEmpty()) {
            OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_INVALID_VALUE);
            e.setReference(value);
            throw e;
        }
    }
}
