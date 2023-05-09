package com.ingemark.springdemo.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestListenerService {

    public List<ConsumerRecord<String, String>> studentEvents = new ArrayList<>();
    public List<ConsumerRecord<String, String>> instructorFailureEvents = new ArrayList<>();

    public void clear() {
        studentEvents.clear();
        instructorFailureEvents.clear();
    }

    @KafkaListener(topics = "${com.ingemark.spring-demo.kafka.topic-student}",
            containerFactory = "testKafkaListenerContainerFactory")
    public void consumeStudentEvent(ConsumerRecord<String, String> studentEvent) {
        studentEvents.add(studentEvent);
    }

    @KafkaListener(topics = "${com.ingemark.spring-demo.kafka.dead-letter-topic}",
            containerFactory = "testKafkaListenerContainerFactory")
    public void consumeInstructorFailureEvent(ConsumerRecord<String, String> instructorFailureEvent) {
        instructorFailureEvents.add(instructorFailureEvent);
    }
}