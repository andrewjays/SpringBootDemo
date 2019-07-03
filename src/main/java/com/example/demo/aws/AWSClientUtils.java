package com.example.demo.aws;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;


/**
 * @Author: jay
 * @Date: 2019-07-01 11:28
 */
@Slf4j
@Component
public class AWSClientUtils {

    // @Autowired
    private AwsS3Config s3ConfigWithApollo;

    private static AwsS3Config s3Config;
    private static AmazonS3 s3Client;
    private static TransferManager xferMgr;

    @PostConstruct
    public void init() {
        s3Config = s3ConfigWithApollo;
    }

    /**
     * 获取 AmazonS3 client 实例
     */
    private static AmazonS3 getS3Client() {

        if (null != s3Client) {
            return s3Client;
        }

        try {
            AWSCredentials credentials = new BasicAWSCredentials(s3Config.getAccessKey(), s3Config.getSecretKey());
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setProtocol(Protocol.HTTP);
            clientConfig.setSignerOverride("S3SignerType");
//            if (StringUtils.isEmpty(s3Config.getEndPoint())) {
//                s3Client =
//                        AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
//                                .withClientConfiguration(clientConfig).build();
//            } else {
//                s3Client =
//                        AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
//                                .withClientConfiguration(clientConfig)
//                                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Config.getEndPoint(),
//                                        s3Config.getRegion())).build();
//            }
        } catch (Exception e) {
            log.error("获取S3客户端失败,请检查配置");
            log.error(e.getMessage());
        }
        return s3Client;
    }

    /**
     * 获取 TransferManager 实例
     */
    private static TransferManager getTransferManager() {
        s3Client = getS3Client();
        try {
            xferMgr = TransferManagerBuilder.standard().withS3Client(s3Client).build();
        } catch (Exception e) {
            log.error("获取TransferManager失败,请检查配置");
            log.error(e.getMessage());
        }
        return xferMgr;
    }

    /**
     * 获取 Bucket 下文件列表
     */
    private static List<S3ObjectSummary> listObjects() {
        s3Client = getS3Client();
        try {
            log.debug("Listing objects of:[{}]", s3Config.getBucketName());
            ObjectListing objectListing = s3Client.listObjects(s3Config.getBucketName());
            if (null == objectListing) {
                return null;
            }
            return objectListing.getObjectSummaries();
        } catch (Exception e) {
            log.error("List objects error,bucketName:[{}]", s3Config.getBucketName());
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 判断存储通中对象是否存在
     */
    private static boolean isObjectExit(String bucketName, String key) {
        s3Client = getS3Client();
        int len = key.length();
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        String s;
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            s = objectSummary.getKey();
            int slen = s.length();
            if (len == slen) {
                int i;
                for (i = 0; i < len; i++)
                    if (s.charAt(i) != key.charAt(i)) {
                        break;
                    }
                if (i == len) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 上传本地文件
     *
     * @param localFilePath 本地文件地址
     * @param remoteKey     S3 文件 Key
     */
    public static void uploadFile(String localFilePath, String remoteKey) {
        File file = new File(localFilePath);
        uploadFile(file, remoteKey);
    }

    /**
     * @param file      文件对象
     * @param remoteKey S3 文件 Key
     */
    public static boolean uploadFile(File file, String remoteKey) {
        s3Client = getS3Client();
        boolean isSuccess = true;
        try {
            s3Client.putObject(new PutObjectRequest(s3Config.getBucketName(), remoteKey, file));
        } catch (Exception e) {
            isSuccess = false;
            log.error("Upload file error,remoteKey:[{}]", remoteKey);
            log.error(e.getMessage());
        }
        return isSuccess;
    }

    /**
     * 根据文件流上传文件
     *
     * @param inputStream 文件流
     * @param remoteKey   S3 文件 Key
     */
    public static boolean uploadFile(InputStream inputStream, String remoteKey) {
        s3Client = getS3Client();
        boolean isSuccess = true;
        try {
            s3Client.putObject(s3Config.getBucketName(), remoteKey, inputStream, new ObjectMetadata());
        } catch (Exception e) {
            isSuccess = false;
            log.error("Upload file error,remoteKey:[{}]", remoteKey);
            log.error(e.getMessage());
        }
        return isSuccess;
    }

    /**
     * @param remoteKey     S3 文件 Key
     * @param localFilePath 下载到本地文件地址
     */
    public static void downloadFile(String remoteKey, String localFilePath) {
        s3Client = getS3Client();
        try {
            s3Client.getObject(new GetObjectRequest(s3Config.getBucketName(), remoteKey), new File(localFilePath));
        } catch (Exception e) {
            log.error("Upload file error,remoteKey:[{}]", remoteKey);
            log.error(e.getMessage());
        }
    }

    public static S3ObjectInputStream downloadFile(String remoteKey) {
        s3Client = getS3Client();
        try {
            GetObjectRequest request = new GetObjectRequest(s3Config.getBucketName(), remoteKey);
            S3Object object = s3Client.getObject(request);
            return object.getObjectContent();
        } catch (Exception e) {
            log.error("Download file error,remoteKey:[{}]", remoteKey);
            log.error(e.getMessage());
        }
        return null;
    }


    public static String getUrl(String remoteKey) {
        s3Client = getS3Client();
        try {
            // 10 minutes
            Date expiration = new Date(System.currentTimeMillis() + 60 * 10 * 1000);
            URL url = s3Client.generatePresignedUrl(s3Config.getBucketName(), remoteKey, expiration);
            log.debug("获取对象临时url:[{}]", url.toString());
            return url.toString();
        } catch (Exception e) {
            log.error("Get url error,remoteKey:[{}]", remoteKey);
            log.error(e.getMessage());
        }
        return "";
    }

    /**
     * @param remoteKey S3 文件 Key
     */
    public static void removeFile(String remoteKey) {
        s3Client = getS3Client();
        try {
            s3Client.deleteObject(s3Config.getBucketName(), remoteKey);
        } catch (Exception e) {
            log.error("Delete file error,remoteKey:[{}]", remoteKey);
            log.error(e.getMessage());
        }
    }


    public static void main(String[] args) {

        boolean test = uploadFile(new File("D:\\test\\file.log"), "test");
        System.out.println(test);
        S3ObjectInputStream test1 = downloadFile("test");

    }
}
