package com.example.demo.tool.apk;

import com.alibaba.fastjson.JSON;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.UseFeature;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

/**
 * apk解析
 */
public class ParserTests {

    @Test
    public void testAPK() {

        try (ApkFile apkFile = new ApkFile(new File("C:\\Users\\rlex\\Desktop\\sgm\\flipkart_ecom_app_preburn_551100_06_Apr_2015.apk"))) {
            ApkMeta apkMeta = apkFile.getApkMeta();
            System.out.println(apkMeta.getLabel());
            System.out.println(apkMeta.getPackageName());
            System.out.println(apkMeta.getVersionCode());
            System.out.println(apkMeta.getVersionName());
            System.out.println(JSON.toJSONString(apkMeta));
            for (UseFeature feature : apkMeta.getUsesFeatures()) {
                System.out.println(feature.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
