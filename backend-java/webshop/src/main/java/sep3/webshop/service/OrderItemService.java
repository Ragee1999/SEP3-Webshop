package sep3.webshop.service;

import sep3.webshop.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems();
    OrderItem getOrderItemById(Long id);
    OrderItem createOrderItem(OrderItem orderItem);
    OrderItem updateOrderItem(Long id, OrderItem orderItem);
    void deleteOrderItem(Long id);
}


