package com.smatechnologies.opcon.commons.test.opcon.event;

import com.smatechnologies.opcon.commons.opcon.event.OpconEventException;
import com.smatechnologies.opcon.commons.opcon.event.template.OpconEventTemplateRegistry;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;


abstract public class AbstractOpconEventParameterTemplateTest {

    public static final String EVENTS_XML_PROPERTY_FILE_NAME = "/opconevent/events.xml";

    protected OpconEventTemplateRegistry registry;

    @Before
    public void before() throws OpconEventException, IOException {
        try (InputStream inputStream = AbstractOpconEventParameterTemplateTest.class.getResourceAsStream(EVENTS_XML_PROPERTY_FILE_NAME)) {
            registry = OpconEventTemplateRegistry.getRegistryFromInputStream(inputStream);
        }
    }
}
