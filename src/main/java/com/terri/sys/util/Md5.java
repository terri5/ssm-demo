package com.terri.sys.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * @author 吴喜军
 *
 */
public class Md5
{
   /**
    * 对字符串进行MD5加密
    * @param String s
    * @return 加密码失败则返回空字符串
    * @throws Exception
    */
   public static String md5(String s)
   {
      MessageDigest messagedigest;
      String en = "";
      try
      {
         messagedigest = MessageDigest.getInstance("MD5");
         messagedigest.update(s.getBytes());
         byte by[] = messagedigest.digest();
         en = bytehex(by);
      }
      catch (NoSuchAlgorithmException e)
      {
         e.printStackTrace();
      }
      return en;
   }

   public static String toHexString(String s)
   {
      String str = "";
      for (int i = 0; i < str.length(); i++)
      {
         char c = str.charAt(i);
         String sl = "0000" + Integer.toHexString(c);
         str = str + sl.substring(sl.length() - 4) + " ";
      }

      return str;
   }

   public static String bytehex(byte by[])
   {
      String s = "";
      for (int i = 0; i < by.length; i++)
      {
         String t = Integer.toHexString(by[i] & 0xff);
         if (t.length() == 1)
            s = s + "0" + t;
         else
            s = s + t;
         if (i < by.length - 1)
            s = s;
      }

      return s.toUpperCase();
   }
   
}
