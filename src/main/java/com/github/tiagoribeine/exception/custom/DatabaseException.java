package com.github.tiagoribeine.exception.custom;

/*
Exceção de quando se deletar um Autor que ainda possui Livros vinculados a ele. O MySQL vai barrar a operação para não deixar "livros órfãos".
*/

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
