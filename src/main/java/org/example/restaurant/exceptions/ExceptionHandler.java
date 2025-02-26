package org.example.restaurant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(RestaurantTableNotFoundException.class)
    ResponseEntity<String> tableNotFound(RestaurantTableNotFoundException exception){

        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }
}
