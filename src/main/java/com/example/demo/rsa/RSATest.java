package com.example.demo.rsa;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Jay
 * @description
 * @date Created in 2019/6/12 20:01
 */
public class RSATest {
    public RSATest() {
    }

    public PublicKey getPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        DataInputStream dis = new DataInputStream(resourceAsStream);

        PublicKey var7;
        try {
            byte[] keyBytes = new byte[resourceAsStream.available()];
            dis.readFully(keyBytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            var7 = kf.generatePublic(spec);
        } finally {
            try {
                if (null != dis) {
                    dis.close();
                }
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }

        return var7;
    }

    public PrivateKey getPrivateKey(String filename) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        DataInputStream dis = new DataInputStream(resourceAsStream);

        try {
            byte[] keyBytes = new byte[resourceAsStream.available()];
            dis.readFully(keyBytes);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey var7 = kf.generatePrivate(spec);
            return var7;
        } catch (IOException var21) {
            var21.printStackTrace();
        } catch (NoSuchAlgorithmException var22) {
            var22.printStackTrace();
        } catch (InvalidKeySpecException var23) {
            var23.printStackTrace();
        } finally {
            try {
                if (null != dis) {
                    dis.close();
                }
            } catch (IOException var20) {
                var20.printStackTrace();
            }

        }

        return null;
    }

    public void generateKey(String publicKeyFilename, String privateKeyFilename, String password) {
        KeyPairGenerator keyPairGenerator = null;
        FileOutputStream fos = null;

        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom(password.getBytes(Charset.forName("UTF-8")));
            keyPairGenerator.initialize(1024, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            fos = new FileOutputStream(publicKeyFilename);
            fos.write(publicKeyBytes);
            fos.close();
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            fos = new FileOutputStream(privateKeyFilename);
            fos.write(privateKeyBytes);
        } catch (NoSuchAlgorithmException var22) {
            var22.printStackTrace();
        } catch (FileNotFoundException var23) {
            var23.printStackTrace();
        } catch (IOException var24) {
            var24.printStackTrace();
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
            } catch (IOException var21) {
                var21.printStackTrace();
            }

        }

    }

//    public String decryptToStr(Key privateKey, String endata) throws Exception {
//        RandUtil rand = new RandUtil();
//        String data = new String(this.privateEncrypt(privateKey, rand.parseHexStr2Byte(endata)));
//        return data;
//    }
//
//    private byte[] privateEncrypt(Key privateKey, byte[] encoData) throws Exception {
//        Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
//        cipher.init(2, privateKey);
//        byte[] data = cipher.doFinal(encoData);
//        return data;
//    }
//
//    public String encryptToStr(Key publicKey, String content) throws Exception {
//        RandUtil rand = new RandUtil();
//        String endata = rand.parseByte2HexStr(publicEncrypt(publicKey, content));
//        return endata;
//    }

    private static byte[] publicEncrypt(Key publicKey, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
        cipher.init(1, publicKey);
        byte[] result = cipher.doFinal(data.getBytes());
        return result;
    }

    public static String decrypt(String str, String privateKey) throws Exception {
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static void main(String[] args) throws  Exception{


        String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALs/KMwS6eI3oE+f\n" +
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
        String str="n64+M5JF92Hk+MeEOZlkKpH3xaW6A1khqT1S2S8Gx3FI9cqh9jIrTSoVoKdlACjAceAsz6bXxkhtWm96pviyQDzswd2gFiaxsqpCABL0v+pXlOzLEhV427yTf9ZdaiqYaOd8cKocTBMxhWOpVloF0BH1uqTPyhaX4XE775XjfsM=";
        String decrypt = decrypt(str,privateKey);
        System.out.println(decrypt);

        byte[] bytes = new BASE64Decoder().decodeBuffer(decrypt);
        String s = new String(bytes);
        System.out.println(s);
    }

}
