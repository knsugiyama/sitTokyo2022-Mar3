package my.after.domain.model.order;

import java.math.BigDecimal;
import java.util.List;
import my.after.domain.model.orderItem.OrderItem;

public class Total {

  private final BigDecimal value;

  public Total(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal value() {
    return value;
  }

  public static Total from(List<OrderItem> orderItems) {
    BigDecimal total = orderItems.stream()
        .map(oi -> oi.getAmount().value().multiply(
            oi.getQuantity().toBigDecimal()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    return new Total(total);
  }
}
