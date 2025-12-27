package com.jsrdev.screenmatch.main;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExampleStreams {
    public void showStream() {
        List<String> names = Arrays.asList("José", "Brenda", "Erika", "Luis", "Julieta", "Javier");

        names.stream()
                .sorted()
                .filter(n -> n.startsWith("j"))
                .limit(2)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // numeros pares
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> numerosPares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println(numerosPares); // Salida: [2, 4, 6, 8, 10]

        // transforma los elementos
        List<String> palabras = Arrays.asList("Java", "Stream", "Operaciones", "Intermedias");

        List<Integer> tamanos = palabras.stream()
                .map(String::length)
                .collect(Collectors.toList());

        System.out.println(tamanos); // Salida: [4, 6, 11, 11]

        // num pares en un conjunto
        List<Integer> numeros2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Set<Integer> numerosPares2 = numeros2.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toSet());

        System.out.println(numerosPares2); // Salida: [2, 4, 6, 8, 10]
    }
}
