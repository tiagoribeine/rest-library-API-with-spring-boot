package com.github.tiagoribeine.service;

import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    //find
    public Author findAuthor(Long id){
      return authorRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Author not found!"));
    }

    //find all
    public List<Author> findAllAuthors(){
        return authorRepository.findAll();
    }

    //create
    public Author createAuthor(Author author){
        author.setId(null);
        return authorRepository.save(author);
    }

    //update
    public Author updateAuthor(Author author, Long id){
      author.setId(id);
      if (authorRepository.existsById(id)){
          return authorRepository.save(author);
      }
      else {
          throw new RuntimeException("Author not found!");
      }
    };

    //delete
    public void deleteAuthor(Long id){
        if (authorRepository.existsById(id)){
            authorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Author not found!");
        }
    }
}
