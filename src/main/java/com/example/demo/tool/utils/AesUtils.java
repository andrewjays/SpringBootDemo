package com.example.demo.tool.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author Jay
 * @description
 * @date Created in 2019/6/11 10:42
 */
@Slf4j
public class AesUtils {

    /// public static final String IV = "1234567890123456";


    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 获得密钥
     */
    private static SecretKey generateKey(String secretKey) throws Exception {
        // 防止 linux 下随机生成 key
        Provider p = Security.getProvider("SUN");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", p);
        secureRandom.setSeed(secretKey.getBytes());
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(secureRandom);
        // 生成密钥
        return kg.generateKey();
    }

    /**
     * 加密
     *
     * @param content 需要加密的原内容
     * @param pkey    密匙 32 个字符
     */
    private static byte[] aesEncrypt(String content, String pkey) {
        try {
            SecretKeySpec skey = new SecretKeySpec(pkey.getBytes(), "AES");
            // "算法/加密模式/填充"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            // ECB mode does not use an IV
            // IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            // 初始化加密器
            /// cipher.init(Cipher.ENCRYPT_MODE, skey, iv);
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            // 加密
            return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.info("aesEncrypt() method error:", e);
        }
        return null;
    }

    /**
     * @param content 加密前原内容
     * @param pkey    长度为 32 个字符
     * @return base64EncodeStr   aes加密完成后内容
     * @Title: aesEncryptStr
     * @Description: aes对称加密
     */
    public static String aesEncryptStr(String content, String pkey) {
        byte[] aesEncrypt = aesEncrypt(content, pkey);
        /// System.out.println("加密后的byte数组:" + Arrays.toString(aesEncrypt));
        String base64EncodeStr = Base64.encodeBase64String(aesEncrypt);
        /// System.out.println("加密后 base64EncodeStr:" + base64EncodeStr);
        return base64EncodeStr;
    }

    /**
     * 解密
     *
     * @param content 解密前的byte数组
     * @param pkey    密匙 32 个字符
     * @return result  解密后的byte数组
     */
    private static byte[] aesDecode(byte[] content, String pkey) throws Exception {

        SecretKeySpec skey = new SecretKeySpec(pkey.getBytes(), "AES");
        // ECB mode does not use an IV
        /// IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
        // 创建密码器
        // 初始化解密器
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
        /// cipher.init(Cipher.DECRYPT_MODE, skey, iv);
        cipher.init(Cipher.DECRYPT_MODE, skey);
        // 解密
        return cipher.doFinal(content);
    }

    /**
     * @param content base64处理过的字符串
     * @param pkey    密匙 32 个字符
     * @return String    返回类型
     * @Title: aesDecodeStr
     * @Description: 解密 失败将返回NULL
     */
    public static String aesDecodeStr(String content, String pkey) {
        try {
            log.info("待解密内容:{}", content);
            byte[] base64DecodeStr = Base64.decodeBase64(content);
            /// System.out.println("base64DecodeStr:" + Arrays.toString(base64DecodeStr));
            byte[] aesDecode = aesDecode(base64DecodeStr, pkey);
            /// System.out.println("aesDecode:" + Arrays.toString(aesDecode));
            if (aesDecode == null) {
                return null;
            }
            String result;
            result = new String(aesDecode, StandardCharsets.UTF_8);
            /// System.out.println("aesDecode result:" + result);
            return result;
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());

        }
        return null;

    }

    public static void main(String[] args) {
        //        // 明文
        //        String content = "{\n" +
        //                "    \"vin\": \"39STATSTESTVIN001\",\n" +
        //                "    \"timestamp\": 1558323657,\n" +
        //                "    \"sdkVersion\": \"V0.2.1_8e21cdc_1558323578\",\n" +
        //                "    \"model\": \"U5\",\n" +
        //                "    \"sign\": \"c829c8aba36fac53d7178d9f7e827884\"\n" +
        //                "}";
        //        String pkey = "f70e300942ea540ed365fe0eb05b5585";
        //        System.out.println("待加密报文:" + content);
        //        String aesEncryptStr = aesEncryptStr(content, pkey);
        //        System.out.println("加密后报文:" + aesEncryptStr);
        //        String aesDecodeStr = aesDecodeStr(aesEncryptStr, pkey);
        //        System.out.println("解密后报文:" + aesDecodeStr);
        //        System.out.println("加解密前后内容是否相等:" + aesDecodeStr.equals(content));
        //        String s = "1TNvYuBuw3zRxrs11pAFK+RvFI8jO3jtP12O41MUPtMgJGttqWIH3DopuJuTUNPgB3zZrytHWMrTjyCWlPab8g/7v3AXhyzh9rTLHDPNwTq1yIdWg63wqF/nO7nXVekW";
        //        System.out.println(aesDecodeStr(s, "f70e300942ea540ed365fe0eb05b5585"));

        String path = "123.12"+ File.separator+"asd"+File.separator+"wer"+File.separator+"12eee";
        int i = path.indexOf(File.separator);
        String ip = path.substring(0, i);
        String iconPath1 = path.substring(i);

        System.out.println(ip+"\n"+iconPath1);

    }
}
