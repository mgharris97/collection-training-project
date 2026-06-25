package lv.bootcamp.shelter.model;

import java.time.LocalDate;

public class Animal {
    private final String name;
    private final String species;
    private final Integer age;
    private final boolean vaccinated;
    private final LocalDate intakeDate;

    public Animal(String name, String species, Integer age, boolean vaccinated, LocalDate intakeDate) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.vaccinated = vaccinated;
        this.intakeDate = intakeDate;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public Integer getAge() {
        return age;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public LocalDate getIntakeDate() {
        return intakeDate;
    }
}
