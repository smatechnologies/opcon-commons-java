package com.smatechnologies.opcon.commons.util;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


/**
 * {@link JAXB} XML sertializer tuility class.
 */
public class XMLSerializer {

    /**
     * Utility method to serialize an Object to an XML file.
     * 
     * @param object
     *            the object to serialize
     * @param file
     *            the file to write XML into
     * @throws JAXBException
     *             thrown on XML serialization error
     * @throws IOException
     *             thrown on I/O operation error
     */
    public static <T> void serialize(T object, File file) throws JAXBException, IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            serialize(object, fileOutputStream);
        }
    }

    /**
     * Utility method to deserialize an Object from am XML file.
     * 
     * @param clazz
     *            the class type to deserialize from XML
     * @param file
     *            the file to read XML from
     * @return the deserialized class
     * @throws JAXBException
     *             thrown on XML serialization error
     * @throws IOException
     *             thrown on I/O operation error
     */
    public static <T> T deserialize(Class<T> clazz, File file) throws JAXBException, IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return deserialize(clazz, fileInputStream);
        }
    }

    /**
     * Utility method to serialize an Object to an XML file.
     * 
     * @param object
     *            the object to serialize
     * @param output
     *            the stream to write XML output into
     * @throws JAXBException
     *             thrown on XML serialization error
     */
    public static <T> void serialize(T object, OutputStream output) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(object.getClass());
        final Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        m.marshal(object, output); // Write to File
    }

    /**
     * Utility method to serialize an Object to an XML file.
     *
     * @param object
     *            the object to serialize
     * @return the serialized class
     * @throws JAXBException
     *             thrown on XML serialization error
     */
    public static <T> String serialize(T object) throws JAXBException, IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            XMLSerializer.serialize(object, output);
            return output.toString(StandardCharsets.UTF_8.name());
        }
    }

    /**
     * Utility method to deserialize an Object from am XML file.
     * 
     * @param clazz
     *            the class type to deserialize from XML
     * @param input
     *            the stream to read XML from
     * @return the deserialized class
     * @throws JAXBException
     *             thrown on XML serialization error
     */
    public static <T> T deserialize(Class<T> clazz, InputStream input) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(clazz);
        final Unmarshaller um = context.createUnmarshaller();
        return clazz.cast(um.unmarshal(input));
    }

    /**
     * Utility method to deserialize an Object from am XML file.
     *
     * @param clazz
     *            the class type to deserialize from XML
     * @param string
     *            the string to read XML from
     * @return the deserialized class
     * @throws JAXBException
     *             thrown on XML serialization error
     */
    public static <T> T deserialize(Class<T> clazz, String string) throws JAXBException, IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(string.getBytes(StringUtil.CHARSET_UTF8))) {
            return deserialize(clazz, input);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T copy(T object) {
        if (object == null) {
            return null;
        }

        try {
            return XMLSerializer.deserialize((Class<T>) object.getClass(), XMLSerializer.serialize(object));
        } catch (JAXBException | IOException e) {
            throw new IllegalArgumentException("Cannot copy the object [" + object.getClass().getSimpleName() + "]");
        }
    }
}
