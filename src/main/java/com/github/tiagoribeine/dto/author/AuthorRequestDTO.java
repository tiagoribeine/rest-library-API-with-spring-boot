package com.github.tiagoribeine.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

//Para entrada de dados - Salvar
public record AuthorRequestDTO(
        //Sem ID, pois esse será gerado pelo sistema e o cliente nao poderá mandar

        @NotBlank(message = "Name cannot be blank or empty")
        String name,

        @NotBlank(message = "Nationality cannot be blank or empty")
        String nationality,

        @Past(message = "Date must be in the past")
        LocalDate birthDate,

        String biography
) {}
