package com.github.tiagoribeine.controller;

import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    //[GET] Find an Author
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Author findAuthor(
            @PathVariable("id") Long id
    ){
        return authorService.findAuthor(id) ;
    }

    //[GET] Find all Authors;
    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Author> findAllAuthors(){
        return authorService.findAllAuthors();
    }

    //[POST] Creates an author
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Author createAuthor(
            @RequestBody Author author
    ){
        return authorService.createAuthor(author);
    }

    //[PUT] Updates an Author
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Author author(
            @PathVariable("id") Long id,
            @RequestBody Author author
    ){
        return authorService.updateAuthor(author, id);
    }

    //[[DELETE] Deletes an author
    @DeleteMapping(
            value = "/{id}"
    )
    public void deleteAuthor(
            @PathVariable("id") Long id
    ){
        authorService.deleteAuthor(id);
    }
}
