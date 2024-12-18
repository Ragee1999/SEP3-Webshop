package sep3.webshop.controller;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sep3.webshop.model.Product;
import sep3.webshop.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

  private final ProductService productService;

  public StripeController(ProductService productService) {
    this.productService = productService;
    Stripe.apiKey = "sk_test_51QSbQTFzrlRAcoaO3Q9F6m6WjSlp3g9k54n5AeHPrNBTxWIKPgY5NUaixlKAq208LklRJQAnCLzNwoVM3XVnSUXp00E9Nwdsgg";
  }

  @PostMapping("/reduce-stock")
  public ResponseEntity<Void> reduceStock(@RequestBody List<CartItem> cartItems) {
    try {
      for (CartItem cartItem : cartItems) {
        productService.reduceStock(cartItem.getId(), cartItem.getQuantity());
      }
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(500).build();
    }
  }

  @PostMapping("/create-checkout-session")
  public ResponseEntity<String> createCheckoutSession(@RequestBody List<CartItem> cartItems) {
    try {
      // Create session params
      SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder();

      // Add products to the session using CartItem data
      for (CartItem cartItem : cartItems) {
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("dkk")
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName(cartItem.getName())
                                                .build())
                                .setUnitAmount(
                                        BigDecimal.valueOf(cartItem.getPrice())
                                                .multiply(BigDecimal.valueOf(100))
                                                .longValue())
                                .build())
                .setQuantity((long) cartItem.getQuantity())
                .build();

        paramsBuilder.addLineItem(lineItem);
      }

      // Add shipping and billing address collections
      SessionCreateParams params = paramsBuilder
              .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
              .setMode(SessionCreateParams.Mode.PAYMENT)
              .setSuccessUrl("http://localhost:5203/payment-success?session_id={CHECKOUT_SESSION_ID}")
              .setCancelUrl("http://localhost:5203/payment-error")
              .setShippingAddressCollection(
                      SessionCreateParams.ShippingAddressCollection.builder()
                              .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.DK)
                              .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.SE)
                              .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.NO)
                              .build()
              )
              .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.AUTO)
              .build();

      // Create the session and get the URL for redirection
      Session session = Session.create(params);

      return ResponseEntity.ok(session.getUrl());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
    }
  }


  public static class CartItem {
    private Long id;
    private String name;
    private Double price;
    private int quantity;

    // Getters and setters
    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Double getPrice() {
      return price;
    }

    public void setPrice(Double price) {
      this.price = price;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }
  }
}
