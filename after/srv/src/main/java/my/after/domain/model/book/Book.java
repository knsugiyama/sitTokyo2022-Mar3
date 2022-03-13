package my.after.domain.model.book;

public class Book {

  private final BookId id;
  private final Title title;
  private final Stock stock;

  public Book(BookId id, Title title, Stock stock) {
    this.id = id;
    this.title = title;
    this.stock = stock;
  }

  public BookId getId() {
    return id;
  }

  public Title getTitle() {
    return title;
  }

  public Stock getStock() {
    return stock;
  }
}
