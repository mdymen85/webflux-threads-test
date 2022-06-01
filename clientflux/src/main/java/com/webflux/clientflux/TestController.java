package com.webflux.clientflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    @RequestMapping(value = "/v1/error", method = RequestMethod.POST)
    public String testError() {
        return ResourceBundle.getBundle("error_messages").getString("ERROR-1");
    }

    private void showThreads() {
        var threads = Thread.getAllStackTraces()
                .keySet()
                .stream().toList();

        threads.forEach(t -> {
            log.info("Thread {} Name {} State {} Thread Group {}", t.getId(), t.getName(), t.getState(), t.getThreadGroup());
        });
    }

    @PostConstruct
    public void threads() {
        this.showThreads();
    }

    @RequestMapping(value = "/v1/reactive", method = RequestMethod.POST)
    public Mono<TestEntity> client() {

        long init = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        var client = WebClient.create("http://localhost:8081/api/v1/reactive");

        Mono<TestEntity> resp = null;

        for (int i = 0; i < 10; i++) {
            log.info("Start Thread {} i {}", Thread.currentThread().getName(), i+1);

            var testEntity = TestEntity.builder()
                    .id(String.valueOf(i))
                    .name("Name IT: "+i)
                    .build();

            var response = client
                    .post()
                    .body(Mono.just(testEntity), TestEntity.class)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(TestEntity.class)
                    .log();

            response.subscribe(j -> System.out.println("Total time = " + this.toMinutos(init)));

            log.info("End Thread {} i {} and response {}",Thread.currentThread().getName(), i+1, response);

            resp = response;

        }

        long finalTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        log.info("Total time =  {}", finalTime - init);

        return resp;
    }

    private long toMinutos(long timeMills) {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - timeMills;
    }

    @RequestMapping(value = "/v1/blocking", method = RequestMethod.POST)
    public TestEntity blocking() {

        long init = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        var client = WebClient.create("http://localhost:8083/api/v1/blocking");

        TestEntity resp = null;

        for (int i = 0; i < 10; i++) {
            log.info("Start Thread {} i {}", Thread.currentThread().getName(), i+1);

            var testEntity = TestEntity.builder()
                    .id(String.valueOf(i))
                    .name("Name IT: "+i)
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

        long finalTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        log.info("Total time = {}", finalTime - init);

        return resp;

    }

    @RequestMapping(value = "/v1/reactiveclient", method = RequestMethod.POST)
    public TestEntity reactiveblocking() {

        long init = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        var client = WebClient.create("http://localhost:8083/api/v1/blocking");

        Mono<TestEntity> resp = null;

        for (int i = 0; i < 10; i++) {
            log.info("Start Thread {} i {}", Thread.currentThread().getName(), i+1);

            var testEntity = TestEntity.builder()
                    .id(String.valueOf(i))
                    .name("Name IT: "+i)
                    .build();

            var response = client
                    .post()
                    .body(Mono.just(testEntity), TestEntity.class)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(TestEntity.class)
                    .log();

            response.subscribe(
                    j -> System.out.println("Total time = " + this.toMinutos(init))
            );

            log.info("End Thread {} i {} with response {}", Thread.currentThread().getName(), i+1, resp);

            resp = response;
        }

        long finalTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        log.info("Total time = {}", finalTime - init);

        return resp.block();
    }

    @RequestMapping(value = "/v1/blockingnetty", method = RequestMethod.POST)
    public TestEntity blockingnetty() {

        long init = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        var client = WebClient.create("http://localhost:8081/api/v1/blocking");

        TestEntity resp = null;

        for (int i = 0; i < 10; i++) {
            log.info("Start Thread {} i {}", Thread.currentThread().getName(), i+1);

            var testEntity = TestEntity.builder()
                    .id(String.valueOf(i))
                    .name("Name IT: "+i)
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

        long finalTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        log.info("Total time = {}", finalTime - init);

        return resp;

    }
}
