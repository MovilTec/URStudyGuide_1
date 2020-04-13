package com.example.urstudyguide_migration.Common.Exceptions;

public class InvalidTestQuestion extends Exception {
    public InvalidTestQuestion() {
        super("No se ingreso la pregunta!");
    }
}
