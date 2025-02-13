package org.example.restaurant.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class OrderedProduct {
    private String name;
    private double price;
    private int quantity;

    public OrderedProduct() {}

    public OrderedProduct(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
