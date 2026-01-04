package com.jsrdev.screenmatch.mapper;

import com.jsrdev.screenmatch.dto.SeriesDto;
import com.jsrdev.screenmatch.model.Series;

public class SeriesMapper {
    public static SeriesDto toSeriesDto(Series s) {
        return new SeriesDto(
                s.getId(),
                s.getTitle(),
                s.getYear(),
                s.getRated(),
                s.getReleased(),
                s.getRuntime(),
                s.getGenre(),
                s.getActors(),
                s.getSynopsis(),
                s.getPoster(),
                s.getRating(),
                s.getType(),
                s.getTotalSeasons()
        );
    }
}
