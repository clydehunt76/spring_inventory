package com.acme;

import com.acme.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URLClassLoader;
import java.util.Arrays;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(HelloService helloService) {
        ClassLoader loader = Application.class.getClassLoader();
        if (loader instanceof URLClassLoader) {
            Arrays.stream(((URLClassLoader) loader).getURLs())
                    .forEach(url -> log.info("" + url.toString()));
        }
        return (args) -> {
            helloService.setMessage("Hello, world!");
        };
    }

}