package sep3.webshop;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sep3.webshop.controller.OrderHistoryController;
import sep3.webshop.model.OrderHistory;
import sep3.webshop.service.Impl.OrderHistoryService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderHistoryControllerTest {

    private OrderHistoryService orderHistoryService;
    private OrderHistoryController orderHistoryController;

    @BeforeEach
    void setUp() {
        orderHistoryService = mock(OrderHistoryService.class);
        orderHistoryController = new OrderHistoryController(orderHistoryService);
    }

    @Test
    void saveOrder_ShouldSaveOrderSuccessfully() {
        // Arrange
        OrderHistory mockOrder = new OrderHistory();
        mockOrder.setId(1L);
        mockOrder.setCustomerName("John Doe");
        mockOrder.setAmountPaid(200.0);

        // Act
        ResponseEntity<Void> response = orderHistoryController.saveOrder(mockOrder);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderHistoryService, times(1)).saveOrder(mockOrder);
    }

    @Test
    void saveOrder_ShouldReturnInternalServerErrorOnException() {
        // Arrange
        OrderHistory mockOrder = new OrderHistory();
        doThrow(new RuntimeException("Save failed")).when(orderHistoryService).saveOrder(mockOrder);

        // Act
        ResponseEntity<Void> response = orderHistoryController.saveOrder(mockOrder);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(orderHistoryService, times(1)).saveOrder(mockOrder);
    }
}
