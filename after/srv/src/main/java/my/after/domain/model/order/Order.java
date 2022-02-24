package my.after.domain.model.order;

import java.util.List;
import my.after.domain.model.orderItem.OrderItem;

public class Order {

  /**
   * 注文ID
   */
  private final OrderId orderId;
  /**
   * 合計金額
   */
  private final Total total;

  public Order(OrderId orderId,
      List<OrderItem> orderItems) {
    this.orderId = orderId;
    this.total = Total.from(orderItems);
  }

  public OrderId getOrderId() {
    return orderId;
  }

  public Total getTotal() {
    return total;
  }
}
