package com.sx.common.validater;


import com.sx.common.annotation.SxLength;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.UnsupportedEncodingException;

/** 
 * 校验字符串长度，中文按照charset进行计算
 * @author hanchao
 * @version [1.0, 2017-12-24-]
 */
public class SxLengthValidator implements ConstraintValidator<SxLength, String> {

	private static final Log log = LoggerFactory.make();
	private long min;
	private long max;
	private String charset;
	
	@Override
	public void initialize(SxLength parameters) {
		//do nothing
		this.min = parameters.min();
		this.max = parameters.max();
		this.charset = parameters.charset();
		validateParameters();
	}
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(null == value){
			value = "";
		}
		long length = 0;
		try {
			length = ((String)value).getBytes(charset).length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean result = (length >= min) && (length <= max);
		log.info("sxlength.validator:[value:" + (String)value);
		log.info("sxlength.validator:[min:" + min + ",max:" + max + ",length:" + length + "],result:" + result);
		return (length >= min) && (length <= max);
	}
	private void validateParameters() {
	    if (this.min < 0) {
	      throw log.getMinCannotBeNegativeException();
	    }
	    if (this.max < 0) {
	      throw log.getMaxCannotBeNegativeException();
	    }
	    if (this.max < this.min)
	      throw log.getLengthCannotBeNegativeException();
	}
}