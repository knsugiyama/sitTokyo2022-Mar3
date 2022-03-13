package my.after.dataAccess;

import cds.gen.booksservice.Books_;
import cds.gen.my.sample.Books;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.persistence.PersistenceService;
import java.util.Optional;
import my.after.domain.model.book.Book;
import my.after.domain.model.book.BookId;
import my.after.domain.model.book.Stock;
import my.after.domain.model.book.Title;
import my.after.domain.repository.BookRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookDataAccess implements BookRepository {

  private final PersistenceService db;

  public BookDataAccess(PersistenceService db) {
    this.db = db;
  }

  @Override
  public Book findById(BookId bookId) {
    CqnSelect query = Select.from(Books_.CDS_NAME).byId(bookId.value());
    Optional<Books> optionalBooks = db.run(query).first(Books.class);

    Books books = optionalBooks.orElseThrow(
        () -> new ServiceException(ErrorStatuses.NOT_FOUND, "書籍が見つかりません。")
    );

    return new Book(
        new BookId(books.getId()), new Title(books.getTitle()), new Stock(books.getStock()));
  }
}
