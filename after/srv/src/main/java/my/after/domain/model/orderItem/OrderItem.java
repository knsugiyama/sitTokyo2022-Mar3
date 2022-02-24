package my.after.domain.model.orderItem;

public class OrderItem {

  private Amount amount;
  private Quantity quantity;

  public OrderItem(Amount amount, Quantity quantity) {
    this.amount = amount;
    this.quantity = quantity;
  }

  public Amount getAmount() {
    return amount;
  }

  public Quantity getQuantity() {
    return quantity;
  }
}
