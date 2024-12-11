package sep3.webshop.service.Impl;

import org.springframework.stereotype.Service;
import sep3.webshop.model.OrderItem;
import sep3.webshop.repository.OrderItemRepository;
import sep3.webshop.service.OrderItemService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByCustomerOrderId(orderId);
    }

    @Override public List<OrderItem> getAllOrderItems()
    {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem updateOrderItemQuantity(Long orderItemId, int newQuantity) {
        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));
        item.setQuantity(newQuantity);
        return orderItemRepository.save(item);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
