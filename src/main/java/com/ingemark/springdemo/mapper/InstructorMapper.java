package com.ingemark.springdemo.mapper;

import com.ingemark.springdemo.controller.InstructorRequest;
import com.ingemark.springdemo.controller.InstructorResponse;
import com.ingemark.springdemo.messaging.inbound.InstructorEvent;
import com.ingemark.springdemo.model.Instructor;

public class InstructorMapper {

    private InstructorMapper() {
    }

    public static Instructor toInstructor(InstructorRequest instructorRequest) {
        return Instructor.builder()
                .withId(instructorRequest.id())
                .withFirstName(instructorRequest.firstName())
                .withLastName(instructorRequest.lastName())
                .withEmail(instructorRequest.email())
                .withSalary(instructorRequest.salary())
                .build();
    }

    public static InstructorResponse toInstructorResponse(Instructor instructor) {
        return InstructorResponse.builder()
                .withId(instructor.getId())
                .withFirstName(instructor.getFirstName())
                .withLastName(instructor.getLastName())
                .withEmail(instructor.getEmail())
                .withSalary(instructor.getSalary())
                .build();
    }

    public static Instructor mapInstructorEvent(InstructorEvent instructorEvent) {
        return Instructor.builder()
                .withFirstName(instructorEvent.firstName())
                .withLastName(instructorEvent.lastName())
                .withEmail(instructorEvent.email())
                .withSalary(instructorEvent.salary())
                .build();
    }

}
