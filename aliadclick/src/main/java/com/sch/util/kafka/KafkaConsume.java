package com.sch.util.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

@Component
public class KafkaConsume {


    private KafkaConsumer<String, String> consumer = null;

    public KafkaConsume() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "1");//消费者的组id
        props.put("enable.auto.commit", "false");//手动提交消息的偏移量
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("aliadclick")); // 设置订阅的 topic
    }

    public String consume() {
        // 创建一个 kafka 的对象
        ConsumerRecords<String, String> records =  consumer.poll(480000);
        for (ConsumerRecord record : records) {
            consumer.commitSync();
            return record.value().toString();
        }
        return null;
    }

}
