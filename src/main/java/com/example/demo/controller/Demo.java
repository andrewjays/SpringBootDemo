package com.example.demo.controller;

import com.example.demo.utils.ExcelExportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Component                //实例化
@Configurable             //注入bean
@EnableScheduling
public class Demo {
    private final static Logger logger = LoggerFactory.getLogger(Demo.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron = " 0/5 * * * * ? ")
    public void timePlan() {
        logger.info("这是一个测试用例");
        logger.info("现在时间:"+dateFormat.format(new Date()));
    }


@RequestMapping("/text")
    public void exportVehicleList(String vehicleReq, HttpServletResponse response) {

//        List<VehicleModelListVo> list = vehicleMapper.queryVehicleList(vehicleReq);
//        vehicleReq.setIsLimit(false);
        String fileName = null;
        String sheetName = "sheet";
        String[] headers = null;
        String[] keys = null;
        if (1 == 1) {
            fileName = "车辆列表";
            // 表头
            headers = new String[]{"VIN", "品牌", "车型", "款式", "动力总成", "发动机类型", "版型", "生产日期",
                    "车辆状态", "录入类型", "创建时间"};
            // get方法
            keys = new String[]{"Vin", "BrandName", "ModelName", "Style", "Power", "EngineStyle", "Config",
                    "ProductTime", "IsActive", "Source", "CreateTime"};

        } else if (2== 2) {
            fileName = "测试车辆列表";
            // 表头
            headers = new String[]{"VIN", "品牌", "车型", "款式", "动力总成", "发动机类型", "版型", "生产日期",
                    "车辆状态", "绑定状态", "创建时间"};
            // get方法
            keys = new String[]{"Vin", "BrandName", "ModelName", "Style", "Power", "EngineStyle", "Config",
                    "ProductTime", "IsActive", "Status", "CreateTime"};

        }

        List<Object> data = new ArrayList<>();
        data.addAll(data);
        // 写数据
        try {
            ExcelExportUtil.download(fileName, sheetName, headers, keys, data, response);
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

}
