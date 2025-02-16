package org.example.restaurant;

import org.example.restaurant.exceptions.IsNotPaid;
import org.example.restaurant.model.*;
import org.example.restaurant.repository.TableRepository;
import org.example.restaurant.service.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateStatesTest {

    private TableService tableService;
    private TableRepository tableRepository;

    @BeforeEach
    void setUp() {
        tableRepository = mock(TableRepository.class);
        tableService = new TableService(tableRepository);

        RestaurantTable restaurantTable = new RestaurantTable(1L, 5, 4,
                List.of(new OrderedProduct("Pizza", 30.00, 1)), TableState.FREE);

        when(tableRepository.findById(1L)).thenReturn(Optional.of(restaurantTable));
        when(tableRepository.save(any(RestaurantTable.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testClosingTableFromStateFREE() {
        assertThrows(IsNotPaid.class, () -> tableService.closeTable(1L));
    }

    @Test
    void testClosingTableFromStateTAKEN() {
        RestaurantTable table = tableService.getTableById(1L);
        tableService.updateTableState(table.getId(), "TAKEN");
        assertThrows(IsNotPaid.class, () -> tableService.closeTable(1L));
    }

    @Test
    void testClosingTableFromStateTAKEN_WITH_PRODUCTS() {
        RestaurantTable table = tableService.getTableById(1L);
        tableService.updateTableState(table.getId(), "TAKEN_WITH_PRODUCTS");
        assertThrows(IsNotPaid.class, () -> tableService.closeTable(1L));
    }

    @Test
    void testClosingTableFromStatePAID() {
        RestaurantTable table = tableService.getTableById(1L);
        tableService.updateTableState(table.getId(), "PAID");
        tableService.closeTable(1L);
        RestaurantTable updatedTable = tableService.getTableById(1L);
        assertEquals(TableState.FREE, updatedTable.getTableState());
    }

    @Test
    void testClosingTableForNonExistentId() {
        when(tableRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> tableService.closeTable(99L));
    }
}
