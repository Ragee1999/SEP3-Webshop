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
    public Cart addToCart(Long productId, int quantity) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Opret en ny Cart-instans
        Cart cartItem = new Cart();
        cartItem.setProduct(product);
        cartItem.setImage(product.getImage());
        cartItem.setName(product.getName());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);

        return cartRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public double getTotalPrice() {
        return cartRepository.findAll().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public void clearCart() {
        cartRepository.deleteAll();
    }
}
