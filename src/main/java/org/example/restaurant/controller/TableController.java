package org.example.restaurant.controller;

import org.example.restaurant.model.Product;
import org.example.restaurant.model.Table;
import org.example.restaurant.model.TableState;
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
    public ResponseEntity<Table> createTable(@RequestBody Table table){
        return ResponseEntity.ok(tableService.createTable(table));
    }
    @PostMapping
    public ResponseEntity<Table> addProduct(@PathVariable Long tableId, @RequestBody Product product){
        return ResponseEntity.ok(tableService.addProduct(tableId,product));
    }
    @PostMapping("/{id}")
    public ResponseEntity<Table> deleteProduct(@PathVariable Long tableId, @RequestBody Product product){
        return ResponseEntity.ok(tableService.deleteProduct(tableId,product));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable Long tableId){
        return ResponseEntity.ok(tableService.getTableById(tableId));
    }
    @GetMapping
    public ResponseEntity<List<Table>> getallTables(){
        return ResponseEntity.ok(tableService.getAllTables());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Table> updateSizeTable(@PathVariable Long tableId, int newSize){
        return ResponseEntity.ok(tableService.updateSizeTable(tableId,newSize));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Table> updateCurrentSizeTable(@PathVariable Long tableId, int people){
        return ResponseEntity.ok(tableService.updateCurrentSizeTable(tableId,people));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Table> updateTableState(@PathVariable Long tableId, String tableState){
        return ResponseEntity.ok(tableService.updateTableState(tableId,tableState));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Table> closeTable(@PathVariable Long tableId){
        return ResponseEntity.ok(tableService.closeTable(tableId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Table> openTable(@PathVariable Long tableId){
        return ResponseEntity.ok(tableService.openTable(tableId));
    }


}
