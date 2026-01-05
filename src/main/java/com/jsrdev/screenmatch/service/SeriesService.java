package com.jsrdev.screenmatch.service;

import com.jsrdev.screenmatch.dto.SeriesDto;
import com.jsrdev.screenmatch.mapper.SeriesMapper;
import com.jsrdev.screenmatch.model.Series;
import com.jsrdev.screenmatch.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public List<SeriesDto> getAll() {
        return getSeriesDto(seriesRepository.findAll());
    }

    public List<SeriesDto> getTop5Series() {
        return getSeriesDto(seriesRepository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDto> getReleases() {
        return null;
    }

    private List<SeriesDto> getSeriesDto(List<Series> series) {
        return series.stream()
                .map(SeriesMapper::toSeriesDto)
                .collect(Collectors.toList());
    }
}
