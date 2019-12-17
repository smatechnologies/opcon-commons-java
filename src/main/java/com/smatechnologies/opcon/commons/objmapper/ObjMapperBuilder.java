package com.smatechnologies.opcon.commons.objmapper;

import com.smatechnologies.opcon.commons.objmapper.deserializer.CollectionObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.deserializer.DateObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.deserializer.LocalDateObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.deserializer.LocalDateTimeObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.deserializer.ZonedDateTimeObjMapperDeserializer;
import com.smatechnologies.opcon.commons.objmapper.serializer.CollectionObjMapperSerializer;
import com.smatechnologies.opcon.commons.objmapper.serializer.DateObjMapperSerializer;
import com.smatechnologies.opcon.commons.objmapper.serializer.LocalDateObjMapperSerializer;
import com.smatechnologies.opcon.commons.objmapper.serializer.LocalDateTimeObjMapperSerializer;
import com.smatechnologies.opcon.commons.objmapper.serializer.ZonedDateTimeObjMapperSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * @author Pierre PINON
 */
public class ObjMapperBuilder {

    public static ObjMapper createNewObjMapper() {
        ObjMapper objMapper = new ObjMapper();

        objMapper.addSerializer(Collection.class, CollectionObjMapperSerializer.class);
        objMapper.addSerializer(Date.class, DateObjMapperSerializer.class);
        objMapper.addSerializer(LocalDate.class, LocalDateObjMapperSerializer.class);
        objMapper.addSerializer(LocalDateTime.class, LocalDateTimeObjMapperSerializer.class);
        objMapper.addSerializer(ZonedDateTime.class, ZonedDateTimeObjMapperSerializer.class);

        objMapper.addDeserializer(List.class, CollectionObjMapperDeserializer.class);
        objMapper.addDeserializer(Date.class, DateObjMapperDeserializer.class);
        objMapper.addDeserializer(LocalDate.class, LocalDateObjMapperDeserializer.class);
        objMapper.addDeserializer(LocalDateTime.class, LocalDateTimeObjMapperDeserializer.class);
        objMapper.addDeserializer(ZonedDateTime.class, ZonedDateTimeObjMapperDeserializer.class);

        return objMapper;
    }
}
