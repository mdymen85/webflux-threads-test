package com.webflux.tomcatblock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    private static int count = 0;

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

    @RequestMapping(value = "/v1/blocking", method = RequestMethod.POST)
    public TestEntity test(@RequestBody TestEntity testEntity) throws InterruptedException {

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
