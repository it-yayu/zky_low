package com.sx.helper;


public class ExceptionMessage {
	
	
	
	public String getExceptionCode(String message){
		String exceptionType = null;
		if(null != message && !"".equals(message)){
			exceptionType = message.split("[.]")[message.split("[.]").length-1];
		}
		return exceptionType;
	}
	
}
