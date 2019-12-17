package com.smatechnologies.opcon.commons.opcon.event.parameter;

import com.smatechnologies.opcon.commons.opcon.event.OpconEventException;
import com.smatechnologies.opcon.commons.opcon.event.template.OpconEventParameterTemplate;


public class OpconEventParameterException extends OpconEventException {

    private static final long serialVersionUID = 1L;

    public final static int TYPE_ERROR_PARAMETER_CONVERSION = 10;
    public final static int TYPE_ERROR_PARAMETER_INVALID_VALUE = 11;
    public final static int TYPE_ERROR_PARAMETER_INVALID_CHAR = 12;
    public final static int TYPE_ERROR_PARAMETER_REQUIRED = 13;
    public final static int TYPE_ERROR_PARAMETER_ASYMMETRIC_CONVERSION = 14;

    private OpconEventParameterTemplate parameterTemplateReference;
    private String reference;
    private String ruleReference;

    public OpconEventParameterException(int type) {
        super(type);
    }

    public OpconEventParameterException(int type, String message, Throwable cause) {
        super(type, message, cause);
    }

    public OpconEventParameterException(int type, String message) {
        super(type, message);
    }

    public OpconEventParameterException(int type, Throwable cause) {
        super(type, cause);
    }

    public OpconEventParameterTemplate getParameterTemplateReference() {
        return parameterTemplateReference;
    }

    public void setParameterTemplateReference(OpconEventParameterTemplate parameterTemplateReference) {
        this.parameterTemplateReference = parameterTemplateReference;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRuleReference() {
        return ruleReference;
    }

    public void setRuleReference(String ruleReference) {
        this.ruleReference = ruleReference;
    }
}
