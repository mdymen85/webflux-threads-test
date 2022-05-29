package com.webflux.clientflux;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping(value = "/v1/client", method = RequestMethod.POST)
    public Mono<TestEntity> client() {

        var client = WebClient.create("http://localhost:8081/api/v1/test");

        var response = client.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(TestEntity.class)
                .log();
        response.subscribe(i -> System.out.println(i));

        var response1 = client.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(TestEntity.class)
                .log();
        response1.subscribe(i -> System.out.println(i));

        var response2 = client.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(TestEntity.class)
                .log();
        response1.subscribe(i -> System.out.println(i));

        var response3 = client.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(TestEntity.class)
                .log();
        response1.subscribe(i -> System.out.println(i));

        var response4 = client.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(TestEntity.class)
                .log();
        response1.subscribe(i -> System.out.println(i));

        var response5 = client.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(TestEntity.class)
                .log();
        response1.subscribe(i -> System.out.println(i));


        return response;
    }

}
