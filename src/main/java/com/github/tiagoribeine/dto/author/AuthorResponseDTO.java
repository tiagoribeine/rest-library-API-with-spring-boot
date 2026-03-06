package com.github.tiagoribeine.dto.author;

import com.github.tiagoribeine.dto.book.BookSummaryDTO;

import java.time.LocalDate;
import java.util.List;

public record AuthorResponseDTO(
        Long id,
        String name,
        String nationality,
        LocalDate birthDate,
        String biography,
        List<BookSummaryDTO> books //DTO simplificado da entidade Book
) {}
