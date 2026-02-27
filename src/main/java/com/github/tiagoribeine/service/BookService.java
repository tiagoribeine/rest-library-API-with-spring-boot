package com.github.tiagoribeine.service;

import com.github.tiagoribeine.exception.custom.DatabaseException;
import com.github.tiagoribeine.exception.custom.ResourceNotFoundException;
import com.github.tiagoribeine.model.Book;
import com.github.tiagoribeine.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    //find
    public Book findBook(Long id){
        return bookRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book not found with Id: " + id)); //Tratando a exceção de inexistência do id
    }

    //find all
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    //create
    public Book createBook(Book book){
        book.setId(null); //Garante que cria sempre um registor novo e não sobreescreva um ja existente
        return bookRepository.save(book);
    }

    //update
    public Book updateBook(Book book, Long id){
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

