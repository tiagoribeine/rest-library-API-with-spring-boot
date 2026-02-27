package com.github.tiagoribeine.service;

import com.github.tiagoribeine.exception.custom.DatabaseException;
import com.github.tiagoribeine.exception.custom.ResourceNotFoundException;
import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    //find
    public Author findAuthor(Long id){
      return authorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Author not found!"));
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
          throw new ResourceNotFoundException("Author not found!");
      }
    };

    //delete
    public void deleteAuthor(Long id){
        //1. Verificando se o autor existe(Lança 404 caso não exista)
        if (!authorRepository.existsById(id)){
            throw new ResourceNotFoundException("Author not found with ID:" + id);
        }

        //2. Tentamos deletar (Lança 400 se o autor tiver livros)
        try{
            authorRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Cannot delete: Author is linked to existing books.");
        }
    }
}
