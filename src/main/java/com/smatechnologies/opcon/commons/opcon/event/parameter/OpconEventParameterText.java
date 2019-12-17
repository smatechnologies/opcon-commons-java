package com.smatechnologies.opcon.commons.opcon.event.parameter;

public class OpconEventParameterText extends AbstractOpconEventParameter<String> {

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.AbstractOpconEventParameter#stringToValue(java.lang.String)
     */
    @Override
    protected String stringToValue(String string) throws OpconEventParameterException {
        return string;
    }

    /*
     * (non-Javadoc)
     * @see com.smatechnologies.opcon.commons.opcon.event.parameter.AbstractOpconEventParameter#valueToString(java.lang.Object)
     */
    @Override
    protected String valueToString(String value) throws OpconEventParameterException {
        return value;
    }
}
