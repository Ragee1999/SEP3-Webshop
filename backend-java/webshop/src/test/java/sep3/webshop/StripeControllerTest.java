package sep3.webshop;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import sep3.webshop.controller.StripeController;
import sep3.webshop.service.ProductService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class StripeControllerTest {

    private ProductService productService;
    private StripeController stripeController;

    @BeforeEach
    void setUp() {
        productService = Mockito.mock(ProductService.class);
        stripeController = new StripeController(productService);
    }

    @Test
    void reduceStock_ShouldReduceStockSuccessfully() {
        // Arrange
        List<StripeController.CartItem> cartItems = Arrays.asList(
                new StripeController.CartItem() {{
                    setId(1L);
                    setQuantity(5);
                }},
                new StripeController.CartItem() {{
                    setId(2L);
                    setQuantity(3);
                }}
        );

        // Act
        ResponseEntity<Void> response = stripeController.reduceStock(cartItems);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        Mockito.verify(productService, Mockito.times(1)).reduceStock(1L, 5);
        Mockito.verify(productService, Mockito.times(1)).reduceStock(2L, 3);
    }

    @Test
    void createCheckoutSession_ShouldReturnSessionUrl() throws Exception {
        // Arrange
        List<StripeController.CartItem> cartItems = Arrays.asList(
                new StripeController.CartItem() {{
                    setName("Product 1");
                    setPrice(100.0);
                    setQuantity(2);
                }},
                new StripeController.CartItem() {{
                    setName("Product 2");
                    setPrice(200.0);
                    setQuantity(1);
                }}
        );

        // Mocking the static Stripe Session.create method
        try (MockedStatic<Session> mockedSession = Mockito.mockStatic(Session.class)) {
            Session mockSession = Mockito.mock(Session.class);
            Mockito.when(mockSession.getUrl()).thenReturn("http://checkout-session-url");
            mockedSession.when(() -> Session.create(any(SessionCreateParams.class))).thenReturn(mockSession);

            // Act
            ResponseEntity<String> response = stripeController.createCheckoutSession(cartItems);

            // Assert
            assertNotNull(response);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("http://checkout-session-url", response.getBody());
        }
    }
}
