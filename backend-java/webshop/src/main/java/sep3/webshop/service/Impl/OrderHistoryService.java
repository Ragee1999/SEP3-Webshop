package sep3.webshop.service.Impl;

import org.springframework.stereotype.Service;
import sep3.webshop.model.OrderHistory;
import sep3.webshop.repository.OrderHistoryRepository;

@Service
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    public void saveOrder(OrderHistory orderHistory) {
        orderHistoryRepository.save(orderHistory);
    }
}
