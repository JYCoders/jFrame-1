package com.jysoft.framework.util;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils
{

    public AESUtils()
    {
    }

    public static String encrypt(String content, String keyseed)
        throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(keyseed.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte enCodeFormat[] = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte byteContent[] = content.getBytes("utf-8");
        cipher.init(1, key);
        byte result[] = cipher.doFinal(byteContent);
        return byte2hex(result);
    }

    public static byte[] decrypt(byte content[], String keyseed)
        throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(keyseed.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte enCodeFormat[] = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, key);
        byte result[] = cipher.doFinal(hex2byte(content));
        return result;
    }

    public static String encryptForJS(String content, String keyseed)
        throws Exception
    {
        SecretKeySpec key = getKeySpecFromBytes(keyseed.toUpperCase());
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, key);
        byte byteEnc[] = cipher.doFinal(content.getBytes("UTF-8"));
        return byte2hex(byteEnc);
    }

    public static String decryptForJS(String content, String keyseed)
        throws Exception
    {
        SecretKeySpec key = getKeySpecFromBytes(keyseed.toUpperCase());
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(2, key);
        String result = new String(cipher.doFinal(hex2byte(content.getBytes("UTF-8"))));
        return result.trim();
    }

    public static String byte2hex(byte b[])
    {
        String hs = "";
        String stmp = "";
        for(int n = 0; n < b.length; n++)
        {
            stmp = Integer.toHexString(b[n] & 255);
            if(stmp.length() == 1)
                hs = (new StringBuilder()).append(hs).append("0").append(stmp).toString();
            else
                hs = (new StringBuilder()).append(hs).append(stmp).toString();
        }

        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte b[])
    {
        if(b.length % 2 != 0)
            throw new IllegalArgumentException("\u957F\u5EA6\u4E0D\u662F\u5076\u6570!");
        byte b2[] = new byte[b.length / 2];
        for(int n = 0; n < b.length; n += 2)
        {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte)Integer.parseInt(item, 16);
        }

        return b2;
    }

    public static SecretKeySpec getKeySpecFromBytes(String strBytes)
        throws NoSuchAlgorithmException
    {
        SecretKeySpec spec = new SecretKeySpec(hex2byte(strBytes.getBytes()), "AES");
        return spec;
    }
}
