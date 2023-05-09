package com.ingemark.springdemo.webclient.request;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record NumberOfStudentsByDegreeRequest(Long numberOfStudentsByUndergraduateDegree,
                                              Long numberOfStudentsByGraduateDegree,
                                              Long numberOfStudentsByPostgraduateDegree) {}
