package lv.bootcamp.shelter.service.data;

import lv.bootcamp.shelter.model.Animal;

import java.util.List;

public record ImportResult(List<Animal> allAnimals, int skippedRows, List<Integer> invalidRowNumbers) {

    public ImportResult(List<Animal> allAnimals, int skippedRows, List<Integer> invalidRowNumbers) {
        this.allAnimals = List.copyOf(allAnimals);
        this.skippedRows = skippedRows;
        this.invalidRowNumbers = List.copyOf(invalidRowNumbers);
    }

}
