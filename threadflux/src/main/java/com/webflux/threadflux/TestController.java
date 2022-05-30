package com.webflux.threadflux;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    private static int count = 1;

    @RequestMapping(value = "/v1/reactive", method = RequestMethod.POST)
    public Mono<TestEntity> reactive(@RequestBody TestEntity testEntity) throws InterruptedException {

        log.info("Thread {} Start with request body {}", Thread.currentThread().getName(), testEntity.getName());

        if (count % 2 == 0) {
            log.info("counter {}",count);
            Thread.sleep(5000);
        }

        count++;

        log.info("new counter {}",count);

        log.info(" Thread {} = End with request body {}", Thread.currentThread().getName(), testEntity.getName());

        return Mono.just(TestEntity
                    .builder()
                    .id(testEntity.getId())
                    .name(testEntity.getName())
                    .build()).log();
    }

    @RequestMapping(value = "/v1/blocking", method = RequestMethod.POST)
    public TestEntity blocking(@RequestBody TestEntity testEntity) throws InterruptedException {

        log.info("Thread {} Start with request body {}", Thread.currentThread().getName(), testEntity.getName());

        if (count % 2 == 0) {
            log.info("counter {}",count);
            Thread.sleep(5000);
        }

        count++;

        log.info("new counter {}",count);

        log.info(" Thread {} = End with request body {}", Thread.currentThread().getName(), testEntity.getName());

        return TestEntity
                .builder()
                .id(testEntity.getId())
                .name(testEntity.getName())
                .build();
    }
}

