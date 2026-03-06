package com.github.tiagoribeine.controller;

import com.github.tiagoribeine.controller.docs.AuthorControllerDocs;
import com.github.tiagoribeine.dto.author.AuthorRequestDTO;
import com.github.tiagoribeine.dto.author.AuthorResponseDTO;
import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.service.AuthorService;
import jakarta.validation.Valid;
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
    public AuthorResponseDTO findById(
            @PathVariable("id") Long id
    ){
        return authorService.findByIdDTO(id) ;
    }

    //[GET] Find all Authors;
    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<AuthorResponseDTO> findAll(){
        return authorService.findAllDTO();
    }

    //[POST] Creates an author
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDTO create(
           @RequestBody AuthorRequestDTO author
    ){
        return authorService.createDTO(author);
    }

    //[POST] Creates A LIST author
    @PostMapping(
            value = "/bulk",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public List<AuthorResponseDTO> createAll(@RequestBody List<AuthorRequestDTO> authors){
        return authorService.createAllDTO(authors);
    };

    //[PUT] Updates an Author
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AuthorResponseDTO update(
            @PathVariable("id") Long id,
            @RequestBody AuthorRequestDTO author
    ){
        return authorService.updateDTO(author, id);
    }

    //[[DELETE] Deletes an author
    @DeleteMapping(
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Long id
    ){
        authorService.delete(id);
    }
}
