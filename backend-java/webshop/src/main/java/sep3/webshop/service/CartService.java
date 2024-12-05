package sep3.webshop.service;

import sep3.webshop.model.Cart;
import java.util.List;

public interface CartService {
    List<Cart> getAllCartItems();
    Cart addToCart(Long productId);
    void removeFromCart(Long cartId);
    double getTotalPrice();
    void clearCart();
}
