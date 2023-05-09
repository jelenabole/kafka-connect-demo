package com.ingemark.springdemo.config;

import com.ingemark.springdemo.config.properties.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class TestKafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> testKafkaListenerContainerFactory(ConsumerFactory<Object, Object> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public KafkaAdmin.NewTopics topics(KafkaProperties kafkaProperties) {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(kafkaProperties.getTopicStudent()).partitions(1).build(),
                TopicBuilder.name(kafkaProperties.getTopicInstructor()).partitions(1).build(),
                TopicBuilder.name(kafkaProperties.getDeadLetterTopic()).partitions(1).build()
        );
    }
}
