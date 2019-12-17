package com.smatechnologies.opcon.commons.objmapper.serializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.serializer.SerializeException;
import com.smatechnologies.opcon.commons.serializer.ZonedDateTimeSerializer;

import java.time.ZonedDateTime;


/**
 * @author Pierre PINON
 */
public class ZonedDateTimeObjMapperSerializer implements IObjMapperSerializer<ZonedDateTime> {

    private final ZonedDateTimeSerializer dateSerializer;

    public ZonedDateTimeObjMapperSerializer() {
        dateSerializer = new ZonedDateTimeSerializer();
    }

    @Override
    public String serialize(ObjMapper.ObjMapperContext objMapperContext, ZonedDateTime value) throws ObjMapperException {
        try {
            return dateSerializer.serialize(value);
        } catch (SerializeException e) {
            throw new ObjMapperException(e);
        }
    }
}
