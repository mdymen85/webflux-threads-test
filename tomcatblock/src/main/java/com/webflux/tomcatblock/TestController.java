package com.webflux.tomcatblock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    private static int count = 0;

    @RequestMapping(value = "/v1/blocking", method = RequestMethod.POST)
    public TestEntity test(@RequestBody TestEntity testEntity) throws InterruptedException {

        log.info("Start with request body {} Thread {}", testEntity.getName(), Thread.currentThread().getName());

        if (count % 2 == 0) {
            log.info("counter {}",count);
            Thread.sleep(5000);
        }

        count++;

        log.info("new counter {}",count);

        log.info("End with request body {} Thread {}", testEntity.getName(), Thread.currentThread().getName());

        return TestEntity
                .builder()
                .id(count + "")
                .name("Martin")
                .build();
    }

}
