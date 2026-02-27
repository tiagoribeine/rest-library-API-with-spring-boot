package com.github.tiagoribeine.exception;

import com.github.tiagoribeine.exception.custom.DatabaseException;
import com.github.tiagoribeine.exception.custom.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

//Classe Central - Intercepta exceções de todos os Controllers

@RestControllerAdvice() //Registra essa classe no Spring como Handler global - Qualquer exceção não tratada no Controller cai aqui
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class); //Criará um logo, retendo informações sensíveis apenas para o desenvolvimento, como nome de tabelas etc

    @ExceptionHandler(Exception.class) // Esse metodo será executado sempre que uma Exception for lançada
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex){ //Recebe a exceção lançada como parâmetro

        // 2. Registra o erro no console com nível de severidade ERROR - Boas práticas de segurança
        logger.error("Error: {}", ex.getMessage(), ex);

        var error = new ErrorResponse(
                500, //HTTP Status code
                "An unexpected internal error occurred. Please contact support or check logs!", //Mensagem de erro a ser exibida no JSON
                LocalDateTime.now() //Momento que ocorreu
        );

        return ResponseEntity //Começa a construir a resposta HTTP
                .status(HttpStatus.INTERNAL_SERVER_ERROR) //Define o status retornado como 500
                .body(error); // JSON molde definido em ErrorResponse
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){

        logger.warn("Error: {}" + ex.getMessage());

        var error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), //404 - HTTP status code
                "NOT FOUND: Attempted to access a non-existent resource -> {}" + ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException ex){

        logger.warn("Error: {}" + ex.getMessage());

        var error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Database conflict: The operation could not be completed due to data constraints.",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){

        logger.warn("INVALID INPUT: Malformed JSON -> {}" + ex.getMessage());

        var error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),//ou só colocar 400
                "Malformed JSON request: Please verify your syntax, data types, and date formats (YYYY-MM-DD)",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}


