package com.jsrdev.screenmatch.controller;

import com.jsrdev.screenmatch.dto.SeriesDto;
import com.jsrdev.screenmatch.mapper.SeriesMapper;
import com.jsrdev.screenmatch.model.Series;
import com.jsrdev.screenmatch.repository.SeriesRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("series")
public class SeriesController {

    private final SeriesRepository seriesRepository;

    public SeriesController(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @GetMapping
    public List<SeriesDto> getAll() {
        return seriesRepository.findAll().stream()
                .map(SeriesMapper::toSeriesDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/top5")
    public List<SeriesDto> getTop5Series() {
        return seriesRepository.findTop5ByOrderByRatingDesc().stream()
                .map(SeriesMapper::toSeriesDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/releases")
    public List<SeriesDto> getReleases() {
        return seriesRepository.findAll().stream()
                .map(SeriesMapper::toSeriesDto)
                .collect(Collectors.toList());
    }
}
