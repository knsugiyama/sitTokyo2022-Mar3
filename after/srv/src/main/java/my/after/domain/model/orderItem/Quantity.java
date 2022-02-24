package my.after.domain.model.orderItem;

import java.math.BigDecimal;

public class Quantity {

  private Integer value;

  public Quantity(Integer value) {
    this.value = value;
  }

  public Integer value() {
    return value;
  }

  public BigDecimal toBigDecimal() {
    return new BigDecimal(this.value);
  }
}
