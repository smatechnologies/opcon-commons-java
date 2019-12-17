package com.smatechnologies.opcon.commons.objmapper.deserializer;

import com.smatechnologies.opcon.commons.deserializer.DateDeserializer;
import com.smatechnologies.opcon.commons.deserializer.DeserializeException;
import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;

import java.util.Date;


/**
 * @author Pierre PINON
 */
public class DateObjMapperDeserializer implements IObjMapperDeserializer<Date> {

    private final DateDeserializer dateDeserializer;

    public DateObjMapperDeserializer() {
        dateDeserializer = new DateDeserializer();
    }

    @Override
    public Date deserialize(ObjMapper.ObjMapperContext objMapperContext, String value) throws ObjMapperException {
        try {
            return dateDeserializer.deserialize(value);
        } catch (DeserializeException e) {
            throw new ObjMapperException(e);
        }
    }
}
