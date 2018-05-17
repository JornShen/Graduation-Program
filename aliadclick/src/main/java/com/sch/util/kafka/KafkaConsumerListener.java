package com.sch.util.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;


public class KafkaConsumerListener implements MessageListener<Integer, String> {

    public void onMessage(ConsumerRecord<Integer, String> integerStringConsumerRecord) {

        System.out.println(integerStringConsumerRecord.topic()+"  -->  "+integerStringConsumerRecord.value());
    }
}
