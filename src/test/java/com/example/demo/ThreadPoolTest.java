package com.example.demo;


import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:zhounan
 * @Description: 线程池测试类
 * @Date: Created in 2019/3/6 15:44
 */
@Slf4j
public class ThreadPoolTest {


    private File file = new File("D:\\test\\uploadTest.zip");

    public static void threadPool(CountDownLatch cdl,FastFileStorageClient storageClient) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        cachedThreadPool.execute(() -> {
            try {
                checkDownload(cdl,storageClient);
            } catch (IOException e) {

            }
        });


    }

    private  static  void checkDownload(CountDownLatch cdl, FastFileStorageClient storageClient) throws IOException {
        File file = new File("D:\\test\\uploadTest.zip");
        byte[] b = new byte[1024];
        FileInputStream fileInputStream = new FileInputStream(file);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "zip");
        log.info("线程池------------------第{}次上传文件,文件地址:{}", cdl.getCount(), storePath.getFullPath());
        File file1 = new File("D:\\test\\download.txt");
        FileWriter fileWriter = new FileWriter(file1, true);
        fileWriter.write(storePath.getFullPath() + "\r\n");
        fileWriter.close();
//        byte[] b = new byte[1024];
//        FileInputStream fileInputStream = new FileInputStream(file);
//        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "zip");
//        log.info("线程池------------------第{}次上传文件,文件地址:{}", count, storePath.getFullPath());
        //        CloseableHttpClient httpclient = HttpClients.createDefault();
        //        HttpGet httpget = new HttpGet("http://103.40.232.189:8888/" + storePath.getFullPath());
        //        CloseableHttpResponse response = httpclient.execute(httpget);
        //        InputStream inputStream = response.getEntity().getContent();
        //        byte[] b = new byte[1024 * 1024];
        //        int len2 = 0;
        //        if (inputStream == null) {
        //            log.info("第{}次文件,文件地址:{},没有获取到对应文件", count, storePath.getFullPath());
        //        } else {
        //            File file1 = new File("D:\\test\\temp.zip");
        //            FileOutputStream fileOutputStream = new FileOutputStream(file1);
        //
        //            while ((len2 = inputStream.read(b)) != -1) {
        //                fileOutputStream.write(b, 0, len2);
        //                fileOutputStream.flush();
        //            }
        //            fileOutputStream.close();
        //            long len1 = file1.length();
        //            if (len1 != len) {
        //                log.info("第{}次文件,文件地址:{},文件大小不对,原文件大小:{},新文件大小:{}", count, storePath.getFullPath(), len, len1);
        //
        //            } else {
        //                log.info("线程池--------------第{}次读取文件,文件地址:{},文件大小一致", count, storePath.getFullPath());
        //            }


//        File file1 = new File("D:\\test\\download.txt");
        //        FileWriter fileWriter = new FileWriter(file1, true);
        //        fileWriter.write(storePath.getFullPath() + "\r\n");
        //        fileWriter.close();

        //storageClient.deleteFile(storePath.getFullPath());

        //        }

    }
}
