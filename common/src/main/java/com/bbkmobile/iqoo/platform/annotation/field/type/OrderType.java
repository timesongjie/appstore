package com.bbkmobile.iqoo.platform.annotation.field.type;

public enum OrderType {
    NULL, ASC, DESC;
    public Boolean isDefault() {
        return this.equals(NULL);
    }
}
