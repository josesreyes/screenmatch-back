package com.jsrdev.screenmatch.model;

import java.text.Normalizer;

public enum Genre {
    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    CRIMEN("Crime", "Crimen"),
    DRAMA("Drama", "Drama");

    private String genreOmdb;
    private String genreEsp;

    Genre(String genreOmdb, String genreEsp) {
        this.genreOmdb = genreOmdb;
        this.genreEsp = genreEsp;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreOmdb.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Genre not found: " + text);
    }

    public static Genre fromEsp(String text) {
        String normalizedText = normalize(text);
        for (Genre genre : Genre.values()) {
            if (normalize(genre.genreEsp).equals(normalizedText)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Genre not found: " + text);
    }

    private static String normalize(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "").toLowerCase();
    }
}
