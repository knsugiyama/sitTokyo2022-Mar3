package my.after.useCase.order;

import cds.gen.ordersservice.OrderItems;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import my.after.boundary.order.create.OrderCreateBoundary;
import my.after.boundary.order.create.OrderCreateInputData;
import my.after.domain.model.book.Book;
import my.after.domain.model.book.BookId;
import my.after.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderCreateUseCase implements OrderCreateBoundary {

  private final BookRepository bookRepository;

  public OrderCreateUseCase(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public void valid(OrderCreateInputData orderCreateInputData) {
    if (Objects.isNull(orderCreateInputData.getOrderItemsList())
        || orderCreateInputData.getOrderItemsList().isEmpty()) {
      throw new ServiceException(ErrorStatuses.NOT_FOUND, "明細がありません。");
    }

    for (OrderItems orderItem : orderCreateInputData.getOrderItemsList()) {
      Book book = bookRepository.findById(new BookId(orderItem.getBookId()));

      Map<String, Integer> orderBooks = orderCreateInputData.getOrderItemsList().stream()
          .collect(
              Collectors.groupingBy(cds.gen.ordersservice.OrderItems::getBookId,
                  Collectors.summingInt(cds.gen.ordersservice.OrderItems::getQuantity)));

      if (book.getStock().value() < orderBooks.get(book.getId().value()).intValue()) {
        throw new ServiceException(ErrorStatuses.BAD_REQUEST, "在庫数を超過しています。");
      }
    }

  }
}

