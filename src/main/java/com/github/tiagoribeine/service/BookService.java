package com.github.tiagoribeine.service;

import com.github.tiagoribeine.dto.book.BookRequestDTO;
import com.github.tiagoribeine.dto.book.BookResponseDTO;
import com.github.tiagoribeine.exception.custom.DatabaseException;
import com.github.tiagoribeine.exception.custom.ResourceNotFoundException;
import com.github.tiagoribeine.mapper.BookMapper;
import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.model.Book;
import com.github.tiagoribeine.repository.AuthorRepository;
import com.github.tiagoribeine.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    //find
    public Book findById(Long id){
        return bookRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book not found with Id: " + id)); //Tratando a exceção de inexistência do id
    }

    //find
    public BookResponseDTO findByIdDTO(Long id){
        return bookRepository.findById(id)
                .map(bookMapper::entityToDto)
                .orElseThrow( () -> {throw new ResourceNotFoundException("Book not found with ID: " + id);});
    }

    //find all
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    //find all
    public List<BookResponseDTO> findAllDTO(){
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::entityToDto)
                .toList();
    }

    //create
    public Book create(Book book){
        book.setId(null); //Garante que cria sempre um registor novo e não sobreescreva um ja existente
        return bookRepository.save(book);
    }

    //create
    public BookResponseDTO createDTO(BookRequestDTO bookDto){
       //Buscando o autor pelo id informado no dto:
        Author author = authorRepository.findById(bookDto.authorId()).orElseThrow( () -> {throw new ResourceNotFoundException("Author not found with ID: " + bookDto.authorId());});

        // Mapeia passando o autor encontrado
        Book bookEntity = bookMapper.dtoToEntity(bookDto, author);

        //Salva e retorna o DTO
        var savedBook = bookRepository.save(bookEntity);
        return bookMapper.entityToDto(savedBook);
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


    //create all
    @Transactional //Garante que: ou salva TUDO ou nada é salvo(Rollback)
    public List<BookResponseDTO> createAllDTO(List<BookRequestDTO> booksDto){
        //1. Validação defensiva
        if (booksDto == null || booksDto.isEmpty()){
            throw new IllegalArgumentException("List cannot be empty or null");
        }

        // 3. Extraindo os ids dos autores:
        List<Long> authorsIds = booksDto
                                    .stream()
                                    .map(BookRequestDTO::authorId) //Extraindo o atributo
                                    .distinct() //Valores distintos
                                    .toList();

        // Buscar os autores e transformar em um mapa para acesso rápido (0(1))
        Map<Long, Author> authorMap = authorRepository.findAllById(authorsIds)
                .stream()
                .collect(Collectors.toMap(Author::getId, author -> author));

        // Mapeando o DTO -> Entity com validações manuais
        List<Book> entities = booksDto.stream().map(dto -> {
            if (dto.title() == null || dto.title().isBlank()) {
                throw new IllegalArgumentException("Book title cannot be Empty");
            }

            //Recupera o author do mapa
            Author author = authorMap.get(dto.authorId());
            if(author == null){
                throw new IllegalArgumentException("Author not found with ID: " + dto.authorId());
            }

            return bookMapper.dtoToEntity(dto, author);
        }).toList();

        // 5. Salvar entidades e converter para ResponseDTO
        return bookRepository.saveAll(entities)
                .stream()
                .map(bookMapper::entityToDto)
                .toList();
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

    //update
    public BookResponseDTO updateDTO(BookRequestDTO bookDto, Long id){
        Book existingBook = bookRepository.findById(id).orElseThrow( () -> {throw new ResourceNotFoundException("Book not found with ID: " + id);});

        //Buscando o author pelo id
        Author author = authorRepository.findById(bookDto.authorId()).orElseThrow( () -> {throw new ResourceNotFoundException("Autor not found with ID: " + bookDto.authorId());});

        //Atualizando o livro existente
        existingBook.setTitle(bookDto.title());
        existingBook.setAuthor(author);
        existingBook.setIsbn(bookDto.isbn());
        existingBook.setPrice(bookDto.price());
        existingBook.setLaunchDate(bookDto.launchDate());

        var savedBook = bookRepository.save(existingBook);
        return bookMapper.entityToDto(savedBook);
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

