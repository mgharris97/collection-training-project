package lv.bootcamp.shelter.service.data;

import lv.bootcamp.shelter.model.Animal;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record ShelterReportData(ImportResult importResult, Set<String> uniqueSpecies,
                                Map<String, List<Animal>> animalsBySpecies, List<String> animalsNeedingVetInput) {
    public ShelterReportData(
            ImportResult importResult,
            Set<String> uniqueSpecies,
            Map<String, List<Animal>> animalsBySpecies,
            List<String> animalsNeedingVetInput
    ) {
        this.importResult = importResult;
        this.uniqueSpecies = Set.copyOf(uniqueSpecies);
        this.animalsBySpecies = Map.copyOf(animalsBySpecies);
        this.animalsNeedingVetInput = List.copyOf(animalsNeedingVetInput);
    }
}
