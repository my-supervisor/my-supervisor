package com.supervisor.sdk.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface Field {
	int order() default -1;
	String name() default "NONE";
	String label() default "NONE";
	Relation rel() default Relation.SIMPLE;
	boolean isKey() default false;
	boolean isAuto() default true;
	boolean forceInherit() default false;
	boolean isMandatory() default true;
	boolean ignore() default false;
}
