package com.yiminwu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

   public static String toString(Object obj, String defaultStr) {
      if (null == obj) {
         return defaultStr;
      } else {
         return obj.toString();
      }
   }

   public static int toInt(Object srcStr, int defaultValue) {
      try {
         if (srcStr != null && StringUtil.isInt(srcStr)) {
            String s = srcStr.toString().replaceAll("(\\s)", "");
            return s.length() > 0 ? Integer.parseInt(s) : defaultValue;
         }
      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }

   public static Integer toInteger(Object srcStr, Integer defaultValue) {
      try {
         if (srcStr != null && StringUtil.isInt(srcStr)) {
            String s = srcStr.toString().replaceAll("(\\s)", "");
            return s.length() > 0 ? Integer.parseInt(s) : defaultValue;
         }
      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }

   public static Long toLong(Object srcStr, Long defaultValue) {
      try {
         if (srcStr != null && StringUtil.isInt(srcStr)) {
            String s = srcStr.toString().replaceAll("(\\s)", "");
            return s.length() > 0 ? Long.parseLong(s) : defaultValue;
         }
      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }
   
   public static Integer toInteger(Object obj){
      if(obj != null){
         if( obj instanceof Number){
            return ((Number)obj).intValue();
         }else{
            return toInteger(obj,null);
         }
      }
      return null;
   }
   
   public static Long toLong(Object obj){
      if(obj != null){
         if( obj instanceof Number){
            return ((Number)obj).longValue();
         }else {
            return toLong(obj,null);
         }
      }
      return null;
   }

   public static boolean isInt(Object srcStr) {
      if (srcStr == null) {
         return false;
      }
      String s = srcStr.toString().replaceAll("(\\s)", "");
      Pattern p = Pattern.compile("([-]?[\\d]+)");
      Matcher m = p.matcher(s);
      return m.matches();
   }
   
   public static boolean isNullOrZero(Long id) {
      return id == null || id.equals(0L);
   }

   public static boolean isNullOrZero(Integer id) {
      return id == null || id.equals(0);
   }
}
