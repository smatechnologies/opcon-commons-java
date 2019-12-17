package com.smatechnologies.opcon.commons.deserializer;

/**
 * @author Pierre PINON
 */
public interface IDeserializer<T> {

    T deserialize(String value) throws DeserializeException;
}
