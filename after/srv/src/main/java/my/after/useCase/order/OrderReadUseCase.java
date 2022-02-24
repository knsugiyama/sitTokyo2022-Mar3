package my.after.useCase.order;

import java.util.List;
import my.after.boundary.order.read.OrderReadBoundary;
import my.after.boundary.order.read.OrderReadInputData;
import my.after.boundary.order.read.OrderReadOutputData;
import my.after.domain.model.orderItem.OrderItem;
import my.after.domain.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderReadUseCase implements OrderReadBoundary {

  private final OrderItemRepository orderItemRepository;

  public OrderReadUseCase(OrderItemRepository orderItemRepository) {
    this.orderItemRepository = orderItemRepository;
  }

  @Override
  public OrderReadOutputData calcTotal(OrderReadInputData orderReadInputData) {
    List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderReadInputData.getOrderId());
    return new OrderReadOutputData(orderItems);
  }
}
