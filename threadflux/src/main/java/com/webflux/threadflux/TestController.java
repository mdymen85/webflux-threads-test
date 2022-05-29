package com.webflux.threadflux;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    private static int count = 1;

    @RequestMapping(value = "/v1/test", method = RequestMethod.POST)
    public Mono<TestEntity> test() throws InterruptedException {

        if (count % 2 == 0) {
            log.info("counter {}",count);
            Thread.sleep(5000);
        }

        count++;

        log.info("new counter {}",count);

        return Mono.just(TestEntity
                    .builder()
                    .id(count + "")
                    .name("Martin")
                    .build()).log();
    }

//    @RequestMapping(value = "/v1/test", method = RequestMethod.POST)
//    public Mono<ServerResponse> test() {
//
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(
//                    TestEntity
//                            .builder()
//                            .id("1")
//                            .name("Martin")
//                            .build()
//                )
//                .log();
//    }
}

