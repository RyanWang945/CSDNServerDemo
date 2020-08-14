package com.ryan.article.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ryan
 * @date 2020/2/13 19:31
 * 我明白了运行的原理了，在注册时会存入密码，这个密码已经是MD5加密的了
 * 然后每次login比对密码的时候，都需要先将前台传来的密码进行MD5，而后同数据库中的明文加密密码
 * 进行比对
 */
public class MD5 {
    public static String next(String key){
        char hexDigits[]={
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try{
            byte[] btInput=key.getBytes();
            //获得MD5摘要算法的MessageDigest对象
            MessageDigest mdInst=MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md=mdInst.digest();
            int j=md.length;
            char[] str=new char[j*2];
            int k=0;
            for(int i=0;i<j;i++){
                byte byte0=md[i];
                str[k++]=hexDigits[byte0>>>4&0xf];
                str[k++]=hexDigits[byte0&0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
