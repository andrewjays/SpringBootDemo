package com.example.demo.tool.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Jay
 * @description
 * @date Created in 2019/7/2 16:10
 */
@Component
public class Consumer {

    @KafkaListener(topics = {"test"})
    public void listenUpdateProcessLog(ConsumerRecord<String, String> record) {
        // 发送过来的data
        String value = record.value();
        System.out.println(value);

    }
}
