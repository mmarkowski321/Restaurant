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
    private int currentlyAtTheTable;
    @ElementCollection
    @CollectionTable(name = "ordered_products", joinColumns = @JoinColumn(name = "table_id"))
    private List<OrderedProduct> orderedProducts = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private TableState tableState = TableState.FREE;

    public RestaurantTable() {
    }

    public RestaurantTable(Long id, int size, int currentlyAtTheTable, List<OrderedProduct> orderedProducts, TableState tableState) {
        this.id = id;
        this.size = size;
        this.currentlyAtTheTable = currentlyAtTheTable;
        this.orderedProducts = orderedProducts;
        this.tableState = tableState;
    }
}

