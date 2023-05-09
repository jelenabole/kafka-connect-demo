package com.ingemark.springdemo.fixture;

import com.ingemark.springdemo.messaging.pojo.EventMetadata;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EventMetadataFixture {

    private EventMetadataFixture() {
    }

    public static EventMetadata.EventMetadataBuilder eventMetadataBuilder() {
        return EventMetadata.builder()
                .withId("1")
                .withSource("external-service")
                .withTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}
