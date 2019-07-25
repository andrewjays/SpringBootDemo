package com.example.demo.tool.kafka.kafkstudy;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * Title: KafkaConsumerTest
 * Description:
 * kafka消费者 demo
 * 手动提交测试
 * Version:1.0.0
 *
 * @author pancm
 * @date 2018年1月26日
 */
public class KafkaConsumerTest {

    private static KafkaConsumer<String, String> consumer;
    private static ConsumerRecords<String, String> msgList;
    private static String topic;
    private static final String GROUPID = "groupE4";


    public KafkaConsumerTest(String topicName) {
        topic = topicName;
        init();
    }


    public static void test() throws IOException {
        System.out.println("---------开始消费---------");
        int messageNo = 1;
        List<String> list = new ArrayList<String>();
        List<Long> list2 = new ArrayList<Long>();
        List<String> list3 = new ArrayList<String>();
        try {
            for (; ; ) {
                msgList = consumer.poll(1000);
                if (null != msgList && msgList.count() > 0) {
                    for (ConsumerRecord<String, String> record : msgList) {
                        System.out.println
                                (messageNo + "=======receive: key = " + record.key() + ", value = " + record.value() + " offset===" + record.offset());

                        list.add(record.value());
                        list2.add(record.offset());
                        list3.add(record.value());
                        messageNo++;
                    }
                    if (list.size() == 10) {
                        // 手动提交
                        consumer.commitSync();
                        System.out.println("成功提交" + list.size() + "条,此时的offset为:" + list2.get(9));
                    } else if (list.size() > 10) {
                        consumer.close();
                        init();
                        list.clear();
                        list2.clear();
                    }
                } else {
                    BufferedWriter writer = Files.newBufferedWriter(Paths.get("D:\\test\\test\\consumer2"),
                            StandardCharsets.UTF_8);
                    writer.write(list3.toString());
                    writer.flush();
                    writer.close();
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    private static void init() {
        Properties props = new Properties();
        //kafka消费的的地址
        props.put("bootstrap.servers", "127.0.0.1:9092");
        //组名 不同组名可以重复消费
        props.put("group.id", GROUPID);
        //是否自动提交
        props.put("enable.auto.commit", "false");
        //超时时间
        props.put("session.timeout.ms", "30000");
        //一次最大拉取的条数
        props.put("max.poll.records", 10);
        //earliest当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        //latest
        //当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        //none
        //topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        props.put("auto.offset.reset", "earliest");
        //序列化
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        consumer = new KafkaConsumer<String, String>(props);
        TopicPartition p = new TopicPartition("test4", 1);
        // 指定消费分区
        consumer.assign(Arrays.asList(p));
        //订阅主题列表topic
        consumer.subscribe(Arrays.asList(topic));
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        System.out.println("初始化!");
    }


    public static void main(String args[]) throws IOException {
        KafkaConsumerTest test1 = new KafkaConsumerTest("test4");
        test();
    }
}
