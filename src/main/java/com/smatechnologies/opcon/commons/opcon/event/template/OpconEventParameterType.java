package com.smatechnologies.opcon.commons.opcon.event.template;

import com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterList;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterMap;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterText;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * Type of OpconEventParameter.
 * <p>
 * Method getEventParameterClass allows to get implementation associated with type of parameter
 * 
 * @author Pierre PINON
 */
@XmlType(name = "type")
public enum OpconEventParameterType {
    @XmlEnumValue("text")
    TEXT(OpconEventParameterText.class),
    @XmlEnumValue("int")
    INT(OpconEventParameterText.class),
    @XmlEnumValue("date")
    DATE(OpconEventParameterText.class),
    @XmlEnumValue("map")
    MAP(OpconEventParameterMap.class),
    @XmlEnumValue("list")
    LIST(OpconEventParameterList.class);

    private Class<? extends IOpconEventParameter<?>> eventParameter;

    OpconEventParameterType(Class<? extends IOpconEventParameter<?>> eventParameter) {
        this.eventParameter = eventParameter;
    }

    public Class<? extends IOpconEventParameter<?>> getEventParameterClass() {
        return eventParameter;
    }
}
