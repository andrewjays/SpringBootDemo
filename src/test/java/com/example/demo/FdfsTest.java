package com.example.demo;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadFileWriter;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.csource.fastdfs.StorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class FdfsTest {
    //    @Autowired
    //    private AppendFileStorageClient storageClient;

    @Autowired
    private FastFileStorageClient storageClient;

    //    @Autowired
    //    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUpload() throws FileNotFoundException {
        //        File file = new File("D:\\test\\1.zip");
        //
        //        StorePath storePath = this.storageClient.uploadFile("group1",
        //                new FileInputStream(file), file.length(), "zip");
        //        // 带分组的路径
        //        System.out.println(storePath.getFullPath());
        //        // 不带分组的路径
        //        System.out.println(storePath.getPath());

        Class<? extends FdfsTest> aClass = this.getClass();
        URL resource = aClass.getResource("/");
        String path = resource.getPath();

        //        File file = new File("D:\\test\\2.zip");
        //
        //        DownloadFileWriter downloadFileWriter = new DownloadFileWriter(file.getName());
        //        storageClient.downloadFile("group1","group1/M00/02/B7/Zyjou1ydyTyATVLZAAAAghBcXiA790.zip",downloadFileWriter);

        int a = 1;
    }

    //    @Test
    //    public void testUploadAndCreateThumb() throws FileNotFoundException {
    //                File file = new File("D:\\test\\1.jpg");
    //                // 上传并且生成缩略图
    //                StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
    //                        new FileInputStream(file), file.length(), "jpg", null);
    //                // 带分组的路径
    //                System.out.println(storePath.getFullPath());
    //                // 不带分组的路径
    //                System.out.println(storePath.getPath());
    //                // 获取缩略图路径
    //                String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
    //                System.out.println(path);
    //    }

    //    @Test
    //    public void testDownload() {
    //        try {
    //
    //            ClientGlobal.init("D:\\work\\adups\\workspace\\demo\\src\\main\\resources\\application.properties");
    //            //ClientGlobal.initByProperties("");
    //
    ////            TrackerClient tracker = new TrackerClient();
    ////            TrackerServer trackerServer = tracker.getConnection();
    ////            StorageServer storageServer = null;
    //
    //            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
    //            byte[] b = storageClient.download_file("group1", "M00/00/00/ZyjovFwJ2MGAY_9vAAABUtyLJbI408.zip");
    //            System.out.println(b);
    //            IOUtils.write(b, new FileOutputStream("D:/" + UUID.randomUUID().toString() + ".zip"));
    //
    //
    //        } catch (Exception e) {
    //
    //            e.printStackTrace();
    //        }
    //    }
}
