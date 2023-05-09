package com.ingemark.springdemo.fixture;

import com.ingemark.springdemo.controller.InstructorRequest;
import com.ingemark.springdemo.messaging.inbound.InstructorEvent;
import com.ingemark.springdemo.model.Instructor;

import java.math.BigDecimal;

public class InstructorFixture {

    private InstructorFixture() {
    }

    public static Instructor.InstructorBuilder instructorBuilder() {
        return Instructor.builder()
                .withEmail("random@email.com")
                .withFirstName("firstName")
                .withLastName("lastName")
                .withSalary(BigDecimal.valueOf(950.35));
    }

    public static InstructorRequest.InstructorRequestBuilder instructorRequestBuilder() {
        return InstructorRequest.builder()
                .withEmail("newemail@email.com")
                .withFirstName("new name")
                .withLastName("new lastname")
                .withSalary(BigDecimal.valueOf(899.90));
    }

    public static InstructorEvent.InstructorEventBuilder instructorEventBuilder() {
        return InstructorEvent.builder()
                .withEventMetadata(EventMetadataFixture.eventMetadataBuilder().build())
                .withFirstName("firstName")
                .withLastName("lastName")
                .withEmail("instructor@email.com")
                .withSalary(BigDecimal.valueOf(123.456));
    }
}