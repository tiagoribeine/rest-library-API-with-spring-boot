package com.github.tiagoribeine.controller;

import com.github.tiagoribeine.controller.docs.AuthorControllerDocs;
import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorController implements AuthorControllerDocs {

    @Autowired
    private AuthorService authorService;

    //[GET] Find an Author
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Author findById(
            @PathVariable("id") Long id
    ){
        return authorService.findById(id) ;
    }

    //[GET] Find all Authors;
    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Author> findAll(){
        return authorService.findAll();
    }

    //[POST] Creates an author
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(
            @RequestBody Author author
    ){
        return authorService.create(author);
    }

    //[POST] Creates A LIST author
    @PostMapping(
            value = "/bulk",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public List<Author> createAll(@RequestBody List<Author> authors){
        return authorService.createAll(authors);
    };

    //[PUT] Updates an Author
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Author update(
            @PathVariable("id") Long id,
            @RequestBody Author author
    ){
        return authorService.update(author, id);
    }

    //[[DELETE] Deletes an author
    @DeleteMapping(
            value = "/{id}"
    )
    public void delete(
            @PathVariable("id") Long id
    ){
        authorService.delete(id);
    }
}
