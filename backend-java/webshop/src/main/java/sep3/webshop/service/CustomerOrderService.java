package sep3.webshop.service;

import sep3.webshop.model.CustomerOrder;

import java.util.List;

public interface CustomerOrderService {

    CustomerOrder addToOrder(Long customerId, Long productId, int quantity);

    List<CustomerOrder> getAllOrdersByCustomer(Long customerId);

    List<CustomerOrder> getAllOrders();

    CustomerOrder getOrderById(Long orderId);

    void checkoutOrder(Long orderId);

    void cancelOrder(Long orderId);
}
