package com.smatechnologies.opcon.commons.objmapper.modifier;

/**
 * @author Pierre PINON
 */
public interface IObjMapperModifier<T> {

    T modify(T value);

    /**
     * This marker class is only to be used with annotations, to indicate that <b>no modifier is configured</b>.
     */
    abstract class NoObjMapperModifier implements IObjMapperModifier<Object> {

    }
}
