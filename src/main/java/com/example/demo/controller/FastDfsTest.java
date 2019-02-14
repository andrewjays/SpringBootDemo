package com.example.demo.controller;

import com.example.demo.utils.FastDFSClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.UUID;

/**
 * @Author:zhounan
 * @Description: 分布式文件管理系统
 * @Date: Created in 2018/12/6 16:54
 */
@RestController
public class FastDfsTest {


    @RequestMapping("/upload")
    public String imageUpload(MultipartFile uploadFile) throws Exception {

        // 将图片上传到服务器
        FastDFSClient dfsClient = new FastDFSClient("classpath:application.properties");
        // 获取文件扩展名
        String fileName = uploadFile.getOriginalFilename();
        String extName = FilenameUtils.getExtension(fileName);
        String fastUrl = dfsClient.uploadFile(uploadFile.getBytes(), extName);
        int a = 1;
        a++;
        float b = 1;
        for (int i = 0; i <= 10; i++) {
            a = a + 1;
            b = b + 1;
            a = (int) b;
            b = (float) a;
            if (a < b) {
                a--;
            } else {

                b--;
            }
        }
        return fastUrl;

    }


    public void testDownload() {
        try {
            String conf = "classpath:application.properties";
            if (conf.contains("classpath:")) {
                conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
            }
            ClientGlobal.init(conf);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            byte[] b = storageClient.download_file("group1", "M00/00/00/ZyjovFwJ2MGAY_9vAAABUtyLJbI408.zip");
            System.out.println(b);
            IOUtils.write(b, new FileOutputStream("D:/" + UUID.randomUUID().toString() + ".zip"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
