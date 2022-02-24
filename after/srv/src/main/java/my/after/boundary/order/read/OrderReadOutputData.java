package my.after.boundary.order.read;

import java.util.List;
import my.after.domain.model.order.Total;
import my.after.domain.model.orderItem.OrderItem;

public class OrderReadOutputData {

  private final Total total;

  public OrderReadOutputData(List<OrderItem> orderItems) {
    this.total = Total.from(orderItems);
  }

  public Total getTotal() {
    return total;
  }
}
