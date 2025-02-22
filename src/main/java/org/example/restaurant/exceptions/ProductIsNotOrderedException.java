package org.example.restaurant.exceptions;

public class ProductIsNotOrderedException extends RuntimeException{
    public ProductIsNotOrderedException(String message) {
        super("Product not found in table order " + message);
    }
}
