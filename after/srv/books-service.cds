using {my.sample as my} from '../db';

service BooksService {

    entity Books as select from my.Books
}
