package com.kappa.microservices.collectionsDemo.functionalDP;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Filters {

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {

        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (t != null && predicate.test(t)) {
                result.add(t);
            }
        }

        return result;
    }

    public static List<Persons> filterByHeight(List<Persons> persons, String type) {

        if (persons == null || type == null) {
            throw new IllegalArgumentException("Persons/Height cannot be null");
        }

        if (persons.isEmpty() || type.isEmpty()) {
            return persons;
        }

        List<Persons> result = new ArrayList<>();
        for (Persons person : persons) {
            if (person != null && type.equalsIgnoreCase(person.getHeight())) {
                result.add(person);
            }
        }

        return result;
    }


    public static List<Persons> filterByWeight(
            List<Persons> melons, int weight) {

        List<Persons> result = new ArrayList<>();

        for (Persons person: melons) {
            if (person != null && person.getWeight() == weight) {
                result.add(person);
            }
        }

        return result;
    }

}
