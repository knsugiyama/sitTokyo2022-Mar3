package my.after.domain.repository;

import java.util.List;
import my.after.domain.model.order.OrderId;
import my.after.domain.model.orderItem.OrderItem;

public interface OrderItemRepository {

  List<OrderItem> findByOrderId(OrderId orderId);
}
