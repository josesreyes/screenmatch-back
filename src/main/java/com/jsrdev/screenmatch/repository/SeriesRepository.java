package com.jsrdev.screenmatch.repository;

import com.jsrdev.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    Series findByTitleIgnoreCase(String title);
}
