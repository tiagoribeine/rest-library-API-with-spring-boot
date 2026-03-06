package com.github.tiagoribeine.dto.book;

import com.github.tiagoribeine.dto.author.AuthorResponseDTO;
import com.github.tiagoribeine.model.Author;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookResponseDTO(
        long id,
        String title,
        AuthorResponseDTO author,
        String isbn,
        BigDecimal price,
        LocalDate launchDate
) {
}
