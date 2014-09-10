package com.bbkmobile.iqoo.platform.annotation.field.type;

public enum ProjectionType {
    PROPERTY;
    public Boolean isDefault() {
        return this.equals(PROPERTY);
    }
}
