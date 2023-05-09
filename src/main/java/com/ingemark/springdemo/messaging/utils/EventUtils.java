package com.ingemark.springdemo.messaging.utils;

import com.ingemark.springdemo.messaging.pojo.EventMetadata;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class EventUtils {

    private EventUtils() {
    }

    public static EventMetadata generateKafkaMetadata() {
        return EventMetadata.builder()
                .withId(UUID.randomUUID().toString())
                .withTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .withSource("spring-demo")
                .build();
    }
}