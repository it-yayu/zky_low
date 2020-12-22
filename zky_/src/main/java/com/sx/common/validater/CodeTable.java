package com.sx.common.validater;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CodeTable {

    String tablename();
    String columnName();
    String message();
}
