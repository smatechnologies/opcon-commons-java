package com.smatechnologies.opcon.commons.objmapper.deserializer;

import com.smatechnologies.opcon.commons.deserializer.DeserializeException;
import com.smatechnologies.opcon.commons.deserializer.ZonedDateTimeDeserializer;
import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;

import java.time.ZonedDateTime;


/**
 * @author Pierre PINON
 */
public class ZonedDateTimeObjMapperDeserializer implements IObjMapperDeserializer<ZonedDateTime> {

    private final ZonedDateTimeDeserializer dateDeserializer;

    public ZonedDateTimeObjMapperDeserializer() {
        dateDeserializer = new ZonedDateTimeDeserializer();
    }

    @Override
    public ZonedDateTime deserialize(ObjMapper.ObjMapperContext objMapperContext, String value) throws ObjMapperException {
        try {
            return dateDeserializer.deserialize(value);
        } catch (DeserializeException e) {
            throw new ObjMapperException(e);
        }
    }
}
