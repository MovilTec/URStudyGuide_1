package com.example.urstudyguide_migration.Common.Exceptions;

public class EmptyQuizzException extends Exception {
    public EmptyQuizzException() {
        super("No se ha creado ninguna pregunta para la guía");
    }
}
