package com.ingemark.springdemo.controller;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(setterPrefix = "with")
public record InstructorResponse(Long id,
                                 String firstName,
                                 String lastName,
                                 String email,
                                 BigDecimal salary){}
