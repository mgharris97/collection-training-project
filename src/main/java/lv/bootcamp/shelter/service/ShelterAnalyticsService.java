package lv.bootcamp.shelter.service;

import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;
import lv.bootcamp.shelter.service.data.ShelterReportData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShelterAnalyticsService {

    public ShelterReportData buildReportData(ImportResult importResult) {
        List<Animal> allAnimals = importResult.allAnimals();

        Set<String> uniqueSpecies = new HashSet<>();
        Map<String, List<Animal>> animalsBySpecies = new HashMap<>();
        List<String> animalsNeedingVetInput = new ArrayList<>();

        // TODO Step 2:
        // Fill all collections:
        // - allAnimals (already available from import)
        // - uniqueSpecies
        // - animalsBySpecies
        // - animalsNeedingVetInput with format name(species)

        // TODO Step 3:
        // Use stream pipelines for:
        // - sorting by intake date
        // - vaccinated vs unvaccinated counts per species
        // - oldest animal per species (excluding unknown ages)

        return new ShelterReportData(importResult, uniqueSpecies, animalsBySpecies, animalsNeedingVetInput);
    }
}
