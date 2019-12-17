package com.smatechnologies.opcon.commons.objmapper;

/**
 * @author Pierre PINON
 */
public class UrlParameterMapperBuilder {

    public static UrlParameterMapper createNewParameterMapper() {
        UrlParameterMapper urlParameterMapper = new UrlParameterMapper();

        urlParameterMapper.setObjMapper(ObjMapperBuilder.createNewObjMapper());

        return urlParameterMapper;
    }
}
