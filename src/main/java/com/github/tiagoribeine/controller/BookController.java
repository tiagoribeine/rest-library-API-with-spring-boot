package com.github.tiagoribeine.controller;

import com.github.tiagoribeine.model.Book;
import com.github.tiagoribeine.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;


    //[GET] FIND A BOOK
    @GetMapping("/{id}")
    public Book findBook(
            @PathVariable("id") Long id
    ){
        return bookService.findBook(id);
    }

    //[GET] FIND ALL BOOKS
    @GetMapping("")
    public List<Book> findAllBooks(){
        return bookService.findAllBooks();
    }

    // [POST] CREATE A BOOK
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Book createBook(
            @RequestBody Book book
    ){
        return bookService.createBook(book);
    }

    // [PUT] UPDATE A BOOK
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Book updateBook(
            @PathVariable("id") Long id,
            @RequestBody Book book
    ){
        return bookService.updateBook(book, id);
    }

    // [DELETE] DELETES A BOOK
    @DeleteMapping(value = "/{id}")
    public void deleteBook(
            @PathVariable("id") Long id
    ){
        bookService.deleteBook(id);
    }


}
