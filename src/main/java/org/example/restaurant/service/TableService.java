package org.example.restaurant.service;

import jakarta.transaction.Transactional;
import org.example.restaurant.exceptions.*;
import org.example.restaurant.model.OrderedProduct;
import org.example.restaurant.model.RestaurantTable;
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

    @Transactional
    public RestaurantTable createTable(RestaurantTable table) {
        return tableRepository.save(table);
    }

    public RestaurantTable getTableById(Long tableId){
        return tableRepository.findById(tableId)
                .orElseThrow(() -> new RestaurantTableNotFoundException(tableId));
    }

    public List<RestaurantTable> getAllTables(){
        return tableRepository.findAll();
    }

    public RestaurantTable updateSizeTable(Long tableId, int newSize){
        RestaurantTable table = getTableById(tableId);
        table.setSize(newSize);
        return tableRepository.save(table);
    }

    public RestaurantTable updateCurrentSizeTable(Long tableId, int people){
        RestaurantTable table = getTableById(tableId);
        table.setCurrentlyAtTheTable(people);
        return tableRepository.save(table);
    }

    public RestaurantTable updateTableState(Long tableId, String tableState) {
        if (!isValidTableState(tableState)) {
            throw new InvalidTableStateException(tableState);
        }
        RestaurantTable table = getTableById(tableId);
        table.setTableState(TableState.valueOf(tableState.toUpperCase()));
        return tableRepository.save(table);
    }


    public RestaurantTable addProduct(Long tableId, OrderedProduct product){
        RestaurantTable table = getTableById(tableId);
        table.getOrderedProducts().add(product);
        return tableRepository.save(table);
    }

    public RestaurantTable deleteProduct(Long tableId, OrderedProduct product){
        RestaurantTable table = getTableById(tableId);
        if (!table.getOrderedProducts().remove(product)) {
            throw new ProductIsNotOrderedException(product.toString());
        }
        return tableRepository.save(table);
    }

    public RestaurantTable closeTable(Long tableId){
        RestaurantTable table = getTableById(tableId);
        if(table.getTableState() != TableState.PAID){
            throw new IsNotPaidException(tableId);
        }
        table.setTableState(TableState.FREE);
        return tableRepository.save(table);
    }

    public RestaurantTable openTable(Long tableId) {
        RestaurantTable table = getTableById(tableId);
        if(table.getTableState() == TableState.FREE){
            throw new IsAlreadyOpenException("Table is already open");
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
