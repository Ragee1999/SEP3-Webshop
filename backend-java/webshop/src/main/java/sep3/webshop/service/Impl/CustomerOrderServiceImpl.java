package sep3.webshop.service.Impl;

import org.springframework.stereotype.Service;
import sep3.webshop.model.Customer;
import sep3.webshop.model.CustomerOrder;
import sep3.webshop.model.OrderItem;
import sep3.webshop.model.Product;
import sep3.webshop.repository.CustomerOrderRepository;
import sep3.webshop.repository.ProductRepository;
import sep3.webshop.service.CustomerOrderService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository, ProductRepository productRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CustomerOrder addToOrder(Long customerId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new IllegalStateException("Not enough stock for product ID: " + productId);
        }

        // Fetch active order or create new
        CustomerOrder order = customerOrderRepository.findActiveOrderByCustomerId(customerId)
                .orElseGet(() -> {
                    CustomerOrder newOrder = new CustomerOrder();
                    newOrder.setCustomer(new Customer()); // Replace with actual customer retrieval
                    newOrder.setStatus("waiting"); // Default status
                    return newOrder;
                });

        // Add or update order item
        Optional<OrderItem> existingItem = order.getOrderItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().addQuantity(quantity);
        } else {
            OrderItem newItem = new OrderItem();
            newItem.setCustomerOrder(order);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            order.addOrderItem(newItem);
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        return customerOrderRepository.save(order);
    }

    @Override
    public List<CustomerOrder> getAllOrdersByCustomer(Long customerId) {
        return customerOrderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<CustomerOrder> getAllOrders() {
        return customerOrderRepository.findAll();
    }

    @Override
    public CustomerOrder getOrderById(Long orderId) {
        return customerOrderRepository.findOrderWithDetails(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    public void checkoutOrder(Long orderId) {
        CustomerOrder order = getOrderById(orderId);
        order.setTotalPrice(order.getOrderItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum());
        customerOrderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        CustomerOrder order = getOrderById(orderId);
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
        customerOrderRepository.delete(order);
    }

    public int countOrdersThisMonth() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        return (int) customerOrderRepository.findAll().stream()
                .filter(order -> order.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(startOfMonth))
                .count();
    }

    public void updateOrderStatus(Long orderId, String status) {
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        customerOrderRepository.save(order);
    }
}
