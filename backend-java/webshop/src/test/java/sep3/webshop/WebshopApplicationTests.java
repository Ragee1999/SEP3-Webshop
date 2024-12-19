package sep3.webshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebshopApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void shouldReturnProducts() {
        // Arrange
        String url = "/products";

        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }


    // Tester for product laptop som har id 1
    @Test
    void shouldReturnProductById() {
        // Arrange
        String url = "/products/1";

        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("Laptop");
    }

}