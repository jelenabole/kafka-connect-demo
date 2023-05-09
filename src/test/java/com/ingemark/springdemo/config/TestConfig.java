package com.ingemark.springdemo.config;

import com.ingemark.springdemo.service.InstructorService;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class TestConfig {

    // NOTE: this is placed here because @SpyBean prevents us from reusing an already started application context, so
    // it creates a new one with the new TestListenerService which causes some tests to fail. When this is placed in
    // TestBase, it is instantiated at the very beginning of tests execution, so only one application context is created
    @SpyBean
    protected KafkaTemplate<String, Object> kafkaTemplate;

    @SpyBean
    protected InstructorService instructorService;

}
