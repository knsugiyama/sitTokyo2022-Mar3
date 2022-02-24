package my.after.dataAccess;

import cds.gen.ordersservice.OrderItems;
import cds.gen.ordersservice.OrderItems_;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.persistence.PersistenceService;
import java.util.List;
import java.util.stream.Collectors;
import my.after.domain.model.order.OrderId;
import my.after.domain.model.orderItem.Amount;
import my.after.domain.model.orderItem.OrderItem;
import my.after.domain.model.orderItem.Quantity;
import my.after.domain.repository.OrderItemRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemItemDataAccess implements OrderItemRepository {

  private final PersistenceService db;

  public OrderItemItemDataAccess(PersistenceService db) {
    this.db = db;
  }

  @Override
  public List<OrderItem> findByOrderId(OrderId orderId) {
    CqnSelect query = Select.from(OrderItems_.class)
        .where(oi -> oi.parent_ID().eq(orderId.value()));
    List<OrderItems> orderItems = db.run(query).listOf(OrderItems.class);

    return orderItems.stream().map(
        oi -> new OrderItem(new Amount(oi.getAmount()), new Quantity(oi.getQuantity()))
    ).collect(Collectors.toList());
  }
}
