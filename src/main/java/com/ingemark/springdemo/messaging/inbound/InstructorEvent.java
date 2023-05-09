package com.ingemark.springdemo.messaging.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ingemark.springdemo.messaging.pojo.EventMetadata;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(setterPrefix = "with")
@JsonIgnoreProperties(ignoreUnknown = true)
public record InstructorEvent(EventMetadata eventMetadata,
                              String firstName,
                              String lastName,
                              String email,
                              BigDecimal salary) {}
