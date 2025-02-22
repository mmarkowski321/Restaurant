package org.example.restaurant;

import org.example.restaurant.model.OrderedProduct;
import org.example.restaurant.model.RestaurantTable;
import org.example.restaurant.model.TableState;
import org.example.restaurant.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AssignUpdatingOrderedProductsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TableRepository tableRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/tables";
        tableRepository.deleteAll();
    }

    @Test
    void shouldAssignProductToRestaurantTable() {

        RestaurantTable table = new RestaurantTable(null, 4, 2, List.of(), TableState.FREE);
        ResponseEntity<RestaurantTable> response = restTemplate.postForEntity(baseUrl, table, RestaurantTable.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestaurantTable createdTable = response.getBody();
        assertNotNull(createdTable);
        Long tableId = createdTable.getId();

        OrderedProduct orderedProduct = new OrderedProduct("Pizza", 40.00, 1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderedProduct> request = new HttpEntity<>(orderedProduct, headers);

        ResponseEntity<RestaurantTable> updateResponse = restTemplate.postForEntity(
                baseUrl + "/" + tableId + "/addProduct",
                request,
                RestaurantTable.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        RestaurantTable updatedTable = updateResponse.getBody();
        assertNotNull(updatedTable);
        assertEquals(1, updatedTable.getOrderedProducts().size());
        assertEquals("Pizza", updatedTable.getOrderedProducts().get(0).getName());
    }

    @Test
    void shouldDeleteProductFromRestaurantTable() {
        RestaurantTable table = new RestaurantTable(null, 4, 2, List.of(new OrderedProduct("Cola", 5.0, 1)), TableState.FREE);
        ResponseEntity<RestaurantTable> response = restTemplate.postForEntity(baseUrl, table, RestaurantTable.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestaurantTable createdTable = response.getBody();
        assertNotNull(createdTable);
        Long tableId = createdTable.getId();

        OrderedProduct productToDelete = createdTable.getOrderedProducts().get(0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderedProduct> request = new HttpEntity<>(productToDelete, headers);

        ResponseEntity<RestaurantTable> deleteResponse = restTemplate.exchange(
                baseUrl + "/" + tableId + "/deleteProduct",
                HttpMethod.DELETE,
                request,
                RestaurantTable.class
        );

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        RestaurantTable updatedTable = deleteResponse.getBody();
        assertNotNull(updatedTable);
        assertEquals(0, updatedTable.getOrderedProducts().size());
    }
}
