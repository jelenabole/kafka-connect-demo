package com.ingemark.springdemo.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter @ToString
@Configuration
@ConfigurationProperties(prefix = "com.ingemark.spring-demo.scheduler")
public class SchedulerProperties {
    private RefreshDegrees refreshDegrees;
    private DegreeCount degreeCount;
    private Outbox outbox;

    @Getter @Setter @ToString
    public static class RefreshDegrees {
        private Boolean enabled;
    }

    @Getter @Setter @ToString
    public static class DegreeCount {
        private Boolean enabled;
    }

    @Getter @Setter @ToString
    public static class Outbox {
        private Boolean enabled;
        private Integer batchSize;
    }
}