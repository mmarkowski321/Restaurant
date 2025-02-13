package org.example.restaurant.service;

import org.example.restaurant.exceptions.IsAlreadyOpen;
import org.example.restaurant.exceptions.IsNotPaid;
import org.example.restaurant.model.OrderedProduct;
import org.example.restaurant.model.Table;
import org.example.restaurant.model.TableState;
import org.example.restaurant.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService{
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    // Create
    public Table createTable(Table table) {
        return tableRepository.save(table);
    }

    // Read
    public Table getTableById(Long tableId){
        return tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
    }

    public List<Table> getAllTables(){
        return tableRepository.findAll();
    }

    // Update
    public Table updateSizeTable(Long tableId, int newSize){
        Table table = getTableById(tableId);
        table.setSize(newSize);
        return tableRepository.save(table);
    }

    public Table updateCurrentSizeTable(Long tableId, int people){
        Table table = getTableById(tableId);
        table.setCurrently_at_the_table(people);
        return tableRepository.save(table);
    }

    public Table updateTableState(Long tableId, String tableState){
        if(!isValidTableState(tableState)){
            throw new IllegalArgumentException("Invalid table state: " + tableState);
        }
        Table table = getTableById(tableId);
        table.setTableState(TableState.valueOf(tableState.toUpperCase()));
        return tableRepository.save(table);
    }

    public Table addProduct(Long tableId, OrderedProduct product){
        Table table = getTableById(tableId);
        table.getOrderedProducts().add(product);
        return tableRepository.save(table);
    }

    public Table deleteProduct(Long tableId, OrderedProduct product){
        Table table = getTableById(tableId);
        if (!table.getOrderedProducts().remove(product)) {
            throw new IllegalArgumentException("Product not found in table order");
        }
        return tableRepository.save(table);
    }

    public Table closeTable(Long tableId){
        Table table = getTableById(tableId);
        if(table.getTableState() != TableState.PAID){
            throw new IsNotPaid("Table is not paid");
        }
        table.setTableState(TableState.FREE);
        return tableRepository.save(table);
    }

    public Table openTable(Long tableId) {
        Table table = getTableById(tableId);
        if(table.getTableState() == TableState.FREE){
            throw new IsAlreadyOpen("Table is already open");
        }
        table.setTableState(TableState.TAKEN);
        return tableRepository.save(table);
    }

    private boolean isValidTableState(String state) {
        for (TableState ts : TableState.values()) {
            if (ts.name().equalsIgnoreCase(state)) {
                return true;
            }
        }
        return false;
    }
}
