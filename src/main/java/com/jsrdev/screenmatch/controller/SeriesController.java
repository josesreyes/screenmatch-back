package com.jsrdev.screenmatch.controller;

import com.jsrdev.screenmatch.dto.SeriesDto;
import com.jsrdev.screenmatch.service.SeriesService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
