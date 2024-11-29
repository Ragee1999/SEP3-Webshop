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
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found with id: " + id));
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        OrderItem existingItem = getOrderItemById(id);
        existingItem.setPrice(orderItem.getPrice());
        existingItem.setQuantity(orderItem.getQuantity());
        existingItem.setProduct(orderItem.getProduct());
        existingItem.setCustomerOrder(orderItem.getCustomerOrder());
        return orderItemRepository.save(existingItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
