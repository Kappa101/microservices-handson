package com.kappa.microservices.collectionsDemo.functionalDP;

public class Persons {

    private final String name;
    private final int weight;
    private final String height;

    public Persons(String name, int weight, String height) {
        this.name = name;
        this.weight = weight;
        this.height = height;
    }


    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Persons{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", height='" + height + '\'' +
                '}';
    }
}
