package lv.bootcamp.shelter.service;
import lombok.extern.slf4j.Slf4j;
import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvImportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ImportResult importAnimals(Path inputPath) {
        log.info("Starting import from {}", inputPath);

        List<Animal> allAnimals = new ArrayList<>();
        int skippedRows = 0;

        try (BufferedReader br = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line;
            int rowNumber = 0;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                rowNumber++;

                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // skip header row
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(",", -1);

                if (fields.length != 5) {
                    log.warn("Row {} skipped: expected 5 columns but found {} -> '{}'",
                            rowNumber, fields.length, line);
                    skippedRows++;
                    continue;
                }

                String name = fields[0].trim();
                String species = fields[1].trim();
                String ageRaw = fields[2].trim();
                String vaccinatedRaw = fields[3].trim();
                String dateRaw = fields[4].trim();

                if (name.isEmpty() || species.isEmpty()) {
                    log.warn("Row {} skipped: name and species are required -> '{}'", rowNumber, line);
                    skippedRows++;
                    continue;
                }

                Integer age;
                if (ageRaw.isEmpty()) {
                    age = null; // blank age accepted as unknown
                } else {
                    try {
                        age = Integer.parseInt(ageRaw);
                    } catch (NumberFormatException e) {
                        log.warn("Row {} skipped: non-numeric age '{}' -> '{}'", rowNumber, ageRaw, line);
                        skippedRows++;
                        continue;
                    }
                }

                boolean vaccinated = Boolean.parseBoolean(vaccinatedRaw); // blank -> false

                LocalDate intakeDate;
                try {
                    intakeDate = LocalDate.parse(dateRaw, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    log.warn("Row {} skipped: invalid intake date '{}' -> '{}'", rowNumber, dateRaw, line);
                    skippedRows++;
                    continue;
                }

                allAnimals.add(new Animal(name, species, age, vaccinated, intakeDate));
            }
        } catch (IOException e) {
            log.error("Failed to read intake file {}", inputPath, e);
        }

        log.info("Import finished: {} imported, {} skipped", allAnimals.size(), skippedRows);
        return new ImportResult(allAnimals, skippedRows);
    }
}
