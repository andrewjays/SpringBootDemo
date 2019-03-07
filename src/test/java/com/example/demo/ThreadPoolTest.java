package com.example.demo;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:zhounan
 * @Description: 线程池测试类
 * @Date: Created in 2019/3/6 15:44
 */
@Slf4j
public class ThreadPoolTest {


    public static void threadPool(StorePath storePath, int count, long len, FastFileStorageClient storageClient) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        cachedThreadPool.execute(() -> {
            try {
                checkDownload(storePath, count, len, storageClient);
            } catch (IOException e) {

            }
        });


    }

    private static void checkDownload(StorePath storePath, int count, long len, FastFileStorageClient storageClient) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://103.40.232.189:8888/" + storePath.getFullPath());
        CloseableHttpResponse response = httpclient.execute(httpget);
        InputStream inputStream = response.getEntity().getContent();
        byte[] b = new byte[1024*1024];
        int len2 = 0;
        if (inputStream == null) {
            log.info("第{}次文件,文件地址:{},没有获取到对应文件", count, storePath.getFullPath());
        } else {
            File file1 = new File("D:\\test\\temp.zip");
            FileOutputStream fileOutputStream = new FileOutputStream(file1);

            while ((len2 = inputStream.read(b)) != -1) {
                fileOutputStream.write(b, 0, len2);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            long len1 = file1.length();
            if (len1 != len) {
                log.info("第{}次文件,文件地址:{},文件大小不对,原文件大小:{},新文件大小:{}", count, storePath.getFullPath(), len, len1);
                System.exit(0);
            }
            log.info("第{}次文件,文件地址:{},文件大小一致", count, storePath.getFullPath());
            storageClient.deleteFile(storePath.getFullPath());

        }

    }
}
