package edu.example.json.integration.jsonb_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.config.PropertyOrderStrategy;

// https://andbin.dev/java/spring-boot/json-binding-libraries

@SpringBootApplication
public class JsonbSpringApp {

    //application wide customized Jsonb Object
    //here defined to override the default implementation
    //if the defaults work, then this is not necessary
    //in larger applications usually found in Spring Configuration object
    @Bean
    public Jsonb jsonb() {
        JsonbConfig cfg = new JsonbConfig();
        //reverse order just for testing
        cfg.setProperty(JsonbConfig.PROPERTY_ORDER_STRATEGY, PropertyOrderStrategy.REVERSE);

        return JsonbBuilder.create(cfg);
    }

    public static void main(String[] args) {
        SpringApplication.run(JsonbSpringApp.class, args);
    }
}
