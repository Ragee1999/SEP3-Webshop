package sep3.webshop.service.Impl;

import org.springframework.stereotype.Service;
import sep3.webshop.model.CustomerOrder;
import sep3.webshop.repository.CustomerOrderRepository;
import sep3.webshop.service.CustomerOrderService;

import java.util.List;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository orderRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public CustomerOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public CustomerOrder createOrder(CustomerOrder order) {
        return orderRepository.save(order);
    }

    @Override
    public CustomerOrder updateOrder(Long id, CustomerOrder order) {
        CustomerOrder existingOrder = getOrderById(id);
        existingOrder.setStatus(order.getStatus());
        existingOrder.setTotalPrice(order.getTotalPrice());
        existingOrder.setCustomer(order.getCustomer());
        existingOrder.setAddress(order.getAddress());
        existingOrder.setOrderItems(order.getOrderItems());
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
