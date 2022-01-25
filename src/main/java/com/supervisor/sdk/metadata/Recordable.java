package com.supervisor.sdk.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Recordable {
	String name() default "NONE";
	String label() default "NONE";
	boolean inheritFields() default true;
	Class<?> comodel() default Object.class;
}
