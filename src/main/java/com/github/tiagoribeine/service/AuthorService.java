package com.github.tiagoribeine.service;

import com.github.tiagoribeine.dto.author.AuthorRequestDTO;
import com.github.tiagoribeine.dto.author.AuthorResponseDTO;
import com.github.tiagoribeine.exception.custom.DatabaseException;
import com.github.tiagoribeine.exception.custom.ResourceNotFoundException;
import com.github.tiagoribeine.mapper.AuthorMapper;
import com.github.tiagoribeine.model.Author;
import com.github.tiagoribeine.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    //Criação - Request
    //Leitura - Response

    //find by id
    public Author findById(Long id){
      return authorRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Author not found!"));
    }

    //find by id DTO
    public AuthorResponseDTO findByIdDTO(Long id){
        return authorRepository.findById(id) //1. Tenta buscar no banco uma única vez
                .map(authorMapper::EntityToResponse) // 2. Caso encontre, será convertido ja para o dto de Resposta
                .orElseThrow(() -> new ResourceNotFoundException("Author doesn't exist wit Id:" + id)); //3. Caso não encontre, lançará a exceção
    }

    //find all
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    //find all DTO
    public List<AuthorResponseDTO> findAllDTO(){
        return authorRepository.findAll() //1. Busca todos no banco
                .stream()                 //2. Abre o fluxo de dados
                .map(authorMapper::EntityToResponse)
                .toList();
    }

    //create
    public Author create(Author author){
        author.setId(null);
        return authorRepository.save(author);
    }

    //createDTO
    public AuthorResponseDTO createDTO(AuthorRequestDTO authorDto){
        var savedAuthor = authorRepository.save(authorMapper.DtoToEntity(authorDto));
        return authorMapper.EntityToResponse(savedAuthor);
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

    //Creates a list of authors:
    @Transactional //Salva TODOS ou salva nenhum
    public List<AuthorResponseDTO> createAllDTO(List<AuthorRequestDTO> authorListDto){
        if( authorListDto == null || authorListDto.isEmpty()){
            throw new IllegalArgumentException("List cannot be empty or null");
        }

        for (AuthorRequestDTO author: authorListDto){
            if (author.name() == null || author.name().isBlank()){
                throw new IllegalArgumentException("Author name cannot be blank or null");
            }
        }

        //1. Convertendo a lista de DTOs para a lista de Entidades:
        List<Author> entities = authorListDto
                .stream() // Transforma coleção em fluxo de dados - Permite aplicar transformações em série sem precisar criar loop
                .map(authorMapper::DtoToEntity) //Converte todos os DTOs de uma vez para entidades
                .toList(); //Converte para a lista de entidades

        //2. Salva as entidades no banco de dados
        List<Author> savedAuthors = authorRepository.saveAll(entities);

        return savedAuthors
                .stream()
                .map(authorMapper::EntityToResponse)
                .toList();
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

    //update
    public AuthorResponseDTO updateDTO(AuthorRequestDTO authorDto, Long id){
        //Verificando se o id não é de um autor nulo
        Author author = authorRepository.findById(id).orElseThrow(() -> {throw new ResourceNotFoundException("Author with id " + id + " not found");});

        //Aplicando regras de negócio
        if (authorDto.name() != null){
            author.setName(authorDto.name());
        } else {
            throw new IllegalArgumentException("Author's name cannot be null");
        }

        if(authorDto.nationality() != null){
            author.setNationality(authorDto.nationality());
        } else {
            throw new IllegalArgumentException("Author's nationality cannot be null");
        }

        author.setBirthDate(authorDto.birthDate());
        author.setBiography(authorDto.biography());

        //Salvando a entidade no banco
        var savedAuthor = authorRepository.save(author);

        //Retornando a entidade
        return authorMapper.EntityToResponse(savedAuthor);
    }

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
