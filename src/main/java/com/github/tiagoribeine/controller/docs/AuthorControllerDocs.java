package com.github.tiagoribeine.controller.docs;

import com.github.tiagoribeine.model.Author;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Authors", description = "Endpoint for Managing Authors")
public interface AuthorControllerDocs {

    // ====================================================== FIND ALL
    @Operation(
            summary = "List all Authors" ,
            description = "Retorn a list with all books saved in the DB" ,
            tags = {"Authors"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List returned successfully",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Author.class)))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    List<Author> findAll();


    // ====================================================== FIND BY ID
    @Operation(
            summary = "List an author by its ID",
            description = "Return a book when givin an id as a Path variable",
            tags = {"Authors"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "Author returned successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    Author findById(Long id);

    // ====================================================== CREATE
    @Operation(
            summary = "Create an author",
            description = "Creates an author with the given json values",
            tags = {"Authors"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Author created successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    Author create(Author author);

    // ====================================================== CREATE ALL
    @Operation(
            summary = "Creates a list of authors",
            description = "Creates a list of authors",
            tags = {"Authors"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Authors created successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    List<Author> createAll(List<Author> authors);

    // ====================================================== UPDATE
    @Operation(
            summary = "Updates an authors",
            description = "Updates an authors",
            tags = {"Authors"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Author updated successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    Author update(Long id, Author author);

    // ====================================================== DELETE
    @Operation(
            summary = "Deletes an authors",
            description = "Deletes an authors",
            tags = {"Authors"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Author deleted successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    void delete(Long id);

}
