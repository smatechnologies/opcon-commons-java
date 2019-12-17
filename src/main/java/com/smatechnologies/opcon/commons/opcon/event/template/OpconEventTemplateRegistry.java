package com.smatechnologies.opcon.commons.opcon.event.template;

import com.smatechnologies.opcon.commons.opcon.event.OpconEvent;
import com.smatechnologies.opcon.commons.opcon.event.OpconEventException;
import com.smatechnologies.opcon.commons.opcon.event.OpconEventParser;
import com.smatechnologies.opcon.commons.util.XMLSerializer;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This is the OpCon/xps event template registry.
 */
@XmlRootElement(name = "events")
public class OpconEventTemplateRegistry {

    public static final String EVENTS_XML_PROPERTY_FILE_NAME = "/events.xml";

    @XmlElement(name = "event")
    private List<OpconEventTemplate> openEventTemplates = new ArrayList<>();

    /**
     * Create {@link OpconEventTemplateRegistry} from event file {@value #EVENTS_XML_PROPERTY_FILE_NAME}
     * 
     * @return OpconEventTemplateRegistry
     * @throws OpconEventException
     */
    public static OpconEventTemplateRegistry getRegistryFromEventFile() throws OpconEventException {
        try (InputStream inputStream = OpconEventTemplateRegistry.class.getResourceAsStream(EVENTS_XML_PROPERTY_FILE_NAME)) {
            return getRegistryFromInputStream(inputStream);

        } catch (IOException e) {
            throw new OpconEventException(OpconEventException.TYPE_ERROR_UNREADABLE_EVENTS_FILE, e);
        }
    }

    /**
     * Create {@link OpconEventTemplateRegistry} from InputStream
     * 
     * @param inputStream
     * @return OpconEventTemplateRegistry
     * @throws OpconEventException
     */
    public static OpconEventTemplateRegistry getRegistryFromInputStream(InputStream inputStream) throws OpconEventException {
        try {
            return XMLSerializer.deserialize(OpconEventTemplateRegistry.class, inputStream);
        } catch (JAXBException e) {
            throw new OpconEventException(OpconEventException.TYPE_ERROR_UNREADABLE_EVENTS_FILE, e);
        }
    }

    /**
     * Get collection of EventTemplates
     * 
     * @return collection of {@link OpconEventTemplate}
     */
    public List<OpconEventTemplate> getEventTemplates() {
        return Collections.unmodifiableList(openEventTemplates);
    }

    /**
     * Get {@link OpconEventTemplate} by command
     * 
     * @param command
     * @return OpconEventTemplate
     */
    public OpconEventTemplate getEventTemplateByCommand(String command) {
        for (OpconEventTemplate opconEventTemplate : openEventTemplates) {
            if (opconEventTemplate.getCommand().equals(command)) {
                return opconEventTemplate;
            }
        }

        return null;
    }

    /**
     * Convert string event to {@link OpconEvent} object
     * 
     * @param eventString
     * @return OpconEvent
     * @throws OpconEventException
     *             if eventString is incorrect
     */
    public OpconEvent stringToEvent(String eventString) throws OpconEventException {
        OpconEventParser eventParser = new OpconEventParser(eventString);

        OpconEventTemplate eventTemplate = getEventTemplateByCommand(eventParser.getCommand());
        if (eventTemplate == null) {
            throw new OpconEventException(OpconEventException.TYPE_ERROR_UNREACHABLE_COMMAND, "Can not find template associed to event command");
        }

        return new OpconEvent(eventTemplate, eventParser.getParameters());
    }
}
