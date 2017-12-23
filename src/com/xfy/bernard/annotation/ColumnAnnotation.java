package com.xfy.bernard.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ColumnAnnotation {
	String column();

	ColumnSortEnum sort() default ColumnSortEnum.ASC;

	DatabaseValueTypeEnum type() default DatabaseValueTypeEnum.VARCHAR;

	DatabaseAnnotation databaseInfo() default @DatabaseAnnotation(name = "oracle", version = "11g", driverClass = "oracle.jdbc.com");
}
