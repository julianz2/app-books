package com.distribuida.servicios;
import com.distribuida.config.DbConfig;
import com.distribuida.db.Book;
import com.distribuida.servicios.BookNotFoundException;
import io.helidon.common.reactive.Multi;
import io.helidon.common.reactive.Single;
import io.helidon.dbclient.DbClient;
import jakarta.enterprise.context.ApplicationScoped;
import io.helidon.common.reactive.Multi;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class BookRepository implements BooksService{

    private DbClient dbClient = DbConfig.dbClient();

    public Multi<Book> findAll() {
        return this.dbClient
                .execute(exec -> exec
                        .createQuery("SELECT * FROM books")
                        .execute()
                )
                .map(row -> row.as(Book.class));
    }

    public Single<Book> findById(Integer id) {
        return this.dbClient
                .execute(exec -> exec
                        .createGet("SELECT * FROM books WHERE id=?")
                        .addParam(id)
                        .execute()
                )
                .map(rowOptional -> rowOptional
                        .map(dbRow -> dbRow.as(Book.class)).orElseThrow(() -> new BookNotFoundException(id))
                );
    }

    public Single<Long> editBook(Integer id, Book book) {
        return this.dbClient
                .execute(exec -> exec
                        .createUpdate("update  books set isbn=:isbn,title=:title,author=:author,price=:price where id= :id")
                        .addParam("id", id)
                        .addParam("title", book.getTitle())
                        .addParam("author", book.getAuthor())
                        .addParam("isbn", book.getIsbn())
                        .addParam("price", book.getPrice())
                        .execute()
                );
    }

    public Single<Book> pushBook(Book book) {
        return this.dbClient
                .execute(exec -> exec
                        .query("INSERT INTO books (title, isbn, author, price) VALUES (?, ?, ? ,?) RETURNING id", book.getTitle(), book.getIsbn(), book.getAuthor(), book.getPrice())

                )
                .first()
                .map(data -> data.column("id").as(Book.class));
    }

    public Single<Long> deleteBook(Integer id) {
        return this.dbClient.execute(exec -> exec
                .createDelete("DELETE FROM books WHERE id = :id")
                .addParam("id", id)
                .execute()
        );
    }
}
