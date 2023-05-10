package com.apartment.billing.reportingandbilling.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BillProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendNotification(String message) {
        kafkaTemplate.send("billTopic",message);
    }
}
