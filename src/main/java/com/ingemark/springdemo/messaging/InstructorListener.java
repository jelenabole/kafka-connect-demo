package com.ingemark.springdemo.messaging;

import com.ingemark.springdemo.messaging.inbound.InstructorEvent;
import com.ingemark.springdemo.service.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.ingemark.springdemo.mapper.InstructorMapper.mapInstructorEvent;

@Service
public class InstructorListener {
    private static final Logger logger = LoggerFactory.getLogger(InstructorListener.class);

    private final InstructorService instructorService;

    public InstructorListener(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @KafkaListener(topics = "${com.ingemark.spring-demo.kafka.topic-instructor}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeInstructorEvent(InstructorEvent instructorEvent) {
        logger.debug("Received instructorEvent: {}", instructorEvent);
        var createdInstructor = instructorService.save(mapInstructorEvent(instructorEvent));
        logger.debug("Instructor created: {}", createdInstructor);
    }
}
