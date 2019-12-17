package com.smatechnologies.opcon.commons.objmapper.serializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperCollectionField;
import com.smatechnologies.opcon.commons.objmapper.modifier.IObjMapperModifier;

import java.util.Collection;
import java.util.StringJoiner;


/**
 * @author Pierre PINON
 */
public class CollectionObjMapperSerializer implements IObjMapperSerializer<Collection> {

    private static final String SEPARATOR = ",";

    public String serialize(ObjMapper.ObjMapperContext objMapperContext, Collection value) throws ObjMapperException {
        if (value == null) {
            return null;
        }

        Class<? extends IObjMapperSerializer<Object>> serializerClass;
        Class<IObjMapperModifier<Object>> modifierClass;
        if (objMapperContext.getField().isAnnotationPresent(ObjMapperCollectionField.class)) {
            ObjMapperCollectionField annotation = objMapperContext.getField().getAnnotation(ObjMapperCollectionField.class);

            //Serializer
            serializerClass = (Class) annotation.serializer();
            if (IObjMapperSerializer.NoObjMapperSerializer.class.isAssignableFrom(serializerClass)) {
                serializerClass = null;
            }

            //Modifier
            modifierClass = (Class) annotation.serializeModifier();
            if (IObjMapperModifier.NoObjMapperModifier.class.isAssignableFrom(modifierClass)) {
                modifierClass = null;
            }
        } else {
            serializerClass = null;
            modifierClass = null;
        }

        StringJoiner stringJoiner = new StringJoiner(SEPARATOR);
        for (Object val : value) {
            String subValue = objMapperContext.getObjMapper().serializeField(objMapperContext, (val == null ? null : (Class)val.getClass()), val, serializerClass, modifierClass);
            stringJoiner.add(subValue);
        }

        return stringJoiner.toString();
    }
}
