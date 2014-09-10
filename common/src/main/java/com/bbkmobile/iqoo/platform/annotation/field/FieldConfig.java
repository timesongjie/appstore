package com.bbkmobile.iqoo.platform.annotation.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bbkmobile.iqoo.platform.annotation.field.type.MatchModeType;
import com.bbkmobile.iqoo.platform.annotation.field.type.OrderType;
import com.bbkmobile.iqoo.platform.annotation.field.type.ProjectionType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldConfig {
    ProjectionType criterionProjection() default ProjectionType.PROPERTY;

    MatchModeType criterionMatchMode() default MatchModeType.EXACT;

    OrderType criterionOrder() default OrderType.NULL;
}
