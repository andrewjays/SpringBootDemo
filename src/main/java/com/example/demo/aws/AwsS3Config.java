package com.example.demo.aws;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Jay
 * @description aws配置
 * @date Created in 2019/7/3 10:44
 */
@Data
//@Component
//@ConfigurationProperties(prefix="aws.s3")
public class AwsS3Config {

    @Value("${aws.s3.endPoint}")
    private String endPoint;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

}
