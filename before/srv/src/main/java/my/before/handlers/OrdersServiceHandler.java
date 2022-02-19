package my.before.handlers;

import cds.gen.booksservice.Books;
import cds.gen.booksservice.Books_;
import cds.gen.ordersservice.OrderItems;
import cds.gen.ordersservice.OrderItems_;
import cds.gen.ordersservice.Orders;
import cds.gen.ordersservice.OrdersService_;
import cds.gen.ordersservice.Orders_;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServiceName(OrdersService_.CDS_NAME)
public class OrdersServiceHandler implements EventHandler {

  private final static Logger logger = LoggerFactory.getLogger(OrdersServiceHandler.class);

  private final PersistenceService db;

  public OrdersServiceHandler(PersistenceService db) {
    this.db = db;
  }

  @After(event = CdsService.EVENT_READ, entity = Orders_.CDS_NAME)
  public void readOrders(Orders orders) {
    // 処理対象の注文情報を取得する
    CqnSelect query = Select.from(OrderItems_.class)
        .where(oi -> oi.parent_ID().eq(orders.getId()));
    List<OrderItems> orderItems = db.run(query).listOf(OrderItems.class);

    // 合計金額を算出する
    BigDecimal total = orderItems.stream()
        .map(oi -> oi.getAmount().multiply(new BigDecimal(oi.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    orders.setTotal(total);
  }

  @Before(event = CdsService.EVENT_CREATE, entity = Orders_.CDS_NAME)
  public void addOrders(Orders orders) {
    if (Objects.isNull(orders.getItems()) || orders.isEmpty()) {
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
    }

  }
}
