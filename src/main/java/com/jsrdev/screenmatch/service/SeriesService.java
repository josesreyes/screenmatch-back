package com.jsrdev.screenmatch.service;

import com.jsrdev.screenmatch.dto.EpisodeDto;
import com.jsrdev.screenmatch.dto.SeriesDto;
import com.jsrdev.screenmatch.mapper.EpisodeMapper;
import com.jsrdev.screenmatch.mapper.SeriesMapper;
import com.jsrdev.screenmatch.model.Episode;
import com.jsrdev.screenmatch.model.Genre;
import com.jsrdev.screenmatch.model.Series;
import com.jsrdev.screenmatch.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public List<SeriesDto> getAll() {
        return getSeriesDtoList(seriesRepository.findAll());
    }

    public List<SeriesDto> getTop5Series() {
        return getSeriesDtoList(seriesRepository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDto> getReleases() {
        return getSeriesDtoList(seriesRepository.releases());
    }

    public SeriesDto getSeriesById(Long id) {
        return seriesRepository.findById(id)
                .map(this::getSeriesDto)
                .orElse(null);
    }

    private List<SeriesDto> getSeriesDtoList(List<Series> series) {
        return series.stream()
                .map(this::getSeriesDto)
                .collect(Collectors.toList());
    }

    private SeriesDto getSeriesDto(Series s) {
        return SeriesMapper.toSeriesDto(s);
    }

    public List<EpisodeDto> getAllSeasons(Long id) {
        return seriesRepository.findById(id)
                .map(s -> getEpisodeDtoList(s.getEpisodes()))
                .orElse(null);
    }

    public List<EpisodeDto> getEpisodesBySeason(Long id, Long season) {
        return getEpisodeDtoList(seriesRepository.getEpisodesBySeason(id, season));
    }

    private List<EpisodeDto> getEpisodeDtoList(List<Episode> episodes) {
        return episodes.stream()
                .map(this::getEpisodeDto)
                .collect(Collectors.toList());
    }

    private EpisodeDto getEpisodeDto(Episode episode) {
        return EpisodeMapper.toEpisodeDto(episode);
    }

    public List<SeriesDto> getSeriesByGenre(String genre) {
        return Genre.fromEspSafe(genre)
                .map(g -> getSeriesDtoList(seriesRepository.findByGenre(g)))
                .orElse(Collections.emptyList());
    }
}
