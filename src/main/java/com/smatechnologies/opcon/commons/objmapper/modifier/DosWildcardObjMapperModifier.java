package com.smatechnologies.opcon.commons.objmapper.modifier;

import com.smatechnologies.opcon.commons.util.StringUtil;


/**
 * @author Pierre PINON
 */
public class DosWildcardObjMapperModifier implements IObjMapperModifier<String> {

    @Override
    public String modify(String value) {
        return StringUtil.convertDosWildcardStringToOpconApiString(StringUtil.emptyToNull(value), false);
    }
}
