package com.kappa.microservices.collectionsDemo.functionalDP;

import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Persons> melons = Arrays.asList(
                new Persons("Kappa", 89, "171 cms"), new Persons("Pritam", 100, "1.56 m"),
                new Persons("Balayya", 90, "121 cms"), new Persons("Glen", 3400, "191 cms"),
                new Persons("Spindaru", 101, "1.71 m"));

        List<Persons> kappa = Filters.filterByHeight(melons, "171 cms");
        System.out.println("Kappa: " + kappa);
    }
}
