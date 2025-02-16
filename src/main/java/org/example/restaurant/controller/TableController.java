package org.example.restaurant.controller;

import org.example.restaurant.model.OrderedProduct;
import org.example.restaurant.model.RestaurantTable;

import org.example.restaurant.service.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    public ResponseEntity<RestaurantTable> createTable(@RequestBody RestaurantTable table){
        return ResponseEntity.ok(tableService.createTable(table));
    }

    @PostMapping("/{id}/addProduct")
    public ResponseEntity<RestaurantTable> addProduct(@PathVariable Long id, @RequestBody OrderedProduct product){
        return ResponseEntity.ok(tableService.addProduct(id, product));
    }


    @DeleteMapping("/{id}/deleteProduct")
    public ResponseEntity<RestaurantTable> deleteProduct(@PathVariable Long id, @RequestBody OrderedProduct product){
        return ResponseEntity.ok(tableService.deleteProduct(id, product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTable> getTableById(@PathVariable Long id){
        return ResponseEntity.ok(tableService.getTableById(id));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTable>> getAllTables(){
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @PutMapping("/{id}/size")
    public ResponseEntity<RestaurantTable> updateSizeTable(@PathVariable Long id, @RequestParam int newSize){
        return ResponseEntity.ok(tableService.updateSizeTable(id, newSize));
    }

    @PutMapping("/{id}/currentSize")
    public ResponseEntity<RestaurantTable> updateCurrentSizeTable(@PathVariable Long id, @RequestParam int people){
        return ResponseEntity.ok(tableService.updateCurrentSizeTable(id, people));
    }

    @PutMapping("/{id}/updateState")
    public ResponseEntity<RestaurantTable> updateTableState(@PathVariable Long id, @RequestParam String tableState){
        return ResponseEntity.ok(tableService.updateTableState(id, tableState));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<RestaurantTable> closeTable(@PathVariable Long id){
        return ResponseEntity.ok(tableService.closeTable(id));
    }

    @PutMapping("/{id}/open")
    public ResponseEntity<RestaurantTable> openTable(@PathVariable Long id){
        return ResponseEntity.ok(tableService.openTable(id));
    }
}
