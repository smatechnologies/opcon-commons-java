package com.smatechnologies.opcon.commons.objmapper.deserializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;


/**
 * @author Pierre PINON
 */
public interface IObjMapperDeserializer<T> {

    T deserialize(ObjMapper.ObjMapperContext objMapperContext, String value) throws ObjMapperException;

    /**
     * This marker class is only to be used with annotations, to indicate that <b>no deserializer is configured</b>.
     */
    abstract class NoObjMapperDeserializer implements IObjMapperDeserializer<Object> {

    }
}
