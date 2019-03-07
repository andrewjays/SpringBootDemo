package com.example.demo;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;


/**
 * @Author:zhounan
 * @Description: 文件上传测试类
 * @Date: Created in 2019/3/6 10:57
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUploadTest {

    @Autowired
    protected FastFileStorageClient storageClient;

    @Test
    public void uploadTest() throws Exception {
        File file = new File("D:\\test\\uploadTest.zip");
        byte[] b = new byte[1024];
        int count = 1;
        long len = file.length();
        while (count < 1000) {
            FileInputStream fileInputStream = new FileInputStream(file);

            StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "zip");
            //log.info("第{}次文件,文件地址:{},文件大小:{}", count, storePath.getFullPath(), len);
            ThreadPoolTest.threadPool(storePath, count, len, storageClient);
            //            CloseableHttpClient httpclient = HttpClients.createDefault();
            //            HttpGet httpget = new HttpGet("http://103.40.232.189:8888/" + storePath.getFullPath());
            //            CloseableHttpResponse response = httpclient.execute(httpget);
            //            InputStream inputStream = response.getEntity().getContent();
            //            if (inputStream == null) {
            //                log.info("第{}次文件,文件地址:{},没有获取到对应文件", count, storePath.getFullPath());
            //            } else {
            //                File file1 = new File("D:\\test\\temp.txt");
            //                FileOutputStream fileOutputStream = new FileOutputStream(file1);
            //
            //                while ((len2 = inputStream.read(b)) != -1) {
            //                    fileOutputStream.write(b, 0, len2);
            //                    fileOutputStream.flush();
            //                }
            //                fileOutputStream.close();
            //                long len1 = file1.length();
            //                if (len1 != len) {
            //                    log.info("第{}次文件,文件地址:{},文件大小不对,原文件大小:{},新文件大小:{}", count, storePath.getFullPath(), len, len1);
            //                    break;
            //                }
            //                log.info("第{}次文件,文件地址:{},文件大小一致", count, storePath.getFullPath());
            //                storageClient.deleteFile(storePath.getFullPath());
            //            }
            count++;
        }
    }
}
