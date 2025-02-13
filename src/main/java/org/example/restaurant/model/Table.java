package org.example.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int size;
    private int currently_at_the_table;
    @ElementCollection
    private List<OrderedProduct> orderedProducts = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private TableState tableState;

}
