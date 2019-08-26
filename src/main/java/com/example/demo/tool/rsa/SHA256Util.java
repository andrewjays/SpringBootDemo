package com.example.demo.tool.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * sha256 工具类
 * @author  shichangcheng on 2017/3/30.
 */
public class SHA256Util {

    public static String getSHA256(File file) {
        FileInputStream in = null;
        byte[] buffer = new byte[1024];
        int len;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                messageDigest.update(buffer, 0, len);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
        	if(in != null){
        		try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }

        BigInteger bigInt = new BigInteger(1, messageDigest.digest());
        return bigInt.toString(16);
    }

    /**
     * TODO 客户端的加密算法
     *
     * @author sheng
     *
     * @param deltaFilePath
     * @return
     */
    public static String getClientSha256(String deltaFilePath) {
        int BUFF_SIZE = 1024 * 1024;
        FileInputStream fis = null;
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            fis = new FileInputStream(deltaFilePath);
            byte[] buffer = new byte[BUFF_SIZE];
            int length = -1;
            if (fis == null || md == null) {
                return null;
            }
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] bytes = md.digest();
            if (bytes == null) {
                return null;
            }
            for (int i = 0; i < bytes.length; i++) {
                String md5s = Integer.toHexString(bytes[i] & 0xff);
                if (md5s == null || buf == null) {
                    return null;
                }
                if (md5s.length() == 1) {
                    buf.append("0");
                }
                buf.append(md5s);
            }
            return buf.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



    /**
     * 分段校验
     * @param file
     * @return
     */
    public static String getSegmentSHA256(File file,int segSize) {
    	JSONArray jsonArray = new JSONArray();
    	FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            in = new FileInputStream(file);
            long redLen = 0;
            while ((len = in.read(buffer, 0, 1024)) != -1) {
            	messageDigest.update(buffer, 0, len);
            	redLen += len;
            	if(redLen % segSize == 0){
            		int number = (int) (redLen / segSize - 1);
            		int startPos = number * segSize;
            		long endPos = redLen;

            		JSONObject jsonObject = digest(number,startPos,endPos,messageDigest);
        			jsonArray.add(jsonObject);
            	}
            }

            if(redLen % segSize != 0){
            	int number = (int) (redLen / segSize);
        		long startPos = number * segSize;
        		long endPos = redLen;

        		JSONObject jsonObject = digest(number,startPos,endPos,messageDigest);
    			jsonArray.add(jsonObject);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
        	if(in != null){
        		try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return jsonArray.toString();
    }

    private static JSONObject digest(int number,long startPos,long endPos,MessageDigest messageDigest){
    	JSONObject jsonObject = new JSONObject();
    	//digest之后会自动重置
		BigInteger bigInt = new BigInteger(1, messageDigest.digest());
        String sha256 = bigInt.toString(16);
		jsonObject.put("num", number);
		jsonObject.put("sha", sha256);
		jsonObject.put("startpos", startPos);
		jsonObject.put("endpos", endPos);
		return jsonObject;
    }

    public static void main(String[] args) {
        String sha = getClientSha256("D://test//test.zip");
        System.out.println(sha);
    }
}
