package org.example.restaurant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTableStateException extends RuntimeException {
    public InvalidTableStateException(String state) {
        super("Invalid table state: " + state);
    }
}
