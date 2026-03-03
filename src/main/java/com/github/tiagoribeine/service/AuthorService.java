package com.github.tiagoribeine.service;

import com.github.tiagoribeine.exception.custom.DatabaseException;
import com.github.tiagoribeine.exception.custom.ResourceNotFoundException;
import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    //find
    public Author findById(Long id){
      return authorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Author not found!"));
    }

    //find all
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    //create
    public Author create(Author author){
        author.setId(null);
        return authorRepository.save(author);
    }

    //Creates a list of authors:
    @Transactional //Salva TODOS ou salva nenhum
    public List<Author> createAll(List<Author> authors){
        if( authors == null || authors.isEmpty()){
            throw new IllegalArgumentException("List cannot be empty or null");
        }

        for (Author author: authors){
            if (author.getName() == null || author.getName().isBlank()){
                throw new IllegalArgumentException("Author name cannot be blank or null");
            }
        }
        return authorRepository.saveAll(authors);
    }

    //update
    public Author update(Author author, Long id){
      author.setId(id);
      if (authorRepository.existsById(id)){
          return authorRepository.save(author);
      }
      else {
          throw new ResourceNotFoundException("Author not found!");
      }
    };

    //delete
    public void delete(Long id){
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
