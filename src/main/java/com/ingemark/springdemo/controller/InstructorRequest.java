package com.ingemark.springdemo.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(setterPrefix = "with")
public record InstructorRequest(Long id,
                                @NotBlank String firstName,
                                @NotBlank String lastName,
                                @NotBlank @Email String email,
                                @NotNull @Min(0) BigDecimal salary) {}
