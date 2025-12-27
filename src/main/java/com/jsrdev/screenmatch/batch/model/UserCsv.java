package com.jsrdev.screenmatch.batch.model;

/**
 * Representa una fila del archivo CSV.
 * NO es una entidad JPA.
 * Solo sirve para transportar datos.
 */
public record UserCsv(
        String name,
        String email
) {}

