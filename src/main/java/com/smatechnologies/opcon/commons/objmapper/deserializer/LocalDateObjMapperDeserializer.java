package com.smatechnologies.opcon.commons.objmapper.deserializer;

import com.smatechnologies.opcon.commons.deserializer.DeserializeException;
import com.smatechnologies.opcon.commons.deserializer.LocalDateDeserializer;
import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;

import java.time.LocalDate;


/**
 * @author Pierre PINON
 */
public class LocalDateObjMapperDeserializer implements IObjMapperDeserializer<LocalDate> {

    private final LocalDateDeserializer dateDeserializer;

    public LocalDateObjMapperDeserializer() {
        dateDeserializer = new LocalDateDeserializer();
    }

    @Override
    public LocalDate deserialize(ObjMapper.ObjMapperContext objMapperContext, String value) throws ObjMapperException {
        try {
            return dateDeserializer.deserialize(value);
        } catch (DeserializeException e) {
            throw new ObjMapperException(e);
        }
    }
}
