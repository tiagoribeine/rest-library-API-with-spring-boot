package com.github.tiagoribeine.dto.book;

import com.github.tiagoribeine.model.Author;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookRequestDTO(
   String title,
   Long authorId,
   String isbn,
   BigDecimal price,
   LocalDate launchDate
) {}
