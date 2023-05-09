package com.ingemark.springdemo.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter @ToString
@Configuration
@ConfigurationProperties(prefix = "com.ingemark.spring-demo.kafka")
public class KafkaProperties {

    private String componentName;
    private String trustedPackages;
    private String consumerGroupId;
    private String bootstrapServers;
    private String securityProtocol;
    private String topicStudent;
    private String topicInstructor;
    private Integer maxRetries;
    private Long retryIntervalMs;
    private String deadLetterTopic;

}
