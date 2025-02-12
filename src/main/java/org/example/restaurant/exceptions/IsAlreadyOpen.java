package org.example.restaurant.exceptions;

public class IsAlreadyOpen extends RuntimeException{
    public IsAlreadyOpen(String message) {
        super(message);
    }
}
