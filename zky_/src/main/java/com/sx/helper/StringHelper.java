package com.sx.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.sx.utility.StringTools;
import org.apache.commons.beanutils.BeanUtils;

@SuppressWarnings("all")
public class StringHelper extends StringTools {

    private StringHelper() {
    }

    /**
     * 去对象空格
     */
    public static void ntos(Object obj) {
        try {
            Class dataBeanClass = obj.getClass();
            Field dataBeanField[] = dataBeanClass.getDeclaredFields();
            String f_name;// 参数名称
            String m_name;// 方法名称
            Method gmethod;// 方法
            Method smethod;// 方法
            String c_value;// 值
            // 处理插入的结构列
            for (int i = 0; i < dataBeanField.length; i++) {
                if (!(dataBeanField[i].getType().equals("test".getClass()))) {// 如果不是字符型
                    continue;
                }
                f_name = dataBeanField[i].getName();// 列名
                m_name = "get" + Character.toUpperCase(f_name.charAt(0)) + f_name.substring(1);// 得到该列的get方法
                gmethod = obj.getClass().getMethod(m_name, null);
                m_name = "set" + Character.toUpperCase(f_name.charAt(0)) + f_name.substring(1);// 得到该列的get方法
                smethod = obj.getClass().getMethod(m_name,
                        new Class[] { dataBeanField[i].getType() });
                c_value = (String) gmethod.invoke(obj, null);// 取值
                c_value = toTrim(c_value);
                smethod.invoke(obj, new Object[] { c_value });// 符值
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
    * 判断字符串是否为非空
    * @param string
    * @return
    */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 判断字符串是否为空
     * @param string
     * @return
     */
    public static boolean isEmpty(String str) {
        str = toTrim(str);
        if ("".equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 描述：将身份证号码最后一位字母转换成大写
     */
    public static String formatSfzhm(String sfzhm) {
        if (isEmpty(sfzhm)) {
            return sfzhm;
        }
        return sfzhm.toUpperCase();
    }

    /**
     * 用途：调用此方法可将页面中的空值（null,"","null"）转换为"&nbsp;"。
     */
    public static String vtoh(String temp) {
        if (toTrim(temp).equals("")) {
            return "&nbsp";
        }
        return temp;
    }

    /**
     * <pre>
     *     把valueBean中的所有字符属性执行stringtools.totrim()方法
     *     这个方法包括继承得来的属性
     * </pre>
     * @param obj
     */
    public static void ntosWithSuper(Object obj) {
        try {
            Class dataBeanClass = obj.getClass();
            String temp;
            Method[] dataMethod = dataBeanClass.getMethods();// 得到所有的方法
            Method gmethod;// 对象的get方法
            String c_value;// 值
            for (int i = 0; i < dataMethod.length; i++) {
                // 如果不是set方法，继续
                if (dataMethod[i].getName().indexOf("set") == -1) {
                    continue;
                }
                // 如果不是字符类型，继续
                if (!dataMethod[i].getParameterTypes()[0].equals("test".getClass())) {
                    continue;
                }
                // 从方法名称中得到set后面的字符
                temp = dataMethod[i].getName()
                        .substring(dataMethod[i].getName().indexOf("set") + 3);
                gmethod = obj.getClass().getMethod("get" + temp, null);
                c_value = (String) gmethod.invoke(obj, null);// 取值
                c_value = StringTools.toTrim(c_value);
                dataMethod[i].invoke(obj, new Object[] { c_value });// 符值
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static String format(String input, int length) {
        String temp = "<SPAN title=\"" + input + "\">";
        temp += StringTools.fmtString(input, length);
        temp += "</SPAN>";
        return temp;
    }

    /**
     * 关于字符撑坏表格的问题
     * ss 字符串
     * dc 指定的长度
     * lx 2007-09-22  
     */
    public static String strFormat(String ss, int dc) {
        try {
            String temp = "";
            int qq = 0;
            if (ss.length() > 0) {
                qq = ss.length() / dc;
            }
            for (int i = 0; i < qq; i++) {
                temp += ss.substring(0, dc) + "\r\n";
                ss = ss.substring(dc);
            }
            temp += ss;
            return temp;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 把输入的字符型数字乘100返回整数部分
     * @param temp
     * @return
     */
    public static int toInt(String temp) {
        return (int) (Double.parseDouble(temp) * 100 + 0.5d);
    }

    /**
     * formBean转换成ValueBean
     *
     * @param obj1,obj2
     * @return
     */
    public static void formBeanToValueBean(Object obj1, Object obj2)
            throws Exception {

        BeanUtils.copyProperties(obj2, obj1);
        StringHelper.ntos(obj2);

    }

    /**
     * ValueBean转换成formbean
     *
     * @param obj1
     * @param obj2
     * @throws Exception
     */
    public static void valueBeanToFormBean(Object obj1, Object obj2)
            throws Exception {

        BeanUtils.copyProperties(obj2, obj1);
        StringHelper.ntos(obj2);

    }

}
