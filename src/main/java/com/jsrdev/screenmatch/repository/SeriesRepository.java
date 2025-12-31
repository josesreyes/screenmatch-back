package com.jsrdev.screenmatch.repository;

import com.jsrdev.screenmatch.model.Genre;
import com.jsrdev.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    //jpa

    Series findByTitleIgnoreCase(String title);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Genre genre);

    List<Series> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(int totalTemporadas, Double evaluacion);

    // native queries
    @Query(value = "SELECT * FROM series WHERE series.total_seasons <= 6 AND series.rating >= 7.5", nativeQuery = true)
    List<Series> seriesBySeasonAndRatingsNativeQueries();

    // jpql
    @Query("SELECT s FROM Series s WHERE s.totalSeasons <= :totalSeason AND s.rating >= :rating")
    List<Series> seriesBySeasonAndRating(int totalSeason, double rating);
}
