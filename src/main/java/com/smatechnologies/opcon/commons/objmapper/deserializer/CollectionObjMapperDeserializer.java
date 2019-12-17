package com.smatechnologies.opcon.commons.objmapper.deserializer;

import com.smatechnologies.opcon.commons.objmapper.ObjMapper;
import com.smatechnologies.opcon.commons.objmapper.ObjMapperException;
import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperCollectionField;
import com.smatechnologies.opcon.commons.objmapper.modifier.IObjMapperModifier;
import com.smatechnologies.opcon.commons.objmapper.serializer.IObjMapperSerializer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;


/**
 * @author Pierre PINON
 */
public class CollectionObjMapperDeserializer implements IObjMapperDeserializer<List> {

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
            String subValue = objMapperContext.getObjMapper().serializeField(objMapperContext, (val == null ? null : (Class) val.getClass()), val, serializerClass, modifierClass);
            stringJoiner.add(subValue);
        }

        return stringJoiner.toString();
    }

    @Override
    public List deserialize(ObjMapper.ObjMapperContext objMapperContext, String value) throws ObjMapperException {
        String[] values = value.split(SEPARATOR);

        Class<? extends IObjMapperDeserializer<Object>> deserializerClass;
        Class<IObjMapperModifier<Object>> modifierClass;
        if (objMapperContext.getField().isAnnotationPresent(ObjMapperCollectionField.class)) {
            ObjMapperCollectionField annotation = objMapperContext.getField().getAnnotation(ObjMapperCollectionField.class);

            //Deserializer
            deserializerClass = (Class) annotation.deserializer();
            if (IObjMapperDeserializer.NoObjMapperDeserializer.class.isAssignableFrom(deserializerClass)) {
                deserializerClass = null;
            }

            //Modifier
            modifierClass = (Class) annotation.deserializeModifier();
            if (IObjMapperModifier.NoObjMapperModifier.class.isAssignableFrom(modifierClass)) {
                modifierClass = null;
            }
        } else {
            deserializerClass = null;
            modifierClass = null;
        }

        Class<? extends IObjMapperDeserializer<Object>> finalDeserializerClass = deserializerClass;
        Class<IObjMapperModifier<Object>> finalModifierClass = modifierClass;

        return Arrays.stream(values)
                .map(val -> {
                    try {
                        return objMapperContext.getObjMapper().deserializeField(objMapperContext, (Class) String.class, val, finalDeserializerClass, finalModifierClass);
                    } catch (ObjMapperException e) {
                        return val;
                    }
                })
                .collect(Collectors.toList());
    }
}
