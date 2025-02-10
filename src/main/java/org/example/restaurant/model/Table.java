package org.example.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int id;
    private int size;
    private int currently_at_the_table;
    private List<Product> ordered_products = new ArrayList<>();
    private TableState tableState;

}
