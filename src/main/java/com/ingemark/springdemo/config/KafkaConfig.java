package com.ingemark.springdemo.config;

import com.ingemark.springdemo.config.properties.KafkaProperties;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(DefaultKafkaProducerFactory<String, Object> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(ConsumerFactory<Object, Object> consumerFactory,
                                                                                                 CommonErrorHandler errorHandler) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(consumerFactory);
        // NOTE: on the producer side we are sending JSON String, so we have to set JsonMessageConverter as
        // MessageConverter in order to be able to deserialize event on the listener side
        factory.setMessageConverter(new JsonMessageConverter());
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public CommonErrorHandler errorHandler(KafkaProperties kafkaProperties, KafkaTemplate<String, Object> kafkaTemplate) {
        var deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (r, e) -> {
                    logger.error("Error while processing event. Topic: {}, partition: {}, offset: {}, key: {}, event:" +
                            " {}, exception: {}", r.topic(), r.partition(), r.offset(), r.key(), r.value(), e.getStackTrace());
                    return new TopicPartition(kafkaProperties.getDeadLetterTopic(), r.partition());
                });
        var backoff = new FixedBackOff(kafkaProperties.getRetryIntervalMs(), kafkaProperties.getMaxRetries());
        return new DefaultErrorHandler(deadLetterPublishingRecoverer, backoff);
    }
}
