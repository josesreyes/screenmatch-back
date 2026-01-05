package com.jsrdev.screenmatch.controller;

import com.jsrdev.screenmatch.dto.EpisodeDto;
import com.jsrdev.screenmatch.dto.SeriesDto;
import com.jsrdev.screenmatch.service.SeriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping
    public List<SeriesDto> getAll() {
        return seriesService.getAll();
    }

    @GetMapping("/top5")
    public List<SeriesDto> getTop5Series() {
        return seriesService.getTop5Series();
    }

    @GetMapping("/releases")
    public List<SeriesDto> getReleases() {
        return seriesService.getReleases();
    }

    @GetMapping("/{id}")
    public SeriesDto getSeriesById(@PathVariable Long id) {
        return seriesService.getSeriesById(id);
    }

    @GetMapping("/{id}/seasons/all")
    public List<EpisodeDto> getAllSeasons(@PathVariable Long id) {
        return seriesService.getAllSeasons(id);
    }

    @GetMapping("/{id}/seasons/{season}")
    public List<EpisodeDto> getEpisodesBySeason(@PathVariable Long id, @PathVariable Long season) {
        return seriesService.getEpisodesBySeason(id, season);
    }

    @GetMapping("/genre/{genre}")
    public List<SeriesDto> getSeriesByGenre(@PathVariable String genre) {
        return seriesService.getSeriesByGenre(genre);
    }

    @GetMapping("/{id}/seasons/top5Episodes")
    public List<EpisodeDto> getTop5Episodes(@PathVariable Long id) {
        return seriesService.getTop5Episodes(id);
    }
}
