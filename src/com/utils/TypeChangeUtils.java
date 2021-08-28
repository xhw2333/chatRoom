package com.utils;

import java.lang.reflect.Array;

/**
 * 类型转换工具类
 */
public class TypeChangeUtils {
    public static void main(String[] args) {
        String str = "[]";
//        String[] sty = str.substring(1,str.length()-1).split(",");
        int[] strs = strToInt(str.substring(1,str.length()-1));
        System.out.println(strs.length);
//        System.out.println(sty[0]);
    }

    /**
     * 字符串转换为int数组
     * @param str
     * @return
     */
    public static int[] strToInt(String str){
//        字符串小于0，直接返回空数组
        if(str.length() <= 0){
            return new int[0];
        }
//        根据逗号划分成数组项
        String[] strs = str.split("\\,");
        int [] intArray = new int[strs.length];
        for(int i = 0; i < strs.length; ++i){
//            转换为整型
            intArray[i] = Integer.parseInt(strs[i]);
        }
        return intArray;
    }
}
