package lv.bootcamp.shelter.service;

import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ShelterReportData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportExportService {

    private static final DateTimeFormatter REPORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public void writeReport(Path outputPath, ShelterReportData reportData) {
        try {
            if (outputPath.getParent() != null) {
                Files.createDirectories(outputPath.getParent());
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not create output directory for " + outputPath, e);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write("Shelter Intake Report");
            writer.newLine();
            writer.write("Generated: " + LocalDate.now().format(REPORT_DATE_FORMATTER));
            writer.newLine();
            writer.newLine();
            writer.write("Total imported: " + reportData.importResult().allAnimals().size());
            writer.newLine();
            writer.write("Total skipped: " + reportData.importResult().skippedRows());
            writer.newLine();
            writer.newLine();
            writer.write("Unique species: " + String.join(", ", reportData.uniqueSpecies()));
            writer.newLine();
            writer.newLine();
            writer.write("Per-species breakdown:");
            writer.newLine();
            for (String species : reportData.uniqueSpecies()) {
                List<Animal> animals = reportData.animalsBySpecies().getOrDefault(species, List.of());
                long vaccinatedCount = reportData.vaccinatedCountBySpecies().getOrDefault(species, 0L);
                writer.write("  " + species + ": total=" + animals.size() + ", vaccinated=" + vaccinatedCount);
                writer.newLine();
            }
            writer.newLine();

            writer.write("Oldest animal per species:");
            writer.newLine();
            for (String species : reportData.uniqueSpecies()) {
                Animal oldest = reportData.oldestAnimalBySpecies().get(species);
                if (oldest != null) {
                    writer.write("  " + species + ": " + oldest.getName() + " (age " + oldest.getAge() + ")");
                } else {
                    writer.write("  " + species + ": no age data available");
                }
                writer.newLine();
            }
            writer.newLine();

            writer.write("Needs vet input: " + String.join(", ", reportData.animalsNeedingVetInput()));
            writer.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write report to " + outputPath, e);
        }
    }
}