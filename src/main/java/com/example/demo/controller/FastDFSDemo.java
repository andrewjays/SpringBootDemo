package com.example.demo.controller;



import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author:zhounan
 * @Description: 大文件分批上传以及续传
 * @Date: Created in 2019/2/28 19:36
 */
@Slf4j
@RestController
public class FastDFSDemo {

    @Autowired
    private AppendFileStorageClient storageClient;

    @PostMapping("/fileLoad")
    public String fileUpLoad(MultipartFile uploadFile) throws Exception {
        int filesize = 1024 * 80;
        File file = null;
        InputStream inputStream = uploadFile.getInputStream();

        byte[] b = new byte[filesize];
        int count = 1;
        int len = 0;
        StorePath path = null;
        while ((len = IOUtils.read(inputStream, b)) != -1&&len!=0) {
            log.info("第" + count + "次:" + len);
            if (count == 1) {
                path = storageClient.uploadAppenderFile("group1", inputStream
                        , len, "docx");

            } else if (len < filesize ) {
                FileOutputStream fos = new FileOutputStream("D:\\test\\temp");
                IOUtils.write(b, fos);
                file = new File("D:\\test\\temp");
                FileInputStream fileInputStream = new FileInputStream(file);
                storageClient.appendFile("group1", path.getPath(), fileInputStream, len);
            }
            count++;
        }
        inputStream.close();
        return path.getFullPath();
    }
}
