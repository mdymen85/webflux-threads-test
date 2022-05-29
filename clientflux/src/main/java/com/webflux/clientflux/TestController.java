package com.webflux.clientflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    @RequestMapping(value = "/v1/reactive", method = RequestMethod.POST)
    public Mono<TestEntity> client() {

        var client = WebClient.create("http://localhost:8081/api/v1/reactive");

        Mono<TestEntity> resp = null;

        for (int i = 0; i < 10; i++) {
            log.info("Start Thread {} i {}", Thread.currentThread().getName(), i+1);

            var testEntity = TestEntity.builder()
                    .id(String.valueOf(i))
                    .name("Martin->"+i)
                    .build();

            var response = client
                    .post()
                    .body(Mono.just(testEntity), TestEntity.class)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(TestEntity.class)
                    .log();

            response.subscribe(j -> System.out.println(j));

            log.info("End Thread {} i {} and response {}",Thread.currentThread().getName(), i+1, response);

            resp = response;

        }

        return resp;
    }

    @RequestMapping(value = "/v1/client2", method = RequestMethod.POST)
    public Mono<TestEntity> client2() {

        var client = WebClient.create("http://localhost:8081/api/v1/test2");

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

        return response;
    }

    @RequestMapping(value = "/v1/blocking", method = RequestMethod.POST)
    public TestEntity blocking() {

        var client = WebClient.create("http://localhost:8083/api/v1/blocking");

        TestEntity resp = null;

        for (int i = 0; i < 10; i++) {
            log.info("Start Thread {} i {}", Thread.currentThread().getName(), i+1);

            var testEntity = TestEntity.builder()
                    .id(String.valueOf(i))
                    .name("Martin->"+i)
                    .build();

            var response = client
                    .post()
                    .body(Mono.just(testEntity), TestEntity.class)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(TestEntity.class)
                    .log();

            resp = response.block();

            log.info("End Thread {} i {} with response {}", Thread.currentThread().getName(), i+1, resp);

        }

        return resp;

    }
}
