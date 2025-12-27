package com.jsrdev.screenmatch.model;

import java.time.LocalDate;

public class Episode {
    private Integer season;
    private String title;
    private Integer episode;
    private Double rating;
    private LocalDate releaseDate;

    public Episode(Integer season, EpisodeData ed) {
        this.season = season;
        this.title = ed.title();
        this.episode = Integer.valueOf(ed.episode());
        this.rating = (ed.rating().contains("N/A")) ? 0.0 : Double.parseDouble(ed.rating());
        this.releaseDate = ed.releaseDate().equals("N/A") ? null : LocalDate.parse(ed.releaseDate());
    }

    public Integer getSeason() {
        return season;
    }

    public String getTitle() {
        return title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public double getRating() {
        return rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "season=" + season +
                ", title='" + title +
                ", episode=" + episode +
                ", rating=" + rating +
                ", releaseDate=" + releaseDate;
    }
}
