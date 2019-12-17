package com.smatechnologies.opcon.commons.objmapper.serializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.serializer.DateSerializer;
import com.smatechnologies.opcon.commons.serializer.SerializeException;

import java.util.Date;


/**
 * @author Pierre PINON
 */
public class DateObjMapperSerializer implements IObjMapperSerializer<Date> {

    private final DateSerializer dateSerializer;

    public DateObjMapperSerializer() {
        dateSerializer = new DateSerializer();
    }

    @Override
    public String serialize(ObjMapper.ObjMapperContext objMapperContext, Date value) throws ObjMapperException {
        try {
            return dateSerializer.serialize(value);
        } catch (SerializeException e) {
            throw new ObjMapperException(e);
        }
    }
}
