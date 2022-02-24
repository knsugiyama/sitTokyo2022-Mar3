package my.after.boundary.order.read;

import my.after.domain.model.order.OrderId;

public class OrderReadInputData {

  private final OrderId orderId;

  public OrderReadInputData(OrderId orderId) {
    this.orderId = orderId;
  }

  public OrderId getOrderId() {
    return orderId;
  }
}
