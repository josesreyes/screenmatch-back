package com.jsrdev.screenmatch.repository;

import com.jsrdev.screenmatch.model.Genre;
import com.jsrdev.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    Series findByTitleIgnoreCase(String title);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Genre genre);
}
