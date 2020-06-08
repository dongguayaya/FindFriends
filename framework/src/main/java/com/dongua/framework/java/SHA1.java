package com.dongua.framework.java;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 哈希计算
 */
public class SHA1 {
    public static String sha1(String data){
        StringBuffer buf=new StringBuffer();
        try {
            MessageDigest md=MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits=md.digest();
            for(int i=0;i<bits.length;i++){
                int a=bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
