package com.smatechnologies.opcon.commons.objmapper.annotation;

import com.smatechnologies.opcon.commons.objmapper.deserializer.IObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.modifier.IObjMapperModifier;
import com.smatechnologies.opcon.commons.objmapper.serializer.IObjMapperSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Pierre PINON
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjMapperCollectionField {

    Class<? extends IObjMapperSerializer<?>> serializer() default IObjMapperSerializer.NoObjMapperSerializer.class;

    Class<? extends IObjMapperDeserializer<?>> deserializer() default IObjMapperDeserializer.NoObjMapperDeserializer.class;

    Class<? extends IObjMapperModifier<?>> serializeModifier() default IObjMapperModifier.NoObjMapperModifier.class;

    Class<? extends IObjMapperModifier<?>> deserializeModifier() default IObjMapperModifier.NoObjMapperModifier.class;
}
