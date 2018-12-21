package com.example.demo;

//import com.github.tobato.fastdfs.domain.StorePath;
//import com.github.tobato.fastdfs.domain.ThumbImageConfig;
//import com.github.tobato.fastdfs.service.FastFileStorageClient;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.experimental.var;
import org.apache.commons.io.IOUtils;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class FdfsTest {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUpload() throws FileNotFoundException {
        //        File file = new File("D:\\test\\1.jpg");
        //        // 上传并且生成缩略图
        //        StorePath storePath = this.storageClient.uploadFile(
        //                new FileInputStream(file), file.length(), "jpg", null);
        //        // 带分组的路径
        //        System.out.println(storePath.getFullPath());
        //        // 不带分组的路径
        //        System.out.println(storePath.getPath());
    }

    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException {
        //        File file = new File("D:\\test\\1.jpg");
        //        // 上传并且生成缩略图
        //        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
        //                new FileInputStream(file), file.length(), "jpg", null);
        //        // 带分组的路径
        //        System.out.println(storePath.getFullPath());
        //        // 不带分组的路径
        //        System.out.println(storePath.getPath());
        //        // 获取缩略图路径
        //        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        //        System.out.println(path);
    }

    @Test
    public void testDownload() {
        try {

            ClientGlobal.init("D:\\work\\adups\\workspace\\demo\\src\\main\\resources\\application.properties");
            //ClientGlobal.initByProperties("");
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
