package my.after.boundary.order.read;

public interface OrderReadBoundary {

  OrderReadOutputData calcTotal(
      OrderReadInputData orderReadInputData);
}
