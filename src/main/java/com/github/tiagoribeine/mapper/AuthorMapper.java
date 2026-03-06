package com.github.tiagoribeine.mapper;

import com.github.tiagoribeine.dto.author.AuthorRequestDTO;
import com.github.tiagoribeine.dto.author.AuthorResponseDTO;
import com.github.tiagoribeine.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    //Converte DtoRequest para Entidade (Para salvar)
    public Author DtoToEntity(AuthorRequestDTO dto){
        if (dto == null) {return  null;}
        Author author = new Author();
        author.setName(dto.name());
        author.setNationality(dto.nationality());
        author.setBirthDate(dto.birthDate());
        author.setBiography(dto.biography());
        return author;
    }

    // Converte Entidade para o DtoResponse (Para exibir)
    public AuthorResponseDTO EntityToResponse(Author author){
        if (author == null) {return  null;}
        AuthorResponseDTO dto = new AuthorResponseDTO(
                author.getId(),
                author.getName(),
                author.getNationality(),
                author.getBirthDate(),
                author.getBiography(),
                null
        );
        return dto;
    }
}
