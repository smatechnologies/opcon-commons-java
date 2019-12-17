package com.smatechnologies.opcon.commons.objmapper.modifier;

/**
 * @author Pierre PINON
 */
public class DefaultObjMapperModifier implements IObjMapperModifier<Object> {

    @Override
    public Object modify(Object value) {
        return value;
    }
}
