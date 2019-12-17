package com.smatechnologies.opcon.commons.opcon.event;

import com.smatechnologies.opcon.commons.opcon.event.parameter.IOpconEventParameter;
import com.smatechnologies.opcon.commons.opcon.event.parameter.OpconEventParameterException;
import com.smatechnologies.opcon.commons.opcon.event.template.OpconEventParameterTemplate;
import com.smatechnologies.opcon.commons.opcon.event.template.OpconEventTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * This is an OpCon/xps event object. This is a convenience object to work with OpCon/xps Events. An event can be decomposed this way :
 * 
 * <pre>
 * $CATEGORY:ACTION, parameter1, parameter2
 * </pre>
 */
public class OpconEvent {

    private final OpconEventTemplate eventTemplate;
    private List<IOpconEventParameter<?>> parameters;

    /**
     * Create a new event from the event template object
     * 
     * @param eventTemplate
     * @throws OpconEventException
     */
    public OpconEvent(OpconEventTemplate eventTemplate) throws OpconEventException {
        this.eventTemplate = Objects.requireNonNull(eventTemplate);

        //Initialize event parameters
        if (eventTemplate.getMaximumNumberOfParameters() > 0) {
            this.parameters = new ArrayList<>();

            for (OpconEventParameterTemplate eventParameterTemplate : eventTemplate.getParameters()) {
                try {
                    this.parameters.add(eventParameterTemplate.getType().getEventParameterClass().newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new OpconEventException(OpconEventException.TYPE_ERROR_CREATE_PARAMETER, "Can not create event parameter");
                }
            }
        }
    }

    /**
     * Create a new event from the event template object and a list of parameters
     * 
     * @param eventTemplate
     * @param parameters
     * @throws OpconEventException
     */
    public OpconEvent(OpconEventTemplate eventTemplate, List<String> parameters) throws OpconEventException {
        this(eventTemplate);

        //Check if number of parameter is good
        if ((parameters == null && eventTemplate.getMinimumNumberOfParameters() != 0) ||
            (parameters != null && (parameters.size() < eventTemplate.getMinimumNumberOfParameters() || parameters.size() > eventTemplate.getMaximumNumberOfParameters()))) {
            throw new OpconEventException(OpconEventException.TYPE_ERROR_BAD_NUMBER_OF_PARAMETER, "Incorrect number of parameter");
        }

        //Set value of event parameters
        if (parameters != null && parameters.size() > 0) {
            for (int i = 0; i < parameters.size(); i++) {
                this.parameters.get(i).setString(parameters.get(i));
            }
        }

        validateStructure();
    }

    /**
     * Get the EventTemplate associed to Event
     * 
     * @return IOpconEventTemplate
     */
    public OpconEventTemplate getEventTemplate() {
        return eventTemplate;
    }

    /**
     * Get the event's parameters.
     * 
     * @return an array of {@link IOpconEventParameter} parameters.
     */
    public List<IOpconEventParameter<?>> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    /**
     * Get the event's parameter
     * 
     * @param index
     * @return {@link IOpconEventParameter}
     */
    public IOpconEventParameter<?> getParameter(int index) {
        return parameters.get(index);
    }

    /**
     * Check if Event is valid
     *
     * @throws OpconEventParameterException
     */
    public void validate() throws OpconEventParameterException {
        if (eventTemplate.getMaximumNumberOfParameters() > 0) {
            for (int i = 0; i < eventTemplate.getParameters().size(); i++) {
                OpconEventParameterTemplate eventParameterTemplate = eventTemplate.getParameter(i);
                IOpconEventParameter<?> eventParameter = getParameter(i);

                eventParameter.validate();

                if (!eventParameterTemplate.isOptional() && eventParameter.toString().isEmpty()) {
                    OpconEventParameterException e = new OpconEventParameterException(OpconEventParameterException.TYPE_ERROR_PARAMETER_REQUIRED);
                    e.setParameterTemplateReference(eventParameterTemplate);
                    throw e;
                }
            }
        }
    }

    /**
     * Check if Event has a valid structure
     *
     * @throws OpconEventParameterException
     */
    public void validateStructure() throws OpconEventParameterException {
        if (eventTemplate.getMaximumNumberOfParameters() > 0) {
            for (int i = 0; i < eventTemplate.getParameters().size(); i++) {
                IOpconEventParameter<?> eventParameter = getParameter(i);

                try {
                    eventParameter.validateStructure();
                } catch (OpconEventParameterException e) {
                    OpconEventParameterTemplate eventParameterTemplate = eventTemplate.getParameter(i);
                    e.setParameterTemplateReference(eventParameterTemplate);
                    throw e;
                }
            }
        }
    }

    public String toString() {
        return OpconEventParser.commandAndParametersToString(eventTemplate.getCommand(), parameters);
    }
}
