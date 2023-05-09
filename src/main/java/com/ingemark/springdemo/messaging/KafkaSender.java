package com.ingemark.springdemo.messaging;

import com.ingemark.springdemo.config.properties.KafkaProperties;
import com.ingemark.springdemo.messaging.inbound.InstructorEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaSender {

    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaSender(KafkaProperties kafkaProperties, KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendInstructorEvent(InstructorEvent event) {
        kafkaTemplate.send(kafkaProperties.getTopicInstructor(), event.email(),  event);
    }
}
