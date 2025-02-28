package org.example.restaurant;

import org.example.restaurant.exceptions.InvalidTableStateException;
import org.example.restaurant.exceptions.IsAlreadyOpenException;
import org.example.restaurant.exceptions.IsNotPaidException;
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
    void closingTableFromFreeShouldThrowException() {
        // Given
        tableService.updateTableState(1L, "FREE");

        // When & Then
        assertThrows(IsNotPaidException.class, () -> tableService.closeTable(1L));
    }

    @Test
    void closingTableFromTakenShouldThrowException() {
        // Given
        tableService.updateTableState(1L, "TAKEN");

        // When & Then
        assertThrows(IsNotPaidException.class, () -> tableService.closeTable(1L));
    }

    @Test
    void closingTableFromTakenWithProductsShouldThrowException() {
        // Given
        tableService.updateTableState(1L, "TAKEN_WITH_PRODUCTS");

        // When & Then
        assertThrows(IsNotPaidException.class, () -> tableService.closeTable(1L));
    }

    @Test
    void closingTableFromPaidShouldSetStateToFree() {
        // Given
        tableService.updateTableState(1L, "PAID");

        // When
        tableService.closeTable(1L);

        // Then
        RestaurantTable updatedTable = tableService.getTableById(1L);
        assertEquals(TableState.FREE, updatedTable.getTableState());
    }

    @Test
    void openingTableWhenFreeShouldThrowException() {
        // Given
        tableService.updateTableState(1L, "FREE");

        // When & Then
        assertThrows(IsAlreadyOpenException.class, () -> tableService.openTable(1L));
    }

    @Test
    void openingTableFromTakenShouldKeepStateTaken() {
        // Given
        tableService.updateTableState(1L, "TAKEN");

        // When
        RestaurantTable updatedTable = tableService.openTable(1L);

        // Then
        assertEquals(TableState.TAKEN, updatedTable.getTableState());
    }

    @Test
    void openingTableFromTakenWithProductsShouldSetStateToTaken() {
        // Given
        tableService.updateTableState(1L, "TAKEN_WITH_PRODUCTS");

        // When
        RestaurantTable updatedTable = tableService.openTable(1L);

        // Then
        assertEquals(TableState.TAKEN, updatedTable.getTableState());
    }

    @Test
    void openingTableFromPaidShouldSetStateToTaken() {
        // Given
        tableService.updateTableState(1L, "PAID");

        // When
        RestaurantTable updatedTable = tableService.openTable(1L);

        // Then
        assertEquals(TableState.TAKEN, updatedTable.getTableState());
    }

    @Test
    void updatingStateToFreeShouldWork() {
        // Given

        // When
        RestaurantTable updatedTable = tableService.updateTableState(1L, "FREE");

        // Then
        assertEquals(TableState.FREE, updatedTable.getTableState());
    }

    @Test
    void updatingStateToTakenShouldWork() {
        // Given

        // When
        RestaurantTable updatedTable = tableService.updateTableState(1L, "TAKEN");

        // Then
        assertEquals(TableState.TAKEN, updatedTable.getTableState());
    }

    @Test
    void updatingStateToTakenWithProductsShouldWork() {
        // Given

        // When
        RestaurantTable updatedTable = tableService.updateTableState(1L, "TAKEN_WITH_PRODUCTS");

        // Then
        assertEquals(TableState.TAKEN_WITH_PRODUCTS, updatedTable.getTableState());
    }

    @Test
    void updatingStateToPaidShouldWork() {
        // Given

        // When
        RestaurantTable updatedTable = tableService.updateTableState(1L, "PAID");

        // Then
        assertEquals(TableState.PAID, updatedTable.getTableState());
    }

    @Test
    void updatingToInvalidStateShouldThrowException() {
        // Given

        // When & Then
        assertThrows(InvalidTableStateException.class, () -> tableService.updateTableState(1L, "INVALID"));
    }
}
