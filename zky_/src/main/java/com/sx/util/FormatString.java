package com.sx.util;

/**
 * 取小数点后两位.
 */

public class FormatString {

    public static  String formateRate(String rateStr){
        if(rateStr.indexOf(".") != -1){
            //获取小数点的位置
            int num = 0;
            num = rateStr.indexOf(".");

            //获取小数点后面的数字 是否有两位 不足两位补足两位
            String dianAfter = rateStr.substring(0,num+1);
            String afterData = rateStr.replace(dianAfter, "");
            if(afterData.length() < 2){
                afterData = afterData + "0" ;
            }else{
                afterData = afterData;
            }
            return rateStr.substring(0,num) + "." + afterData.substring(0,2);
        }else{
            if(rateStr == "1"){
                return "100";
            }else{
                return rateStr;
            }
        }
    }

//    public static void main(String[] args) {
//
//        System.out.println(formateRate("2.13333"));
//    }

}
