package com.jsrdev.screenmatch.model;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Optional;

public enum Genre {
    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    CRIMEN("Crime", "Crimen"),
    DRAMA("Drama", "Drama");

    private final String genreOmdb;
    private final String genreEsp;

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

    public static Optional<Genre> fromEspSafe(String text) {
        String normalizedText = normalize(text);
        return Arrays.stream(Genre.values())
                .filter(g -> normalize(g.genreEsp).equals(normalizedText))
                .findFirst();
    }

    private static String normalize(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "").toLowerCase();
    }
}
