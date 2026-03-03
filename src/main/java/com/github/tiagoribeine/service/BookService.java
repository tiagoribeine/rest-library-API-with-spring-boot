package com.github.tiagoribeine.service;

import com.github.tiagoribeine.exception.custom.DatabaseException;
import com.github.tiagoribeine.exception.custom.ResourceNotFoundException;
import com.github.tiagoribeine.model.Book;
import com.github.tiagoribeine.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    //find
    public Book findById(Long id){
        return bookRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book not found with Id: " + id)); //Tratando a exceção de inexistência do id
    }

    //find all
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    //create
    public Book create(Book book){
        book.setId(null); //Garante que cria sempre um registor novo e não sobreescreva um ja existente
        return bookRepository.save(book);
    }

    //create all
    @Transactional //Garante que: ou salva TUDO ou nada é salvo(Rollback)
    public List<Book> createAll(List<Book> books){
        //1. Validação defensiva
        if (books == null || books.isEmpty()){
            throw new IllegalArgumentException("List cannot be empty or null");
        }
        // 2.Validação de integridade antes do banco
        for(Book book: books){
            if (book.getTitle() == null || book.getTitle().isBlank()){
                throw new IllegalArgumentException("Books title cannot be empty or null");
            }
        }
        return bookRepository.saveAll(books);
    }

    //update
    public Book update(Book book, Long id){
        if (bookRepository.existsById(id)){
            book.setId(id);
            return bookRepository.save(book);
        } else{
            throw  new ResourceNotFoundException("Cannot update: Book not found!");
        }
    }

    //delete
    public void deleteBook(Long id){
        if(!bookRepository.existsById(id)){
            throw new ResourceNotFoundException("Book not found with ID: " + id);
        }

        try {
            bookRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Cannot delete: Book is linked to an existing author");
        }
    }
}

