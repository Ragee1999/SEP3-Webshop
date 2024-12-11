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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

  // Constructor to set your Stripe secret key
  public StripeController() {
    // Set your Stripe secret key
    Stripe.apiKey = "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"; // Replace with your actual Stripe secret key
  }

  @PostMapping("/create-checkout-session")
  public ResponseEntity<String> createCheckoutSession(@RequestBody List<Product> products) {
    try {
      // Create the session parameters builder
      SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder();

      // Convert the products to Stripe session line items
      for (Product product : products) {
        // Build the line item for each product
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
            .setPriceData(
                SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("dkk")
                    .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(product.getName())
                            .build())
                    .setUnitAmount(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(100))
                        .longValue()) // Amount in cents
                    .build())
            .setQuantity(1L) // Adjust quantity as needed
            .build();

        // Add line item to the session params builder
        paramsBuilder.addLineItem(lineItem);  // Corrected here: add line item one by one
      }

      // Set other session parameters
      SessionCreateParams params = paramsBuilder
          .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
          .setMode(SessionCreateParams.Mode.PAYMENT) // Payment mode
          .setSuccessUrl("https://yourwebsite.com/success") // Success URL
          .setCancelUrl("https://yourwebsite.com/cancel")  // Cancel URL
          .setCustomerEmail(null)  // Don't set email directly here
          .setShippingAddressCollection(
              SessionCreateParams.ShippingAddressCollection.builder()
                  .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.DK)
                  .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.SE)
                  .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.NO)
                  .build())
          .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.AUTO)  // Automatically collect billing address
          .build();

      // Create the session using Stripe API
      Session session = Session.create(params);

      // Return the URL of the session for redirection
      return ResponseEntity.ok(session.getUrl());
    } catch (Exception e) {
      // Handle errors and send back a meaningful response
      e.printStackTrace();  // Log error for debugging
      return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
    }
  }
}
