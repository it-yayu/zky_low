package com.sx.interceptor;

/**
 * @author windy
 *
 */
public @interface AvoidDuplicateSubmission {
	boolean needSaveToken() default false;
    boolean needRemoveToken() default false;
}
