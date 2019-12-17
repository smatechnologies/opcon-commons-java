package com.smatechnologies.opcon.commons.serializer;

/**
 * @author Pierre PINON
 */
public interface ISerializer<T> {

    String serialize(T value) throws SerializeException;
}
