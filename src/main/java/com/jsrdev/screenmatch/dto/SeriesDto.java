package com.jsrdev.screenmatch.dto;

import com.jsrdev.screenmatch.model.Genre;

public record SeriesDto(
        Long id,
        String title,
        String year,
        String rated,
        String released,
        String runtime,
        Genre genre,
        String actors,
        String synopsis,
        String poster,
        Double rating,
        String type,
        Integer totalSeasons
) {
}
