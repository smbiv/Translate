package ru.tinkoff.translate.translate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class TranslateApplication {
    public static void main(String[] args) {
        SpringApplication.run(TranslateApplication.class, args);
    }
}
