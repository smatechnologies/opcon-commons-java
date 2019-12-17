package com.smatechnologies.opcon.commons.adapter;

import com.smatechnologies.opcon.commons.deserializer.LocalDateDeserializer;
import com.smatechnologies.opcon.commons.serializer.LocalDateSerializer;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;


/**
 * @author Pierre PINON
 */
public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {

    private final LocalDateSerializer localDateSerializer;
    private final LocalDateDeserializer localDateDeserializer;

    public LocalDateXmlAdapter() {
        localDateSerializer = new LocalDateSerializer();
        localDateDeserializer = new LocalDateDeserializer();
    }

    public LocalDate unmarshal(String localDate) throws Exception {
        return localDateDeserializer.deserialize(localDate);
    }

    public String marshal(LocalDate localDate) throws Exception {
        return localDateSerializer.serialize(localDate);
    }
}
