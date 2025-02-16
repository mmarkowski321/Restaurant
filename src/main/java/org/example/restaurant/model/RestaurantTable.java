package org.example.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "restaurant_table")
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int size;
    private int currently_at_the_table;
    @ElementCollection
    @CollectionTable(name = "ordered_products", joinColumns = @JoinColumn(name = "table_id"))
    private List<OrderedProduct> orderedProducts = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private TableState tableState = TableState.FREE;
}

