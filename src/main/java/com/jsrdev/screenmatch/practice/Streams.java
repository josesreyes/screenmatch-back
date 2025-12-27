package com.jsrdev.screenmatch.practice;

import java.util.List;
import java.util.stream.Collectors;

public class Streams {
    public void practiceStreams() {
        List<String> employees = List.of("Ana", "Juan", "Albert", "Maria");

        List<String> employeesStartWithLetterA = employees.stream()
                .filter(e -> e.startsWith("A"))
                .collect(Collectors.toList());

        List<Double> ventas = List.of(200.0, 4000.0, 50.0);
        List<Double> comision = ventas.stream()
                .map(v -> v * 0.05)
                .collect(Collectors.toList());

        List<Double> comisionFilter = ventas.stream()
                .map(v -> v * 0.05)
                .filter(c -> c > 2.0)
                .collect(Collectors.toList());
    }
}
