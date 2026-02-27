package com.github.tiagoribeine.exception;

import java.time.LocalDateTime;

public record ErrorResponse( //Imutável, limpo e otimizado para transferir dados (DTO)
        int status,
        String message,
        LocalDateTime timestamp
) {}
