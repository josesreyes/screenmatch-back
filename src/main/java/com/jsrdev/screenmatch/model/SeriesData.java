package com.jsrdev.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(
        @JsonAlias("Title") String title,
        @JsonAlias("Year") String year,
        @JsonAlias("Rated") String rated,
        @JsonAlias("Released") String released,
        @JsonAlias("Runtime") String runtime,
        @JsonAlias("Genre") String genre,
        @JsonAlias("Actors") String actors,
        @JsonAlias("Plot") String synopsis,
        @JsonAlias("Poster") String poster,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Type") String type,
        @JsonAlias("totalSeasons") String totalSeasons
) {
}
