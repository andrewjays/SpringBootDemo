package com.example.demo.kafka.producer;


/**
 * kafka调用接口
 *
 * @author Jay
 * @date  2019-3-20 14:08
 */

public interface KafkaProducerUntil {
    /**
     * 发送kafka
     *
     * @param kafkaTopic
     * @param data
     */
    void dataSyn(String kafkaTopic, Object data);

}
