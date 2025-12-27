package com.jsrdev.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") String episode,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String releaseDate,
        @JsonAlias("Season") String season,
        @JsonAlias("Year") String year,
        @JsonAlias("Runtime") String runtime
) {
}
