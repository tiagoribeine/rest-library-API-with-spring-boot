package com.github.tiagoribeine.mapper;

import com.github.tiagoribeine.dto.book.BookRequestDTO;
import com.github.tiagoribeine.dto.book.BookResponseDTO;
import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BookMapper {

    @Autowired
    public AuthorMapper authorMapper;

    public Book dtoToEntity(BookRequestDTO dto, Author author) {
        return new Book(
                dto.title(),
                author,
                dto.isbn(),
                dto.price(),
                dto.launchDate()
        );
    }

    public BookResponseDTO entityToDto(Book entity){
        return new BookResponseDTO(
            entity.getId(),
            entity.getTitle(),
           entity.getAuthor() != null? authorMapper.EntityToResponse(entity.getAuthor()) : null,
            entity.getIsbn(),
            entity.getPrice(),
            entity.getLaunchDate()
        );
    }
}
