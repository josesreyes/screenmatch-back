package com.jsrdev.screenmatch.mapper;

import com.jsrdev.screenmatch.dto.EpisodeDto;
import com.jsrdev.screenmatch.model.Episode;

public class EpisodeMapper {
    public static EpisodeDto toEpisodeDto(Episode e) {
        return new EpisodeDto(
                e.getId(),
                e.getSeason(),
                e.getTitle(),
                e.getEpisode(),
                e.getRating(),
                e.getReleaseDate()
        );
    }
}
