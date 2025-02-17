package org.example.restaurant;

import org.example.restaurant.model.*;
import org.example.restaurant.repository.TableRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RestaurantTableIntegrationTest {

    @Autowired
    private TableRepository tableRepository;

    @BeforeEach
    void setUp() {
        tableRepository.deleteAll();
    }

    @Test
    void testCreateTable() {
        RestaurantTable table = new RestaurantTable(null, 4, 2,
                new ArrayList<>(List.of(new OrderedProduct("Pizza", 30.00, 2))), TableState.FREE);
        RestaurantTable savedTable = tableRepository.save(table);
        assertNotNull(savedTable.getId());
        assertEquals(1, savedTable.getOrderedProducts().size());
    }


    @Test
    void testReadTable() {
        RestaurantTable table1 = new RestaurantTable(null, 3, 1,
                List.of(new OrderedProduct("Cola", 5.00, 3)), TableState.TAKEN);
        RestaurantTable table2 = new RestaurantTable(null, 3, 1,
                List.of(new OrderedProduct("Pepsi", 6.00, 2)), TableState.TAKEN);
        tableRepository.save(table1);
        tableRepository.save(table2);
        List<RestaurantTable> tables = tableRepository.findAll();
        assertEquals(2, tables.size());
        RestaurantTable retrievedTable = tableRepository.findById(tables.get(0).getId()).orElseThrow();
        String firstProductName = retrievedTable.getOrderedProducts().get(0).getName();
        assertEquals("Cola", firstProductName);
    }



    @Test
    void testUpdateTable() {
        RestaurantTable table = new RestaurantTable(null, 2, 0,
                new ArrayList<>(List.of(new OrderedProduct("Spaghetti", 25.00, 1))), TableState.FREE);
        RestaurantTable savedTable = tableRepository.save(table);
        savedTable.setSize(6);
        tableRepository.save(savedTable);
        Optional<RestaurantTable> updatedTable = tableRepository.findById(savedTable.getId());
        assertTrue(updatedTable.isPresent(), "Table should exist");
        assertEquals(6, updatedTable.get().getSize(), "Size should be 6");
    }


    @Test
    void testDeleteTable() {
        RestaurantTable table = new RestaurantTable(null, 5, 3,
                List.of(new OrderedProduct("Burger", 15.00, 2)), TableState.TAKEN);
        RestaurantTable savedTable = tableRepository.save(table);
        tableRepository.deleteById(savedTable.getId());
        Optional<RestaurantTable> deletedTable = tableRepository.findById(savedTable.getId());
        assertFalse(deletedTable.isPresent());
    }
}