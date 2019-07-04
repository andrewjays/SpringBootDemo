package com.example.demo.spring.controller;


import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import lombok.extern.slf4j.Slf4j;
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
        int filesize = 1024 * 1024 * 50;
        InputStream inputStream = uploadFile.getInputStream();
        int count = 1;
        StorePath path = null;
        while (inputStream.available() > 0) {
            log.info("第" + count + "次:" + filesize + "流大小:" + inputStream.available());
            int size = inputStream.available() > filesize ? filesize : inputStream.available();
            if (count == 1) {
                path = storageClient.uploadAppenderFile("group1", inputStream
                        , size, "zip");
            } else {
                storageClient.appendFile("group1", path.getPath(), inputStream, size);
            }
            log.info("第" + count + "次上传成功:" + filesize);
            count++;
        }
        inputStream.close();
        return path.getFullPath();
    }
}
