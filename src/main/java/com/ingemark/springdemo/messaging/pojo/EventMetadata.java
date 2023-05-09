package com.ingemark.springdemo.messaging.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonIgnoreProperties(ignoreUnknown = true)
public record EventMetadata(String id,
                            String source,
                            String timestamp) {}

