package com.example.urstudyguide_migration.Common.Exceptions;

public class InvalidAnswerQuestion extends Exception {
    public InvalidAnswerQuestion() {
        super("No se ingreso el texto de la respuesta!");
    }
}
