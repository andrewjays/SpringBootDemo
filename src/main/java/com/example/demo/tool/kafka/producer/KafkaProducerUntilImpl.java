package com.example.demo.tool.kafka.producer;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author Jay
 * @description kafka数据同步发送端
 * @date Created in 2019/3/21 17:06
 */
@Slf4j
@Component
public class KafkaProducerUntilImpl implements KafkaProducerUntil {

    //    @Autowired
    //    private KafkaTemplate kafkaTemplate;

    @Override
    public void dataSyn(String kafkaTopic, Object data) {
        //        kafkaTemplate.send(kafkaTopic, JSON.toJSONString(data));
    }
}
