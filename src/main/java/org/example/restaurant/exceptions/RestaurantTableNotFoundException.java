package org.example.restaurant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantTableNotFoundException extends RuntimeException{
    public RestaurantTableNotFoundException(Long id) {
        super("Table with id " + id + " not found");
    }
}
