package com.ingemark.springdemo.webclient;

import com.ingemark.springdemo.config.properties.ClientProperties;
import com.ingemark.springdemo.webclient.request.NumberOfStudentsByDegreeRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DegreeCountWebClient {

    private final ClientProperties clientProperties;
    private final WebClient webClient;

    public DegreeCountWebClient(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.webClient = WebClient.builder()
                .baseUrl(clientProperties.getDegreeCountReporter().getBaseUrl())
                .build();
    }

    public void postDegreeCountReport(NumberOfStudentsByDegreeRequest numberOfStudentsByDegreeRequest) {
        webClient.post()
                .uri(clientProperties.getDegreeCountReporter().getCountEndpoint())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(numberOfStudentsByDegreeRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new HttpServerErrorException(response.statusCode())))
                .toBodilessEntity()
                .block();
    }
}
