package my.after.domain.model.orderItem;

import java.math.BigDecimal;

public class Amount {

  private BigDecimal value;

  public Amount(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal value() {
    return value;
  }
}
