package com.happyldc.util;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 */

public class EncryptionUtils {

    //初始化向量，随意填写
    private static byte[]iv={1,2,3,4,5,6,7,8};
    private final static String HEX="0123456789ABCDEF";

    private EncryptionUtils(){}

    /**
     *  DES加密
     * @param encryptString 明文
     * @param encryptKey 密钥
     * @return 加密后的密文
     */
    public static String encryptDES(String encryptString, String encryptKey){

        try {
            //实例化IvParameterSpec对象，使用指定的初始化向量
            IvParameterSpec zeroIv=new IvParameterSpec(iv);
            //实例化SecretKeySpec，根据传入的密钥获得字节数组来构造SecretKeySpec
            SecretKeySpec key =new SecretKeySpec(encryptKey.getBytes(),"DES");
            //创建密码器
            Cipher cipher= Cipher.getInstance("DES/CBC/PKCS5Padding");
            //用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE,key,zeroIv);
            //执行加密操作
            byte[]encryptedData=cipher.doFinal(encryptString.getBytes());
            return Base64.encodeToString(encryptedData,0);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  DES解密
     * @param decryptString 密文
     * @param decryptKey 密钥
     * @return  返回明文
     */

    public static String decryptDES(String decryptString, String decryptKey){

        try {
            //先使用Base64解密
            byte[]byteMi= Base64.decode(decryptString,0);
            //实例化IvParameterSpec对象使用指定的初始化向量
            IvParameterSpec zeroIv=new IvParameterSpec(iv);
            //实例化SecretKeySpec，根据传入的密钥获得字节数组来构造SecretKeySpec,
            SecretKeySpec key=new SecretKeySpec(decryptKey.getBytes(),"DES");
            //创建密码器
            Cipher cipher= Cipher.getInstance("DES/CBC/PKCS5Padding");
            //用密钥初始化Cipher对象,上面是加密，这是解密模式
            cipher.init(Cipher.DECRYPT_MODE,key,zeroIv);
            //获取解密后的数据
            byte [] decryptedData=cipher.doFinal(byteMi);
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * AES 加密
     * @param seed      密钥
     * @param cleartext 明文
     * @return 密文
     */
    public static String encryptAES(String seed, String cleartext) {
        //对密钥进行加密
        byte[] rawkey = getRawKey(seed.getBytes());
        //加密数据
        byte[] result = encrypt(rawkey, cleartext.getBytes());
        //将十进制数转换为十六进制数
        return toHex(result);
    }

    /**
     * AES 解密
     * @param seed      密钥
     * @param encrypted 密文
     * @return 明文
     */
    public static String decryptAES(String seed, String encrypted) {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    /** 获取密钥
     * @param seed 密文的字节数组
     * @return 密钥
     */
    private static byte[] getRawKey(byte[] seed) {

        try {
            //获取密钥生成器
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(seed);
            //生成位的AES密码生成器
            kgen.init(128, sr);
            //生成密钥
            SecretKey skey = kgen.generateKey();
            //编码格式
            byte[] raw = skey.getEncoded();
            return raw;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

    }


    /** 加密
     * @param raw 密钥
     * @param clear
     * @return
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) {

        try {
            //生成一系列扩展密钥，并放入一个数组中
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            //使用ENCRYPT_MODE模式，用skeySpec密码组，生成AES加密方法
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            //得到加密数据
            byte[] encrypted = cipher.doFinal(clear);
            return encrypted;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 解密
     * @param raw
     * @param encrypted
     * @return
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted) {

        try {
            //生成一系列扩展密钥，并放入一个数组中
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES");
            //使用DECRYPT_MODE模式，用skeySpec密码组，生成AES解密方法
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //得到加密数据
            byte[] decrypted = cipher.doFinal(encrypted);
            return decrypted;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }



    //将十六进制字符串转为十进制字节数组
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }
    //将十进制字节数组转换为十六进制
    public static String toHex(byte[]buf){
        if(buf==null){
            return "";
        }
        StringBuffer result=new StringBuffer(2*buf.length);
        for(int i=0;i<buf.length;i++){
            appendHex(result,buf[i]);
        }
        return result.toString();
    }
    private static void appendHex(StringBuffer sb, byte b){
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }



    /** 将String加密成MD5格式
     * @param s 加密的字符串
     * @return 加密后的MD5值
     */
    public static String encodeMD5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    
    /** 将16进制转换成String
     * @param keyData
     * @return
     */
    private static String toHexString(byte[] keyData) {
        if (keyData == null) {
            return null;
        }
        int expectedStringLen = keyData.length * 2;
        StringBuilder sb = new StringBuilder(expectedStringLen);
        for (int i = 0; i < keyData.length; i++) {
            String hexStr = Integer.toString(keyData[i] & 0x00FF, 16);
            if (hexStr.length() == 1) {
                hexStr = "0" + hexStr;
            }
            sb.append(hexStr);
        }
        return sb.toString();
    }

    /** 将文件进行MD5加密
     * @param f 加密的File对象
     * @return 加密后的MD5值
     */
    public static String encodeMD5(File f) {
        if (!f.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(f);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


}
