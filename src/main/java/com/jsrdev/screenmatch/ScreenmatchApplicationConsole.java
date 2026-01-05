package com.jsrdev.screenmatch;

import com.jsrdev.screenmatch.main.Main;
import com.jsrdev.screenmatch.repository.SeriesRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@SpringBootApplication
public class ScreenmatchApplicationConsole implements CommandLineRunner {

    private final SeriesRepository seriesRepository;

    public ScreenmatchApplicationConsole(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplicationConsole.class, args);
    }

    @Override
    public void run(String @NonNull ... args) {
        new Main(seriesRepository).showMenu();
    }
}*/
