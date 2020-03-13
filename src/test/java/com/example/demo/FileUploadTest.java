package com.example.demo;


import com.github.tobato.fastdfs.domain.conn.ConnectionManager;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;

import org.csource.fastdfs.StorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Author:zhounan
 * @Description: 文件上传测试类
 * @Date: Created in 2019/3/6 10:57
 */
@Slf4j
//@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUploadTest {

//    @Autowired
//    public FastFileStorageClient storageClient;
//
//
//    @Test
//    public void uploadTest() throws Exception {
//
//        CountDownLatch cdl = new CountDownLatch(2);
//        File file1 = new File("D:\\test\\download.txt");
//        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(100);
//        File file = new File("D:\\test\\uploadTest.zip");
//
//        //          ThreadPoolTest.threadPool(cdl,storageClient);
//
//
//        cachedThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // DefaultFastFileStorageClient storageClient = new DefaultFastFileStorageClient();
//
//                    FileInputStream fileInputStream = new FileInputStream(file);
//                    StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "zip");
//                    log.info("线程池------------------第{}次上传文件,文件地址:{}", "", storePath.getFullPath());
//
//                    System.out.println("==============" + cdl.getCount());
//                    FileWriter fileWriter = new FileWriter(file1, true);
//                    fileWriter.write(storePath.getFullPath() + "\r\n");
//                    fileWriter.close();
//                    cdl.countDown();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//        cdl.await();
//
//        System.out.println("=========end======");
//
//
//    }
//
//    public void checkDownload1(FastFileStorageClient storageClient) throws IOException {
//
//
//        int count = 0;
//        File file = new File("D:\\test\\uploadTest.zip");
//        byte[] b = new byte[1024];
//        FileInputStream fileInputStream = new FileInputStream(file);
//        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "zip");
//        log.info("线程池------------------第{}次上传文件,文件地址:{}", count, storePath.getFullPath());
//        File file1 = new File("D:\\test\\download.txt");
//        FileWriter fileWriter = new FileWriter(file1, true);
//        fileWriter.write(storePath.getFullPath() + "\r\n");
//        fileWriter.close();
//
//    }


}


//log.info("第{}次文件,文件地址:{},文件大小:{}", count, storePath.getFullPath(), len);
//ThreadPoolTest.threadPool(count);
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

