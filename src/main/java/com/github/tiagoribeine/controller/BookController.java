package com.github.tiagoribeine.controller;

import com.github.tiagoribeine.controller.docs.BookControllerDocs;
import com.github.tiagoribeine.dto.book.BookRequestDTO;
import com.github.tiagoribeine.dto.book.BookResponseDTO;
import com.github.tiagoribeine.model.Book;
import com.github.tiagoribeine.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController implements BookControllerDocs {

    @Autowired
    private BookService bookService;

    //[GET] FIND A BOOK
    @GetMapping("/{id}")
    public BookResponseDTO findById(
            @PathVariable("id") Long id
    ){
        return bookService.findByIdDTO(id);
    }

    //[GET] FIND ALL BOOKS
    @GetMapping("")
    public List<BookResponseDTO> findAll(){
        
        return bookService.findAllDTO();
    }

    // [POST] CREATE A BOOK
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(
            @RequestBody Book book
    ){
        return bookService.create(book);
    }

    // [POST] CREATE MANY BOOKS
    @PostMapping(
            value = "/bulk",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED) //Indica sucesso na criação
    public List<BookResponseDTO> createAll(
            @RequestBody List<BookRequestDTO> books){
        return bookService.createAllDTO(books);
    }

    // [PUT] UPDATE A BOOK
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public BookResponseDTO update(
            @PathVariable("id") Long id,
            @RequestBody BookRequestDTO book
    ){
        return bookService.updateDTO(book, id);
    }

    // [DELETE] DELETES A BOOK
    @DeleteMapping(value = "/{id}")
    public void delete(
            @PathVariable("id") Long id
    ){
        bookService.deleteBook(id);
    }


}
