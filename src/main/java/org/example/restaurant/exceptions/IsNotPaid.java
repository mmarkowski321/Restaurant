package org.example.restaurant.exceptions;

public class IsNotPaid extends RuntimeException {
    public IsNotPaid(String message) {
        super(message);
    }
}
