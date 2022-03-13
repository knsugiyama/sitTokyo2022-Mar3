package my.after.handlers;

import cds.gen.ordersservice.Orders;
import cds.gen.ordersservice.OrdersService_;
import cds.gen.ordersservice.Orders_;
import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import my.after.boundary.order.create.OrderCreateBoundary;
import my.after.boundary.order.create.OrderCreateInputData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServiceName(OrdersService_.CDS_NAME)
public class OrdersCreateServiceHandler implements EventHandler {

  private final static Logger logger = LoggerFactory.getLogger(OrdersCreateServiceHandler.class);

  private final OrderCreateBoundary orderCreateBoundary;

  public OrdersCreateServiceHandler(
      OrderCreateBoundary orderCreateBoundary) {
    this.orderCreateBoundary = orderCreateBoundary;
  }

  /**
   * 注文情報を保存する前の処理
   *
   * @param orders
   */
  @Before(event = CdsService.EVENT_CREATE, entity = Orders_.CDS_NAME)
  public void beforeCreateOrders(Orders orders) {
    orderCreateBoundary.valid(new OrderCreateInputData(orders.getItems()));
  }
}
