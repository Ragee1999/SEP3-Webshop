package sep3.webshop.service;

import sep3.webshop.model.CustomerOrder;

import java.util.List;

public interface CustomerOrderService {
    List<CustomerOrder> getAllOrders();
    CustomerOrder getOrderById(Long id);
    CustomerOrder createOrder(CustomerOrder order);
    CustomerOrder updateOrder(Long id, CustomerOrder order);
    void deleteOrder(Long id);
}
