package sep3.webshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep3.webshop.model.Product;
import sep3.webshop.repository.ProductRepository;
import sep3.webshop.service.Impl.ProductServiceImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Act
        var products = productService.getAllProducts();

        // Assert
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        var result = productService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Product 1", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void addProduct_ShouldAddNewProduct() {
        // Arrange
        Product product = new Product();
        product.setName("New Product");

        when(productRepository.save(product)).thenReturn(product);

        // Act
        var result = productService.addProduct(product);

        // Assert
        assertNotNull(result);
        assertEquals("New Product", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void reduceStock_ShouldReduceStock_WhenSufficientStockExists() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setStockQuantity(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // Act
        productService.reduceStock(1L, 5);

        // Assert
        assertEquals(5, product.getStockQuantity());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void reduceStock_ShouldThrowException_WhenInsufficientStock() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setStockQuantity(4);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> productService.reduceStock(1L, 5));
        assertEquals("Not enough stock for product ID: 1", exception.getMessage());
        verify(productRepository, times(0)).save(product);
    }
}
