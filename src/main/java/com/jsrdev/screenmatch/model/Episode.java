package com.jsrdev.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer season;
    private String title;
    private Integer episode;
    private Double rating;
    private LocalDate releaseDate;
    @ManyToOne @JoinColumn(name = "series_id")
    private Series series;

    public Episode(Integer season, EpisodeData ed) {
        this.season = season;
        this.title = ed.title();
        this.episode = Integer.valueOf(ed.episode());
        this.rating = (ed.rating().contains("N/A")) ? 0.0 : Double.parseDouble(ed.rating());
        this.releaseDate = ed.releaseDate().equals("N/A") ? null : LocalDate.parse(ed.releaseDate());
    }

    public Episode() {}

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
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
