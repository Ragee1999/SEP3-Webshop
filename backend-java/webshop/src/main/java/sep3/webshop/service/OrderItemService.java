package sep3.webshop.service;

import sep3.webshop.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    List<OrderItem> getAllOrderItems();

    OrderItem updateOrderItemQuantity(Long orderItemId, int newQuantity);

    void deleteOrderItem(Long orderItemId);
}
