package my.after.handlers;

import cds.gen.ordersservice.Orders;
import cds.gen.ordersservice.OrdersService_;
import cds.gen.ordersservice.Orders_;
import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import my.after.boundary.order.read.OrderReadBoundary;
import my.after.boundary.order.read.OrderReadInputData;
import my.after.domain.model.order.OrderId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServiceName(OrdersService_.CDS_NAME)
public class OrdersReadServiceHandler implements EventHandler {

  private final static Logger logger = LoggerFactory.getLogger(OrdersReadServiceHandler.class);

  private final OrderReadBoundary orderReadBoundary;

  public OrdersReadServiceHandler(
      OrderReadBoundary orderReadBoundary) {
    this.orderReadBoundary = orderReadBoundary;
  }

  /**
   * 注文情報を読み込んだ後の処理
   *
   * @param orders
   */
  @After(event = CdsService.EVENT_READ, entity = Orders_.CDS_NAME)
  public void readOrders(Orders orders) {
    OrderReadInputData inputData =
        new OrderReadInputData(new OrderId(orders.getId()));
    orders.setTotal(
        orderReadBoundary.calcTotal(inputData).getTotal().value());
  }
}
