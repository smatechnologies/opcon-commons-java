package com.smatechnologies.opcon.commons.opcon.event.template;

import com.smatechnologies.opcon.commons.opcon.event.OpconEventParser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


/**
 * This class represent an parameter of EventTemplate
 * 
 * @author Pierre PINON
 */
@XmlRootElement(name = "parameter")
public class OpconEventParameterTemplate {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "optional")
    private boolean isOptional;

    @XmlElement(name = "type")
    private OpconEventParameterType parameterType;

    @XmlElementWrapper(name = "choices")
    @XmlElement(name = "choice")
    private List<OpconEventParameterChoiceTemplate> choices;

    /**
     * Get the name of parameter
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get type of parameter
     *
     * @return OpconEventParameterType
     */
    public OpconEventParameterType getType() {
        return parameterType;
    }

    /**
     * Return if parameter is optional
     *
     * @return boolean
     */
    public boolean isOptional() {
        return isOptional;
    }

    /**
     * Return if choices are available
     *
     * @return boolean
     */
    public boolean hasChoice() {
        return choices != null && !choices.isEmpty();
    }

    /**
     * Return the choice of parameter from parameter name
     * <p>
     * Return null if there is no choices
     *
     * @return list of {@link OpconEventParameterChoiceTemplate}
     */
    public List<OpconEventParameterChoiceTemplate> getChoices() {
        if (choices == null || choices.isEmpty()) {
            return null;
        }
        return choices;
    }

    @Override
    public String toString() {
        return OpconEventParser.parameterToString(name, isOptional);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((choices == null) ? 0 : choices.hashCode());
        result = prime * result + (isOptional ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parameterType == null) ? 0 : parameterType.hashCode());
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
        OpconEventParameterTemplate other = (OpconEventParameterTemplate) obj;
        if (choices == null) {
            if (other.choices != null)
                return false;
        } else if (!choices.equals(other.choices))
            return false;
        if (isOptional != other.isOptional)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (parameterType != other.parameterType)
            return false;
        return true;
    }
}
