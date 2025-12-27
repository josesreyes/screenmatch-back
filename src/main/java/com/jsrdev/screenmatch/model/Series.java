package com.jsrdev.screenmatch.model;

import com.jsrdev.screenmatch.service.GeminiAIService;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String actors;
    private String synopsis;
    private String poster;
    private Double rating;
    private String type;
    private Integer totalSeasons;
    @Transient
    private List<Episode> episodes;

    public Series(SeriesData seriesData) {
        this.title = seriesData.title();
        this.year = seriesData.year();
        this.rated = seriesData.rated();
        this.released = seriesData.released();
        this.runtime = seriesData.runtime();
        this.genre = Genre.fromString(seriesData.genre().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.synopsis = GeminiAIService.getTranslation(seriesData.synopsis());
        this.poster = seriesData.poster();
        this.rating = OptionalDouble.of(Double.parseDouble(seriesData.rating())).orElse(0);
        this.type = seriesData.type();
        this.totalSeasons = OptionalInt.of(Integer.parseInt(seriesData.totalSeasons())).orElse(0);
    }

    public Series() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getActors() {
        return actors;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPoster() {
        return poster;
    }

    public Double getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", rated='" + rated + '\'' +
                ", released='" + released + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre=" + genre +
                ", actors='" + actors + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", poster='" + poster + '\'' +
                ", rating=" + rating +
                ", type='" + type + '\'' +
                ", totalSeasons=" + totalSeasons;
    }
}
