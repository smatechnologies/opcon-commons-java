package com.smatechnologies.opcon.commons.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Use this class to keep only some fields of bean objects
 * @author Pierre PINON
 */
public class StripUtil {

    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";

    private StripUtil() {

    }

    public static <T> T stripAndKeepId(T object) {
        return strip(object, FIELD_ID);
    }

    public static <T> T stripAndKeepIdAndName(T object) {
        return strip(object, FIELD_ID, FIELD_NAME);
    }

    public static <T> T strip(T object, String... fieldsToKeep) {
        if (object == null) {
            return null;
        }

        try {
            @SuppressWarnings("unchecked")
            T stripObject = (T) object.getClass().newInstance();

            if (fieldsToKeep != null && fieldsToKeep.length != 0) {
                for (String fieldToKeep : fieldsToKeep) {
                    Field privateStringField = object.getClass().getDeclaredField(fieldToKeep);
                    privateStringField.setAccessible(true);

                    Object fieldValue = privateStringField.get(object);
                    privateStringField.set(stripObject, fieldValue);
                }
            }

            return stripObject;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalArgumentException("Cannot strip object", e);
        }
    }

    public static <T> Set<T> stripSetAndKeepId(Set<T> collection) {
        return stripSet(collection, FIELD_ID);
    }

    public static <T> Set<T> stripSetAndKeepIdAndName(Set<T> collection) {
        return stripSet(collection, FIELD_ID, FIELD_NAME);
    }

    public static <T> Set<T> stripSet(Set<T> collection, String... fieldsToKeep) {
        if (collection == null) {
            return null;
        }

        Set<T> newCollection = new HashSet<>();

        if (collection.isEmpty()) {
            return newCollection;
        }

        for (T object : collection) {
            newCollection.add(strip(object, fieldsToKeep));
        }

        return newCollection;
    }

    public static <T> List<T> stripListAndKeepId(List<T> collection) {
        return stripList(collection, FIELD_ID);
    }

    public static <T> List<T> stripListAndKeepIdAndName(List<T> collection) {
        return stripList(collection, FIELD_ID, FIELD_NAME);
    }

    public static <T> List<T> stripList(List<T> collection, String... fieldsToKeep) {
        if (collection == null) {
            return null;
        }

        List<T> newCollection = new ArrayList<>();

        if (collection.isEmpty()) {
            return newCollection;
        }

        for (T object : collection) {
            newCollection.add(strip(object, fieldsToKeep));
        }

        return newCollection;
    }
}
