package com.example.urstudyguide_migration.Common.Exceptions;

public class NonCorrectAnswerSelectedException extends Exception {
    public NonCorrectAnswerSelectedException() {
        super("No se selecciono ninguna respuesta correcta!");
    }
}
