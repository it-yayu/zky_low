package com.sx.common.annotation;



import com.sx.common.config.SxValidatorMessage;
import com.sx.common.validater.SxLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** 
 * 校验字符串长度，中文按照charset进行计算
 * @author hanchao
 * @version [1.0, 2017-12-24-]
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SxLengthValidator.class)
@Documented
public @interface SxLength {
	
	long min() default 0;
	long max() default Integer.MAX_VALUE; 
	String charset() default "gbk";  
	
	String message() default SxValidatorMessage.SX_LENGTH_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}