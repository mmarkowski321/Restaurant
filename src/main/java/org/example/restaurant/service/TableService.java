package org.example.restaurant.service;

import org.example.restaurant.exceptions.IsAlreadyOpen;
import org.example.restaurant.exceptions.IsNotPaid;
import org.example.restaurant.model.Product;
import org.example.restaurant.model.Table;
import org.example.restaurant.model.TableState;
import org.example.restaurant.repository.ProductRepository;
import org.example.restaurant.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService{
    private final TableRepository tableRepository;
    private final ProductRepository productRepository;

    public TableService(TableRepository tableRepository, ProductRepository productRepository) {
        this.tableRepository = tableRepository;
        this.productRepository = productRepository;
    }

    //Create
    public Table createTable(Table table) {
        return tableRepository.save(table);
    }
    //Read
    public Table getTableById(Long tableId){
        return tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
    }
    //Update
    public Table updateSizeTable(Long tableId,int newSize){
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
        table.setSize(newSize);
        return tableRepository.save(table);
    }
    public Table updateCurrentSizeTable(Long tableId,int people){
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
        table.setCurrently_at_the_table(people);
        return tableRepository.save(table);
    }
    public Table updateTableState(Long tableId, String tableState){
        if(!isValidTableState(tableState)){
            throw new IllegalArgumentException("Invalid table state: " + tableState);
        }
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
        TableState.valueOf(tableState.toUpperCase());
        table.setTableState(TableState.valueOf(tableState.toUpperCase()));
        return tableRepository.save(table);
    }
    public Table addProduct(Long tableId, Product product){
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
        product.setTable(table);
        table.getOrderedProducts().add(product);
        productRepository.save(product);
        return tableRepository.save(table);
    }
    public Table deleteProduct(Long tableId,Product product){
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
        if(!orderedProduct(product,table.getOrderedProducts())){
            throw new IllegalArgumentException("Product " + product + " is not ordered");
        }
        product.setTable(null);
        table.getOrderedProducts().remove(product);
        productRepository.save(product);
        return tableRepository.save(table);
    }
    public List<Table> getAllTables(){
        return tableRepository.findAll();
    }
    private boolean isValidTableState(String state) {
        for (TableState ts : TableState.values()) {
            if (ts.name().equalsIgnoreCase(state)) {
                return true;
            }
        }
        return false;
    }
    private boolean orderedProduct(Product product,List<Product> orderedProducts){
        return orderedProducts.stream().anyMatch(p -> p.getId().equals(product.getId()));
    }
    public Table closeTable(Long tableId){
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
        if(table.getTableState()!=TableState.PAID){
            throw new IsNotPaid("Table is not paid");
        }
        table.setTableState(TableState.FREE);
        return tableRepository.save(table);
    }


    public Table openTable(Long tableId) {
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
        if(table.getTableState()==TableState.FREE){
            throw new IsAlreadyOpen("Table is not paid");
        }
        table.setTableState(TableState.TAKEN);
        return tableRepository.save(table);
    }
}
