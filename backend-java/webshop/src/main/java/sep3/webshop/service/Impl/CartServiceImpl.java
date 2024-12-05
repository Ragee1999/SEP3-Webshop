package sep3.webshop.service.Impl;

import org.springframework.stereotype.Service;
import sep3.webshop.model.Cart;
import sep3.webshop.repository.CartRepository;
import sep3.webshop.repository.ProductRepository;
import sep3.webshop.service.CartService;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    @Override
    public Cart addToCart(Long productId) {
        // Fetch the product by ID using an existing service or repository
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create a new Cart item and populate details
        Cart cartItem = new Cart();
        cartItem.setProduct(product);
        cartItem.setName(product.getName());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(1); // Default quantity to 1

        // Save and return the cart item
        return cartRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public double getTotalPrice() {
        return cartRepository.findAll().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public void clearCart() {
        cartRepository.deleteAll();
    }
}
