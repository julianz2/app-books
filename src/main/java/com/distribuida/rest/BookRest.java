package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.servicios.BookRepository;
import io.helidon.common.reactive.Multi;
import io.helidon.common.reactive.Single;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/books")

public class BookRest {

    @Inject
    private BookRepository bookService;


    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Book> findAll() {
        return bookService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<Book> findById(@PathParam("id") Integer id){
        return bookService.findById(id);
    }


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Single<Book> pushBook(Book book){
        return bookService.pushBook(book);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Single<Long> editBook(@PathParam("id") Integer id, Book book) {
        return bookService.editBook(id, book);
    }

    @DELETE
    @Path("/{id}")
    public Single<Long> deleteBook(@PathParam("id") Integer id){
        return bookService.deleteBook(id);
    }


}
