package sep3.webshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import sep3.webshop.controller.ProductController;
import sep3.webshop.model.Product;
import sep3.webshop.service.ProductService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductService productService; // Mocked Service
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Product 1", "image1.jpg", 100.0, 10, "Description 1"),
                new Product(2L, "Product 2", "image2.jpg", 200.0, 20, "Description 2")
        );
        when(productService.getAllProducts()).thenReturn(mockProducts);

        // Act
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        // Arrange
        Product mockProduct = new Product(1L, "Product 1", "image1.jpg", 100.0, 10, "Description 1");
        when(productService.getProductById(1L)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> response = productController.getProductById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product 1", response.getBody().getName());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void addProduct_ShouldReturnSavedProduct() {
        // Arrange
        Product mockProduct = new Product(1L, "Product 1", "image1.jpg", 100.0, 10, "Description 1");
        when(productService.addProduct(mockProduct)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> response = productController.addProduct(mockProduct);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product 1", response.getBody().getName());
        verify(productService, times(1)).addProduct(mockProduct);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        // Arrange
        Product updatedProduct = new Product(1L, "Updated Product", "image_updated.jpg", 150.0, 15, "Updated Description");
        when(productService.updateProduct(1L, updatedProduct)).thenReturn(updatedProduct);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Product", response.getBody().getName());
        verify(productService, times(1)).updateProduct(1L, updatedProduct);
    }

    @Test
    void reduceStock_ShouldReduceStockSuccessfully() {
        // Arrange
        Product mockProduct = new Product(1L, "Product 1", "image1.jpg", 100.0, 10, "Description 1");
        when(productService.getProductById(1L)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Void> response = productController.reduceStock(1L, 5);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).reduceStock(1L, 5);
    }
}
