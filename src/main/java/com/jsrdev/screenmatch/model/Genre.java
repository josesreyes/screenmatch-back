package com.jsrdev.screenmatch.model;

public enum Genre {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    CRIMEN("Crime"),
    DRAMA("Drama");

    private String genreOmdb;

    Genre(String genreOmdb) {
        this.genreOmdb = genreOmdb;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreOmdb.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Genre not found: " + text);
    }
}
