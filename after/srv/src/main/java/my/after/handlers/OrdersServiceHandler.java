package my.after.handlers;

import cds.gen.ordersservice.Orders;
import cds.gen.ordersservice.OrdersService_;
import cds.gen.ordersservice.Orders_;
import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import my.after.boundary.order.read.OrderReadBoundary;
import my.after.boundary.order.read.OrderReadInputData;
import my.after.domain.model.order.OrderId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServiceName(OrdersService_.CDS_NAME)
public class OrdersServiceHandler implements EventHandler {

  private final static Logger logger = LoggerFactory.getLogger(OrdersServiceHandler.class);

  private final OrderReadBoundary orderReadBoundary;

  public OrdersServiceHandler(
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

  /**
   * 注文情報を保存する前の処理
   *
   * @param orders
   */
  @Before(event = CdsService.EVENT_CREATE, entity = Orders_.CDS_NAME)
  public void addOrders(Orders orders) {
/*    if (Objects.isNull(orders.getItems()) || orders.isEmpty()) {
      throw new ServiceException(ErrorStatuses.NOT_FOUND, "明細がありません。");
    }

    Map<String, Integer> orderBooks = orders.getItems().stream()
        .collect(
            Collectors.groupingBy(OrderItems::getBookId,
                Collectors.summingInt(OrderItems::getQuantity)));

    for (OrderItems orderItem : orders.getItems()) {
      CqnSelect query = Select.from(Books_.CDS_NAME).byId(orderItem.getBookId());
      Optional<Books> optionalBooks = db.run(query).first(Books.class);

      if (optionalBooks.isEmpty()) {
        throw new ServiceException(ErrorStatuses.NOT_FOUND, "書籍が見つかりません。");
      }

      Books books = optionalBooks.get();
      if (books.getStock() < orderBooks.get(books.getId()).intValue()) {
        throw new ServiceException(ErrorStatuses.BAD_REQUEST, "在庫数を超過しています。");
      }
    }*/

  }
}
