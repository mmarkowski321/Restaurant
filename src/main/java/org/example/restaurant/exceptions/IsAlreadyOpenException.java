package org.example.restaurant.exceptions;

public class IsAlreadyOpenException extends RuntimeException{
    public IsAlreadyOpenException(String message) {
        super(message);
    }
}
