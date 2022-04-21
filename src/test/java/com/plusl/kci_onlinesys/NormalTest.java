package com.plusl.kci_onlinesys;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

/**
 * @program: kci_onlinesys
 * @description: 常规测试
 * @author: PlusL
 * @create: 2022-04-20 17:30
 **/

@SpringBootTest
@ContextConfiguration(classes = KciOnlinesysApplication.class)
public class NormalTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void testKafka() {
        kafkaProducer.sendMsg("test", "你好");
        kafkaProducer.sendMsg("test", "在吗");
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

@Component
class KafkaProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMsg(String topic, String content) {
        kafkaTemplate.send(topic, content);
    }
}

@Component
class KafkaConsumer {
    @KafkaListener(topics = {"test"})
    public void handleMsg(ConsumerRecord consumerRecord) {
        System.out.println(consumerRecord.value());
    }
}