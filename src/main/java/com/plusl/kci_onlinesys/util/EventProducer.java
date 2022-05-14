package com.plusl.kci_onlinesys.util;

import com.alibaba.fastjson.JSONObject;
import com.plusl.kci_onlinesys.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: kci_onlinesys
 * @description: Kafka事件消息生产/消费者
 * @author: PlusL
 * @create: 2022-04-26 21:30
 **/
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 触发事件，处理事件
     * @param event
     */
    public void fireEvent(Event event) {
        //将事件发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }

}
