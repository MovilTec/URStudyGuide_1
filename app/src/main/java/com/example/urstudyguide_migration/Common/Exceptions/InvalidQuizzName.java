package com.example.urstudyguide_migration.Common.Exceptions;

public class InvalidQuizzName extends Exception {
    public InvalidQuizzName() {
        super("No se ha ingresado el nombre del cuestionario!");
    }
}
