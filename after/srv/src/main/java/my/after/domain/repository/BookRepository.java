package my.after.domain.repository;

import my.after.domain.model.book.Book;
import my.after.domain.model.book.BookId;

public interface BookRepository {

  Book findById(BookId bookId);
}
