package com.smatechnologies.opcon.commons.objmapper.serializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.serializer.LocalDateSerializer;
import com.smatechnologies.opcon.commons.serializer.SerializeException;

import java.time.LocalDate;


/**
 * @author Pierre PINON
 */
public class LocalDateObjMapperSerializer implements IObjMapperSerializer<LocalDate> {

    private final LocalDateSerializer dateSerializer;

    public LocalDateObjMapperSerializer() {
        dateSerializer = new LocalDateSerializer();
    }

    @Override
    public String serialize(ObjMapper.ObjMapperContext objMapperContext, LocalDate value) throws ObjMapperException {
        try {
            return dateSerializer.serialize(value);
        } catch (SerializeException e) {
            throw new ObjMapperException(e);
        }
    }
}
