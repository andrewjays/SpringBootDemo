package com.example.demo.controller;

import com.example.demo.utils.FastDFSClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:zhounan
 * @Description:
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
            return fastUrl;



    }
}
