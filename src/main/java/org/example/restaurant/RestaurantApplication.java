package org.example.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantApplication {

    public static void main(String[] args) {
        new EnvLoader();
        SpringApplication.run(RestaurantApplication.class, args);
    }

}
