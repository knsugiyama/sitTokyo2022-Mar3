package my.after.boundary.order.create;

import cds.gen.ordersservice.OrderItems;
import java.util.List;


public class OrderCreateInputData {

  private final List<OrderItems> orderItemsList;

  public OrderCreateInputData(List<OrderItems> orderItems) {
    this.orderItemsList = orderItems;
  }

  public List<OrderItems> getOrderItemsList() {
    return orderItemsList;
  }
}
