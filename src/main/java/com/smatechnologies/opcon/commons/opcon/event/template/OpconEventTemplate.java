package com.smatechnologies.opcon.commons.opcon.event.template;

import com.smatechnologies.opcon.commons.opcon.event.OpconEventParser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.List;


/**
 * This is an OpCon/xps event template object. This is a convenience object to work with OpCon/xps Events. An event can be decomposed this way :
 * 
 * <pre>
 * $CATEGORY:ACTION, parameter1, parameter2
 * </pre>
 */
@XmlRootElement(name = "event")
public class OpconEventTemplate implements Comparable<OpconEventTemplate> {

    @XmlElement(name = "category")
    private String category;

    @XmlElement(name = "action")
    private String action;

    @XmlElementWrapper(name = "parameters")
    @XmlElement(name = "parameter")
    private List<OpconEventParameterTemplate> eventParameterTemplates;

    /**
     * Get the event category : the very first part of the event command. It's the event's action target.
     * <p>
     * An event has the following form
     *
     * <pre>
     * $CATEGORY:ACTION
     * </pre>
     *
     * @return string
     */
    public String getCategory() {
        return category;
    }

    /**
     * Get the event action.
     * <p>
     * An event has the following form
     *
     * <pre>
     * $CATEGORY:ACTION
     * </pre>
     *
     * @return string
     */
    public String getAction() {
        return action;
    }

    /**
     * Get the event command. The command is the first event's parameter.
     * <P>
     * An event command has the following form
     *
     * <pre>
     * $CATEGORY:ACTION
     * </pre>
     *
     * @return the command string.
     */
    public String getCommand() {
        return OpconEventParser.categoryAndActionToCommand(category, action);
    }

    /**
     * Get the event template's parameters.
     *
     * @return an array of {@link OpconEventParameterTemplate} parameters.
     */
    public List<OpconEventParameterTemplate> getParameters() {
        if (eventParameterTemplates == null || eventParameterTemplates.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableList(eventParameterTemplates);
    }

    /**
     * Get the event template's parameter
     *
     * @param index
     * @return {@link OpconEventParameterTemplate}
     */
    public OpconEventParameterTemplate getParameter(int index) {
        return eventParameterTemplates.get(index);
    }

    /**
     * @return the minimum number of parameters that this event must provide. like if the optional parameters were not provided.
     */
    public int getMinimumNumberOfParameters() {
        if (eventParameterTemplates != null) {
            for (int i = eventParameterTemplates.size(); i >= 1; i--) {
                if (!eventParameterTemplates.get(i - 1).isOptional()) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * @return the number of parameters that this event should provide, counting optional parameters.
     */
    public int getMaximumNumberOfParameters() {
        if (eventParameterTemplates == null) {
            return 0;
        }
        return eventParameterTemplates.size();
    }

    @Override
    public String toString() {
        return OpconEventParser.commandAndParametersToString(getCommand(), eventParameterTemplates);
    }

    @Override
    public int compareTo(OpconEventTemplate o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((eventParameterTemplates == null) ? 0 : eventParameterTemplates.hashCode());
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
        OpconEventTemplate other = (OpconEventTemplate) obj;
        if (action == null) {
            if (other.action != null)
                return false;
        } else if (!action.equals(other.action))
            return false;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        if (eventParameterTemplates == null) {
            if (other.eventParameterTemplates != null)
                return false;
        } else if (!eventParameterTemplates.equals(other.eventParameterTemplates))
            return false;
        return true;
    }
}
