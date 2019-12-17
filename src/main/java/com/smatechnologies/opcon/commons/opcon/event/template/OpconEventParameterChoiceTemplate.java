package com.smatechnologies.opcon.commons.opcon.event.template;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * This class represent an parameter choice of EventTemplate
 * 
 * @author Pierre PINON
 */
@XmlRootElement(name = "choice")
public class OpconEventParameterChoiceTemplate {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "value")
    private String value;

    /**
     * Get the name of choice
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the value of choice
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OpconEventParameterChoiceTemplate other = (OpconEventParameterChoiceTemplate) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
