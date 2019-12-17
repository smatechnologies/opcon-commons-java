package com.smatechnologies.opcon.commons.objmapper;

import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperCreator;
import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperField;
import com.smatechnologies.opcon.commons.objmapper.annotation.ObjMapperValue;
import com.smatechnologies.opcon.commons.objmapper.deserializer.IObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.modifier.IObjMapperModifier;
import com.smatechnologies.opcon.commons.objmapper.serializer.IObjMapperSerializer;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Pierre PINON
 */
public class ObjMapper {

    private final Map<Class<?>, Class<? extends IObjMapperSerializer<?>>> classToSerializerClasses = new LinkedHashMap<>();
    private final Map<Class<?>, Class<? extends IObjMapperDeserializer<?>>> classToDeserializersClasses = new LinkedHashMap<>();

    public <T> void addSerializer(Class<T> clazz, Class<? extends IObjMapperSerializer<T>> serializerClass) {
        classToSerializerClasses.put(clazz, serializerClass);
    }

    public <T> void addDeserializer(Class<T> clazz, Class<? extends IObjMapperDeserializer<T>> deserializerClass) {
        classToDeserializersClasses.put(clazz, deserializerClass);
    }

    public <T> String serializeField(ObjMapperContext objMapperContext, Class<T> fieldType, T field, Class<? extends IObjMapperSerializer<T>> serializerClass, Class<? extends IObjMapperModifier<T>> modifierClass) throws ObjMapperException {
        //Modifier
        if (modifierClass != null) {
            IObjMapperModifier<T> modifier;
            try {
                modifier = modifierClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ObjMapperException("Cannot create new instance of serializer", e);
            }
            field = modifier.modify(field);
        }

        //Tried to find compatible serializer
        if (serializerClass == null && fieldType != null) {
            serializerClass = classToSerializerClasses.entrySet().stream()
                    .filter(classToSerializerClass -> classToSerializerClass.getKey().isAssignableFrom(fieldType))
                    .map(classToSerializerClass -> (Class<IObjMapperSerializer<T>>) classToSerializerClass.getValue())
                    .findFirst()
                    .orElse(null);
        }
        if (serializerClass != null) {
            try {
                IObjMapperSerializer<T> serializer = serializerClass.getDeclaredConstructor().newInstance();

                return serializer.serialize(objMapperContext, field);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ObjMapperException("Cannot create new instance of serializer", e);
            }
        } else if (field == null) {
            return "";
        } else {
            List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(field.getClass(), ObjMapperValue.class, true, true);

            Method methodAnnotation = methodsListWithAnnotation.stream()
                    .filter(method -> method.getParameterTypes().length == 0 && String.class.isAssignableFrom(method.getReturnType()))
                    .findFirst()
                    .orElse(null);

            if (methodAnnotation == null) {
                return field.toString();
            } else {
                methodAnnotation.setAccessible(true);

                try {
                    return (String) methodAnnotation.invoke(field);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new ObjMapperException(e);
                }
            }
        }
    }

    public <T> T deserializeField(ObjMapperContext objMapperContext, Class<T> fieldType, String string, Class<? extends IObjMapperDeserializer<T>> deserializerClass, Class<? extends IObjMapperModifier<T>> modifierClass) throws ObjMapperException {
        T value;

        IObjMapperDeserializer<T> deserializer;
        if (deserializerClass == null) {
            deserializerClass = classToDeserializersClasses.entrySet().stream()
                    .filter(classToDeserializerClass -> fieldType.isAssignableFrom(classToDeserializerClass.getKey()))
                    .map(classToDeserializerClass -> (Class<IObjMapperDeserializer<T>>) classToDeserializerClass.getValue())
                    .findFirst()
                    .orElse(null);
        }
        if (deserializerClass != null) {
            try {
                deserializer = deserializerClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ObjMapperException("Cannot create new instance of deserializer", e);
            }

            value = deserializer.deserialize(objMapperContext, string);
        } else {
            List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(fieldType, ObjMapperCreator.class, true, true);

            Method methodAnnotation = methodsListWithAnnotation.stream()
                    .filter(method -> Modifier.isStatic(method.getModifiers()) && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].isAssignableFrom(String.class) && fieldType.isAssignableFrom(method.getReturnType()))
                    .findFirst()
                    .orElse(null);

            if (methodAnnotation == null) {
                if (fieldType.isAssignableFrom(String.class)) {
                    value = (T) string;
                } else if (fieldType.isAssignableFrom(Integer.class) || fieldType.isAssignableFrom(Integer.TYPE)) {
                    value = (T) Integer.valueOf(string);
                } else if (fieldType.isAssignableFrom(Boolean.class) || fieldType.isAssignableFrom(Boolean.TYPE)) {
                    value = (T) Boolean.valueOf(string);
                } else {
                    throw new ObjMapperException("Cannot find unserializer");
                }
            } else {
                methodAnnotation.setAccessible(true);

                try {
                    value = (T) methodAnnotation.invoke(null, string);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new ObjMapperException(e);
                }
            }
        }

        //Modifier
        if (modifierClass != null) {
            IObjMapperModifier<T> modifier;
            try {
                modifier = modifierClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ObjMapperException("Cannot create new instance of serializer", e);
            }
            value = modifier.modify(value);
        }

        return value;
    }

    public <T> Map<String, String> serialize(T object) throws ObjMapperException {
        //Get all fields annotated with ObjMapperField annotation
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(object.getClass(), ObjMapperField.class);

        Map<String, String> map = new LinkedHashMap<>();

        for (Field field : fields) {
            //Allows to access private field
            field.setAccessible(true);

            ObjMapperField objMapperFieldAnnotation = field.getAnnotation(ObjMapperField.class);

            //Name from fieldName or Annotation
            String annotationName = objMapperFieldAnnotation.value();
            String name = annotationName.isEmpty() ? field.getName() : annotationName;

            //Serializer
            Class<IObjMapperSerializer<Object>> serializerClass = (Class) objMapperFieldAnnotation.serializer();
            if (IObjMapperSerializer.NoObjMapperSerializer.class.isAssignableFrom(serializerClass)) {
                serializerClass = null;
            }

            try {
                Object fieldValue = field.get(object);

                //Modifier
                Class<IObjMapperModifier<Object>> modifierClass = (Class) objMapperFieldAnnotation.serializeModifier();
                if (IObjMapperModifier.NoObjMapperModifier.class.isAssignableFrom(modifierClass)) {
                    modifierClass = null;
                }

                if (fieldValue != null) {
                    @SuppressWarnings("unchecked")
                    String value = serializeField(createNewContext(field), (Class) field.getType(), fieldValue, serializerClass, modifierClass);

                    map.put(name, value);
                }
            } catch (ObjMapperException e) {
                throw new ObjMapperException("Cannot serialize value of field [" + name + "] with serializer [" + serializerClass.getSimpleName() + "]", e);
            } catch (IllegalAccessException e) {
                throw new ObjMapperException("Cannot get value of field [" + name, e);
            }
        }

        return map;
    }

    public <T> T deserializer(Class<T> clazz, Map<String, String> map) throws ObjMapperException {
        T object;
        try {
            object = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ObjMapperException("Cannot create new instance of object", e);
        }

        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(clazz, ObjMapperField.class);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            String stringValue = entry.getValue();

            Field field = fields.stream()
                    .filter(currentField -> {
                        ObjMapperField objMapperFieldAnnotation = currentField.getAnnotation(ObjMapperField.class);

                        return name.equals(objMapperFieldAnnotation.value()) || name.equals(currentField.getName());
                    })
                    .findFirst()
                    .orElse(null);

            if (field != null) {
                ObjMapperField objMapperFieldAnnotation = field.getAnnotation(ObjMapperField.class);

                //Serializer
                Class<IObjMapperDeserializer<Object>> deserializerClass = (Class) objMapperFieldAnnotation.deserializer();
                if (IObjMapperDeserializer.NoObjMapperDeserializer.class.isAssignableFrom(deserializerClass)) {
                    deserializerClass = null;
                }

                //Modifier
                Class<IObjMapperModifier<Object>> modifierClass = (Class) objMapperFieldAnnotation.serializeModifier();
                if (IObjMapperModifier.NoObjMapperModifier.class.isAssignableFrom(modifierClass)) {
                    modifierClass = null;
                }

                //Allows to access private field
                field.setAccessible(true);

                //Serialize value
                try {
                    @SuppressWarnings("unchecked")
                    Object value = deserializeField(createNewContext(field), (Class) field.getType(), stringValue, deserializerClass, modifierClass);

                    field.set(object, value);
                } catch (ObjMapperException e) {
                    throw new ObjMapperException("Cannot deserialize value of field [" + name + "] with deserializer [" + ((deserializerClass == null) ? null : deserializerClass.getSimpleName()) + "]", e);
                } catch (IllegalAccessException e) {
                    throw new ObjMapperException("Cannot set the value of field", e);
                }
            }
        }

        return object;
    }

    private ObjMapperContext createNewContext(Field field) {
        return new ObjMapperContext() {

            @Override
            public ObjMapper getObjMapper() {
                return ObjMapper.this;
            }

            @Override
            public Field getField() {
                return field;
            }
        };
    }

    public interface ObjMapperContext {

        ObjMapper getObjMapper();

        Field getField();
    }
}
