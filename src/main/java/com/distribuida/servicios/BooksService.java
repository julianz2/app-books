package com.distribuida.servicios;

import com.distribuida.db.Book;
import java.util.List;
import io.helidon.common.reactive.Single;
import io.helidon.common.reactive.Multi;


public interface BooksService {
    Single<Book> findById(Integer id);
    Multi<Book> findAll();
    Single<Book> pushBook(Book book);

    Single<Long> editBook(Integer id, Book book);

    Single<Long> deleteBook(Integer id);
}
