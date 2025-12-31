package com.jsrdev.screenmatch.main;

import com.jsrdev.screenmatch.model.*;
import com.jsrdev.screenmatch.repository.SeriesRepository;
import com.jsrdev.screenmatch.service.ApiClient;
import com.jsrdev.screenmatch.service.ConvertData;
import com.jsrdev.screenmatch.utils.Env;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final Scanner scanner = new Scanner(System.in);

    private final SeriesRepository repository;

    List<Series> series = new ArrayList<>();

    Series searchedSeries;

    public Main(SeriesRepository seriesRepository) {
        this.repository = seriesRepository;
    }

    public void showMenu() {
        String menu = """
                \n=== SERIES MENU ===
                Enter an option:
                1 - Search for a series
                2 - Search for episodes
                3.- Show searched series
                4.- Search series by title
                5.- Find the top 5 series by rating
                6.- Search series by genre
                7.- Filter series by season and rating
                8.- Search episodes by title
                9.- Top 5 episodes by series
                
                0 - Exit
                """;

        while (true) {
            System.out.println(menu);
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> handleSeriesSearch();
                case "2" -> handleEpisodeSearch();
                case "3" -> showSearchedSeries();
                case "4" -> searchSeriesByTitle();
                case "5" -> searchTop5SeriesByRating();
                case "6" -> searchSeriesByGenre();
                case "7" -> filterSeriesBySeasonAndRatings();
                case "8" -> searchEpisodeByTitleName();
                case "9" -> top5episodesBySeries();
                case "0" -> {
                    System.out.println("Exiting program...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void top5episodesBySeries() {
        searchSeriesByTitle();
        if (searchedSeries == null) {
            System.out.println("No series selected.");
            return;
        }
        List<Episode> episodes = repository.top5EpisodesBySeries(searchedSeries, 5);
        episodes.forEach(System.out::println);
    }

    private void searchEpisodeByTitleName() {
        System.out.println("Enter episode name: ");
        String episodeName = scanner.nextLine();

        List<Episode> episodes = repository.episodesByTitle(episodeName);
        episodes.forEach( e -> System.out.printf("\nSeries: %s, Season: %s, Episode: %s - %s, Rating: %s",
                e.getSeries().getTitle(),
                e.getSeason(),
                e.getTitle(),
                e.getEpisode(),
                e.getRating()));
    }

    private void filterSeriesBySeasonAndRatings() {
        System.out.println("\nFilter series with how many seasons? ");
        var totalSeasons = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\nEvaluation based on which value? ");
        var rating = scanner.nextDouble();
        scanner.nextLine();
        //List<Series> filteredSeries = repository.findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(totalSeasons, rating);
        List<Series> filteredSeries = repository.seriesBySeasonAndRating(totalSeasons, rating);
        System.out.println("*** Filtered Series ***");
        filteredSeries.forEach(s ->
                System.out.println(s.getTitle() + "  - rating: " + s.getRating()));
    }

    private void searchSeriesByGenre() {
        String entryGenre = readSeriesName("Enter the genre of the series you want to search for.");
        try {
            Genre genre = Genre.fromEsp(entryGenre);

            repository.findByGenre(genre).stream()
                    .forEachOrdered(System.out::println);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void searchTop5SeriesByRating() {
        repository.findTop5ByOrderByRatingDesc().stream()
                .forEach(System.out::println);
    }

    private void searchSeriesByTitle() {
        String seriesTitle = readValidSeriesName("Enter the name of the series you want to watch:");

        searchedSeries = repository.findByTitleIgnoreCase(seriesTitle);

        if (searchedSeries == null) {
            System.out.println("\nSeries not found");
            return;
        }

        System.out.println("\nSeries found: " + searchedSeries);
    }

    private void showSearchedSeries() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }

    // -----------------------------
    // MENU OPTION 1: SERIES SEARCH
    // -----------------------------
    private void handleSeriesSearch() {
        String seriesName = readValidSeriesName("Enter a series name:");

        SeriesData seriesData = fetch(seriesName, Map.of(), SeriesData.class);

        if (seriesData.totalSeasons() == null) {
            System.out.println("\nSeries not found: " + seriesName);
            return;
        }

        if (repository.findByTitleIgnoreCase(seriesName) != null) {
            System.out.println("\nSeries " + seriesName + " already exist in database");
            return;
        }

        Series series = new Series(seriesData);

        repository.save(series);
        System.out.println(series);
    }

    // --------------------------------
    // MENU OPTION 2: EPISODE SEARCH
    // --------------------------------
    private void handleEpisodeSearch() {
        showSearchedSeries();
        String seriesName = readValidSeriesName("Enter the name of the series to watch it's episodes:");

        Optional<Series> optionalSeries = series.stream()
                .filter(s -> s.getTitle().toUpperCase().contains(seriesName.toUpperCase()))
                .findFirst();

        if (optionalSeries.isEmpty()) {
            System.out.println("\nSeries not found: " + seriesName);
            return;
        }

        Series seriesFound = optionalSeries.get();

        List<SeasonData> seasons = loadSeasons(seriesFound);
        seasons.forEach(System.out::println);

        List<Episode> episodes = buildEpisodeList(seasons);
        seriesFound.setEpisodes(episodes);

        repository.save(seriesFound);

        //filterEpisodesByYear(episodes);
        //searchEpisodeByTitle(episodes);
        //showSeasonRatings(episodes);
        //showEpisodeStatistics(episodes);
    }

    private String readValidSeriesName(String message) {
        while (true) {
            String input = readSeriesName(message);

            if (isEmpty(input)) {
                System.out.println("\nSeries name cannot be empty. Please try again.");
                continue;
            }

            return input;
        }
    }

    // -----------------------------
    // DATA LOADING METHODS
    // -----------------------------
    private List<SeasonData> loadSeasons(Series series) {
        List<SeasonData> seasons = new ArrayList<>();

        for (int s = 1; s <= series.getTotalSeasons(); s++) {
            SeasonData seasonData = fetch(
                    series.getTitle(),
                    Map.of("Season", String.valueOf(s)),
                    SeasonData.class
            );
            seasons.add(seasonData);
        }
        return seasons;
    }

    private List<Episode> buildEpisodeList(List<SeasonData> seasons) {
        return seasons.stream()
                .flatMap(s -> s.episodeData().stream()
                        .map(e -> new Episode(s.season(), e)))
                .collect(Collectors.toList());
    }

    // -----------------------------
    // EPISODE OPERATIONS
    // -----------------------------
    private void filterEpisodesByYear(List<Episode> episodes) {
        System.out.println("\nEnter a year to filter episodes:");
        int year = scanner.nextInt();
        scanner.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
                .forEach(e ->
                        System.out.println("Season: " + e.getSeason() +
                                ", Episode: " + e.getEpisode() +
                                ", Title: " + e.getTitle() +
                                ", Release date: " + e.getReleaseDate().format(dtf))
                );
    }

    private void searchEpisodeByTitle(List<Episode> episodes) {
        System.out.println("\nEnter an episode title:");
        String title = scanner.nextLine();

        Optional<Episode> result = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(title.toUpperCase()))
                .findFirst();

        if (result.isPresent()) {
            System.out.println("\nEpisode found: " + result.get());
        } else {
            System.out.println("\nEpisode not found.");
        }
    }

    private void showSeasonRatings(List<Episode> episodes) {
        Map<Integer, Double> ratingBySeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(
                        Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)
                ));

        System.out.println("\nAverage rating by season:");
        System.out.println(ratingBySeason);
    }

    private void showEpisodeStatistics(List<Episode> episodes) {
        DoubleSummaryStatistics stats = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.println("\nEpisode rating statistics:");
        System.out.println(stats);
    }

    // -----------------------------
    // INPUT HELPERS
    // -----------------------------
    private boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    private String readSeriesName(String message) {
        System.out.println("\n" + message);
        return scanner.nextLine();
    }

    // -----------------------------
    // API HELPERS
    // -----------------------------
    private String buildUrl(Map<String, String> params) {
        StringBuilder sb = new StringBuilder(Env.BASE_URL);
        sb.append("?");

        params.forEach((key, value) -> {
            String encoded = URLEncoder.encode(value, StandardCharsets.UTF_8);
            sb.append(key).append("=").append(encoded).append("&");
        });

        sb.append("apikey=").append(Env.OMDB_API_KEY);
        return sb.toString();
    }

    private String fetchJson(String url) {
        return new ApiClient().getData(url);
    }

    private <T> T parseData(String json, Class<T> clazz) {
        return new ConvertData().getData(json, clazz);
    }

    private <T> T fetch(String title, Map<String, String> extraParams, Class<T> clazz) {
        Map<String, String> params = new HashMap<>();
        params.put("t", title);
        params.putAll(extraParams);

        String url = buildUrl(params);
        String json = fetchJson(url);

        return parseData(json, clazz);
    }
}
