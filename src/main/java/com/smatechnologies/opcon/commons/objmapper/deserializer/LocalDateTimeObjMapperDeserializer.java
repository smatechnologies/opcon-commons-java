package com.smatechnologies.opcon.commons.objmapper.deserializer;

import com.smatechnologies.opcon.commons.deserializer.DeserializeException;
import com.smatechnologies.opcon.commons.deserializer.LocalDateTimeDeserializer;
import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;

import java.time.LocalDateTime;


/**
 * @author Pierre PINON
 */
public class LocalDateTimeObjMapperDeserializer implements IObjMapperDeserializer<LocalDateTime> {

    private final LocalDateTimeDeserializer dateDeserializer;

    public LocalDateTimeObjMapperDeserializer() {
        dateDeserializer = new LocalDateTimeDeserializer();
    }

    @Override
    public LocalDateTime deserialize(ObjMapper.ObjMapperContext objMapperContext, String value) throws ObjMapperException {
        try {
            return dateDeserializer.deserialize(value);
        } catch (DeserializeException e) {
            throw new ObjMapperException(e);
        }
    }
}
