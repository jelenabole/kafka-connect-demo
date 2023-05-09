package com.ingemark.springdemo.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter @ToString
@Configuration
@ConfigurationProperties(prefix = "com.ingemark.spring-demo.client")
public class ClientProperties {
    private DegreeCountReporter degreeCountReporter;

    @Getter @Setter @ToString
    public static class DegreeCountReporter {
        private String baseUrl;
        private String countEndpoint;
    }
}
