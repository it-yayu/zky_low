package com.sx.common.validater;


import com.sx.common.annotation.SxLength;
import com.sx.helper.StringHelper;
import com.sx.util.AjaxJsonResult;

import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @author 
 * @version [1.0, 2017年10月24日]
 * @ClassName: EntityValidate
 * @Description: TODO(实体类校验)
 * @date 2017年10月24日 上午10:12:43
 * @since version 1.0
 */
public abstract class EntityValidater {
    /**
     * @param errors
     * @param ajaxJsonResult
     * @return boolean    返回类型
     * @Title: checkError
     * @Description: TODO 实体类检验

     */
    public boolean checkError(Errors errors, AjaxJsonResult ajaxJsonResult) {
        // 校验输入项

        if (errors.hasErrors()) {
            if (errors.getFieldError().getDefaultMessage() != null) {
                ajaxJsonResult.setCodeAndMsg(AjaxJsonResult.CODE_CHECKFAILD, errors.getFieldError().getDefaultMessage());
            }
            return false;
        }
        return true;
    }
    
    /**
     * 实体类检验（包含代码表校验）
     *
     * @param errors
     * @param ajaxJsonResult
     * @param obj
     * @return
     */
    public boolean checkError(Errors errors, AjaxJsonResult ajaxJsonResult, Object obj) throws IllegalAccessException {
        if (!this.checkError(errors, ajaxJsonResult)) {
            return false;
        }
        if (obj != null) {
            String msg = checkError(obj);
            if (StringHelper.isNotEmpty(msg)) {
                ajaxJsonResult.setCodeAndMsg(AjaxJsonResult.CODE_CHECKFAILD, msg);
                return false;
            }
        }
        return true;
    }


   /* *//**
     * 实体类检验（代码表校验）
     *
     * @param obj
     * @return
     */
    public String checkError(Object obj) throws IllegalAccessException {
        String msg = null;
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
            	 if(field.getType().isArray()){
            		 field.setAccessible(true);
            		 Object[] stl =(Object[]) field.get(obj);
            		 for (int i = 0; i < stl.length; i++) {
						Class stlc = stl[i].getClass();
						Method[] methods = stlc.getMethods();
						Field[] fields1 = stlc.getDeclaredFields();
						for (int j = 0; j < fields1.length; j++) {
							fields1[j].setAccessible(true);
							SxLength length =fields1[j].getAnnotation(SxLength.class);
							Pattern pattern = fields1[j].getAnnotation(Pattern.class);
                            NotNull notNull = fields1[j].getAnnotation(NotNull.class);
							String value = String.valueOf(fields1[j].get(stl[i]));
							if(notNull!=null){
                                if(StringHelper.isEmpty(value)){
                                    return notNull.message();
                                }
                            }
							if(length!=null){
								Long min = length.min();
								Long max = length.max();
								if(StringHelper.isEmpty(value) && min!=0){
									return length.message();
								}
								if(value.length()>max){
									return length.message();
								}
							}
							if(pattern!=null){
								String jy = pattern.regexp();
								if(!value.matches(jy)){
									return pattern.message();
								}
								//pattern.message()
							}
						}
					}
            	 }
            	
            }
        }
        return msg;
    }
}
