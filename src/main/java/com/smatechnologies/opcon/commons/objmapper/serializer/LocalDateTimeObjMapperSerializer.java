package com.smatechnologies.opcon.commons.objmapper.serializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.serializer.LocalDateTimeSerializer;
import com.smatechnologies.opcon.commons.serializer.SerializeException;

import java.time.LocalDateTime;


/**
 * @author Pierre PINON
 */
public class LocalDateTimeObjMapperSerializer implements IObjMapperSerializer<LocalDateTime> {

    private final LocalDateTimeSerializer dateSerializer;

    public LocalDateTimeObjMapperSerializer() {
        dateSerializer = new LocalDateTimeSerializer();
    }

    @Override
    public String serialize(ObjMapper.ObjMapperContext objMapperContext, LocalDateTime value) throws ObjMapperException {
        try {
            return dateSerializer.serialize(value);
        } catch (SerializeException e) {
            throw new ObjMapperException(e);
        }
    }
}
