package com.bbkmobile.iqoo.platform.annotation.field.type;

import java.util.Map;

import org.hibernate.criterion.MatchMode;

import com.bbkmobile.iqoo.common.util.ReflectUtil;

public enum MatchModeType {
    EXACT, START, END, ANYWHERE;
    public static Map<String, MatchMode> INSTANCES;

    @SuppressWarnings("unchecked")
    public MatchMode getMatchMode() {
        if (INSTANCES == null) {
            INSTANCES = (Map<String, MatchMode>) ReflectUtil
                    .getClassPropertyValue(MatchMode.class, "INSTANCES");
        }
        return INSTANCES.get(this.toString());
    }

    public Boolean isDefault() {
        return this.equals(EXACT);
    }
}
