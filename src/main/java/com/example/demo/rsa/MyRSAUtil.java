package com.example.demo.rsa;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Jay
 * @description
 * @date Created in 2019/6/12 19:29
 */
public class MyRSAUtil {

    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }


    public static void main(String[] args)throws Exception {
        String message = "a123456";



        //将Base64编码后的公钥转换成PublicKey对象
        String publicKeyStr="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7PyjMEuniN6BPn8oqzIZ6AO1N\n" +
                "jSTO9R3adCCIwKfKIEoWXXM+tHDpktdPKSaAsWJPTNAGvEvtxOfzXib/EMXKqD0e\n" +
                "Uy5MatfpRjRdf1hJVimmfrb09Qx2j7CsKLy7nD23m4xubdYBwvkjMwt/L3JxB5D6\n" +
                "qryW1wei/j1c+/OCxQIDAQAB";
        PublicKey publicKey = string2PublicKey(publicKeyStr);
        //用公钥加密
        byte[] publicEncrypt = publicEncrypt(message.getBytes(), publicKey);
        //加密后的内容Base64编码
        String byte2Base64 = byte2Base64(publicEncrypt);
        System.out.println("公钥加密并Base64编码的结果：" + byte2Base64);


       // String byte2Base64="n64+M5JF92Hk" +
        //        "+MeEOZlkKpH3xaW6A1khqT1S2S8Gx3FI9cqh9jIrTSoVoKdlACjAceAsz6bXxkhtWm96pviyQDzswd2gFiaxsqpCABL0v" +
        //        "+pXlOzLEhV427yTf9ZdaiqYaOd8cKocTBMxhWOpVloF0BH1uqTPyhaX4XE775XjfsM=";
        byte[] base642Byte = MyRSAUtil.base642Byte(byte2Base64);
        //用私钥解密
        String privateKeyStr="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALs/KMwS6eI3oE+f\n" +
                "yirMhnoA7U2NJM71Hdp0IIjAp8ogShZdcz60cOmS108pJoCxYk9M0Aa8S+3E5/Ne\n" +
                "Jv8QxcqoPR5TLkxq1+lGNF1/WElWKaZ+tvT1DHaPsKwovLucPbebjG5t1gHC+SMz\n" +
                "C38vcnEHkPqqvJbXB6L+PVz784LFAgMBAAECgYBPu8ZgmBE1/h/qqA1LikodO7XR\n" +
                "FzA8XI44zshn0znfzT4fJe4EHowSOjArCkfV9zL4t6nBpH7kobtvm4EZjgfa2Jwy\n" +
                "13KwF32AvCdjgSHiPssJYTnge8Qul5ZeRakVB1v81CFyowAbBixOdE90GqStSYkF\n" +
                "NioQaQiWoJDm9Qk4IQJBAOF7QoaZurVFanpMO+CkDZ1SaW3Z90/yg1bw7sy9sNpB\n" +
                "F26hmdVFM9gIMp/MWqk1cvWEBiC2HU3S/MOe/dWgtykCQQDUlxj6su/mOzWDku6/\n" +
                "wtojn/e46DQJbt+aH8t8a7PyT87EBI35wA5SM8evG1znGfXNq3j9udXkYR2U6n8O\n" +
                "Gq49AkEAhXYWx2Lgz4C56I+M9YdFA7SO8NWN/AU4VlKflhJFRro2a2Y9jq26ZQld\n" +
                "JZaopvUbCVVO5zfEGpdiw0stRbAuGQJBAMlQkKIPQuiGF0YSpS5IqePkf2TF10k7\n" +
                "illcVIjtQQlslAPBwGTKf4VObYEf61kZl1B9WeUDz04mvSmg6lKD540CQApjzlJH\n" +
                "0oBJpOIMGg+knw3zTlcSF3Qqr6CHNJOHEvd9W0pRAuIKHth3ygx38blLfFYkdRdK\n" +
                "KgBNPy49EfZs5v8=";

        PrivateKey privateKey= string2PrivateKey(privateKeyStr);

        byte[] privateDecrypt = MyRSAUtil.privateDecrypt(base642Byte, privateKey);
        //解密后的明文
        System.out.println("解密后的明文: " + new String(privateDecrypt));


    }

}
