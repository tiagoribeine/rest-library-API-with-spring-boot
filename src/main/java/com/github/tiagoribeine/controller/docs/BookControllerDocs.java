package com.github.tiagoribeine.controller.docs;

import com.github.tiagoribeine.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Books", description = "Endpoint for managing Books") // @Tag: Define o nome e a descrição do grupo de endpoints no Swagger
public interface BookControllerDocs {

    /* @Operation: Define a identidade do endpoint (Título e Explicação).
            summary: O título do metodo
            description: Detalhamento técnico. explica regras de negócios ou nuances
            tags: Serve para agrupar endpoints - Endpoints com a mesma tag serão agrupados

       @ApiResponses: o Catálogo de resultado e respostas possíveis da API
            value = { } -> Define aLista de possíveis respostas da API(@APiResponse)
            @ApiResponse: Cada uma representa um cenário
                responseCode: código de status
                description: Descrição do código de status
                content: define a estrutura dos conteúdos retornados
                array = @ArraySchema(schema = @Schema(implementation = Book.class)): -> Informa ao Swagger que o corpo da resposta não é um objeto único, mas uma lista (coleção) de objetos do tipo X. Essencial para métodos findAll.
     */

    // ====================================================== FIND ALL
    @Operation(
            summary = "List All Books",
            description = "Retorn a list with all books saved in the DB",
            tags = {"Books"}
    )
    @ApiResponses(
            value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List returned successfully",
                    content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Book.class)))
            ),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    List<Book> findAll();


    // ====================================================== FIND BY ID
    @Operation(
            summary = "List a book by its ID",
            description = "Return a book when givin an id as a Path variable",
            tags = {"Books"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book returned successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    Book findById(Long id);


    // ====================================================== CREATE
    @Operation(
            summary = "Creates a book" ,
            description = "Creates a book with the given json values" ,
            tags = {"Books"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book created successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    Book create(Book book);

    // ====================================================== CREATE ALL
    @Operation(
            summary = "Creates a list of books",
            description = "Creates a list of books",
            tags = {"Books"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Books created successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    List<Book> createAll(@RequestBody List<Book> Book);

    // ====================================================== UPDATE
    @Operation(
            summary = "Updates a Book",
            description = "Updates a book with the given json and id values" ,
            tags = {"Books"}
    )
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Book updated successfully", content = @Content),
                @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    Book update(Long id, Book book);

    // ====================================================== DELETE
    @Operation(
            summary = "Deletes a Book",
            description = "Deletes a book by the given ID",
            tags = {"Books"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book deleted succesffully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    void delete(Long id);
}