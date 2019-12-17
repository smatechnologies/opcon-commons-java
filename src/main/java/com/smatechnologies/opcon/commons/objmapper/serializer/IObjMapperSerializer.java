package com.smatechnologies.opcon.commons.objmapper.serializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;


/**
 * @author Pierre PINON
 */
public interface IObjMapperSerializer<T> {

    String serialize(ObjMapper.ObjMapperContext objMapperContext, T value) throws ObjMapperException;

    /**
     * This marker class is only to be used with annotations, to indicate that <b>no serializer is configured</b>.
     */
    abstract class NoObjMapperSerializer implements IObjMapperSerializer<Object> {

    }
}
