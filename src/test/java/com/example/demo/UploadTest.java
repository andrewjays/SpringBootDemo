package com.example.demo;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:zhounan
 * @Description:
 * @Date: Created in 2019/3/8 10:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class UploadTest {

    public static void uploadFile() throws IOException {
        String[] strings = null;
        StorageClient storageClient = null;
        try {
            String configFilePath = "Fastdfs.conf";
            ClientGlobal.init(configFilePath);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;

            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            strings = storageClient.upload_file("D:\\test\\uploadTest.zip", "zip", null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        String path = strings[0] + "/" + strings[1];
        File file1 = new File("D:\\test\\download.txt");
        FileWriter fileWriter = new FileWriter(file1, true);
        fileWriter.write(path + "\r\n");
        fileWriter.close();
        System.out.println("线程池------------------文件地址" + path);
    }

    public static void main(String[] args)  {
        // 多线程插入测试
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

        for (int i = 1; i < 2000; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    UploadTest.uploadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }


}
