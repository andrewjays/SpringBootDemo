package com.example.demo.tool.rsa;


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
    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception {
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception {
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }


    public static void main(String[] args) throws Exception {
                String message = "a123456";
//                KeyPair keyPair = getKeyPair();
//                PrivateKey privateKey = keyPair.getPrivate();
//                PublicKey publicKey = keyPair.getPublic();
        //
        //        String privateKeyStr = new BASE64Encoder().encodeBuffer(privateKey.getEncoded());
         //       String publicKeyStr = new BASE64Encoder().encodeBuffer(publicKey.getEncoded());
        //        System.out.println("私钥:"+privateKeyStr);
        //        System.out.println("公钥:"+publicKeyStr);
        //        //将Base64编码后的公钥转换成PublicKey对象
                String publicKeyStr="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0qAahVLAQMDbQcCnhrItNQqOkYPrOI0U\n" +
                        "g8ogxen7wnWTIqIOevW6pOvVeS93GGurxH/7+THggkkPlkz0bLyBpMBq/Gi8dyy4SrRerb1tOn7D\n" +
                        "UIBZWbSySRZZ0FWUNA0yqfBhm9Iw1JBKhbri0NV2hYfZxvU0k7h7oWcquFA0VwzdQfZQWWXzed9J\n" +
                        "c5k+4ebVGCgEisieiRkKucIM9Fsl+5Lf43LehTvtWjYGnghLKb/hAWH2rw6jbuRizoum3HKA1Zzx\n" +
                        "kskxFA/OEeFskRh6vhCjei+ewiYLnl2JmEI2zWtUUw05ohwzgvzpPEhC6BDSvqnFjJ1/9IUOzfCp\n" +
                        "BFjkXwIDAQAB";
        //        PublicKey publicKey = string2PublicKey(publicKeyStr);
        //        //用公钥加密
          //      byte[] publicEncrypt = publicEncrypt(message.getBytes(), publicKey);
        //        //加密后的内容Base64编码
        //        String byte2Base64 = byte2Base64(publicEncrypt);
        //        System.out.println("公钥加密并Base64编码的结果：" + byte2Base64);


        String byte2Base64 = "D4pUQMz0wpqTT/48aurWybYL4bOeuvpkbO3AnOvZP0cGSJkTSG4RrzQSjhVCHYGhDsiuazxOMq71kdjhnYnzWOH9GrpMTiBjlnQP+m776eS06K8dyiPWGD0JZM96o+b05lC27ytUtFz6ei59RLCmJk2/jupHu7S3xH95Lg2MDYgb3Kuk3FxwvcaLkDpw0FHhJMHFJ8RW6x7r6R2Pv0prtaFSuqIfYoqft1+JZP1cVsKfFDYpoDzK/PwJRC2oLirgQNXCGAF2o2/Nh62i9hwcfs+gD+cId9rQ0ncGAmI72ZO7q+Kqo3vM71vf+CAw8cgtDBXM+lFSru+YE9pVCUG9Pg==";
        String s="Suq5QdJ8b2SXbtR5vNJUiOArnGuv/nOi9XRWe05j772K7YduvhezmWZWpAd3zcBIIqumkbTFYuWd\n" +
                "XxiBvn/fUkyo6GHmEPFJ65zSXVR9O36Zxok0suRIGVrGwhoD6+svkLfio1AMFkxMbS8emmXv5U5o\n" +
                "hmNa5R7T3DzBquTC4AQ=";
        int length = s.length();
        System.out.println(length);
        byte[] base642Byte = MyRSAUtil.base642Byte(byte2Base64);
        //  用私钥解密
        String privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDSoBqFUsBAwNtBwKeGsi01Co6R\n" +
                "g+s4jRSDyiDF6fvCdZMiog569bqk69V5L3cYa6vEf/v5MeCCSQ+WTPRsvIGkwGr8aLx3LLhKtF6t\n" +
                "vW06fsNQgFlZtLJJFlnQVZQ0DTKp8GGb0jDUkEqFuuLQ1XaFh9nG9TSTuHuhZyq4UDRXDN1B9lBZ\n" +
                "ZfN530lzmT7h5tUYKASKyJ6JGQq5wgz0WyX7kt/jct6FO+1aNgaeCEspv+EBYfavDqNu5GLOi6bc\n" +
                "coDVnPGSyTEUD84R4WyRGHq+EKN6L57CJgueXYmYQjbNa1RTDTmiHDOC/Ok8SELoENK+qcWMnX/0\n" +
                "hQ7N8KkEWORfAgMBAAECggEAHo6jylvrEHnqVWvB4kTPUjap4GqnIZk2NLiKcKw1nky4f/nK6APm\n" +
                "hC7C1cYRHTXhwaxfvhOCYuR+omufunbOyDwySVRYo2GViyEH62TkB+yEIZW9YMpaY0ge157S1Ypk\n" +
                "o0sAnj6sXupgJW6roWiWvWCsM7U3X4cQSKZTCFJ6hXZQ7H0wh4f9Q+z5t7HDxN/mgsSwM5uvLu1y\n" +
                "LbFi27jPRBPpG76DD/qDctfdfzj6uqbFem5Ws5L3KQK2Jx2ZoPO+zKnwoJ9z4MIg257rE/dZEz8h\n" +
                "qnXmbIUTJKQTvpNQjgSXzLfXOUEsutU2UHFwh5epoN0gYKSkuGoJNwsMaEp3wQKBgQDuhkll+LhG\n" +
                "F6Yn4lPIway7MfJm6EHFFt0d+LsneKxSumNHjxT0OmwnU+Mt/DPN6C5Isfougl5iiJQiEZEIktrm\n" +
                "6Q3Oj7E6VsM0I1DH6bWsiGUikFK9VqWdi/ndZMm/cM9f00E7a2so4Jdn5lvv9natmhJJ8BvE2SiM\n" +
                "vcQk8pbwaQKBgQDiDovuqou7EE/PsseTdOMY260wa5p7rY8lbT+/HM8pf1msXu56pjSYUup6GODS\n" +
                "dK9sjVZN4ov+PBlZWh4/HugPFt1mJamqssxlb3yJ3eyGMm7zyQbAZiFXTRsuVmPrUUUZ1ducZMai\n" +
                "tHAHYETTgiDzesjGhaWqYpVv1UFsm9uVhwKBgEsTkX67LqVBRFdBccW60DeLnRSu/iSe8bJBXAcs\n" +
                "gHD5oc4gRyQoT2qBiPwsadHhxs+y1WBWQIcbHiUrCk98idIsgOAHcm0+aeq7Qu3prflEZTDf5Gsc\n" +
                "HrzcFiWNV8MeKueqgtnFEdGn/4AAjeeV1/0EhcUoAlWPVKgYAujuQnwhAoGAUAqAh5hBc5HaAJu3\n" +
                "yMFZa8LUttFS2LnidokRSjUEMRhTgxH19N2Gv+yT83l9mWcReiktRtSRZwqWmymqjgT+e+FHsVRi\n" +
                "l/tJHh10hkha6EVRPeaPCm7fHU9C8YxLQtX61qP/Z4fTQWcMR8BRfmaUDkIKdyh5OTRA32EuEiu5\n" +
                "e18CgYEAjS+VBPxzVlOdRYtT/bkKU0Fc+ilN22ectztWcZc+mElV7GPo78UeBLHG7D9XGbxakwzA\n" +
                "8XFrPozZ+I3qvdsH9NmYI3rxNamZDfYoQRoFHxAJJhCbVE76I9YDTHJk6WjtX7zS2KRHvUDsWK2s\n" +
                "ut365mEY0IP6DRiPOeKOEazHzoQ=";

    //    PrivateKey privateKey = string2PrivateKey(privateKeyStr);

      //  byte[] privateDecrypt = MyRSAUtil.privateDecrypt(base642Byte, privateKey);
        //解密后的明文
     //   System.out.println("解密后的明文: " + new String(privateDecrypt));


    }

}
