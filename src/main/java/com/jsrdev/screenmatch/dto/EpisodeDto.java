package com.jsrdev.screenmatch.dto;

import java.time.LocalDate;

public record EpisodeDto(
        Long id,
        Integer season,
        String title,
        Integer episode,
        Double rating,
        LocalDate releaseDate
) {
}
