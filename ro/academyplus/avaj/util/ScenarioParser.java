package ro.academyplus.avaj.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ro.academyplus.avaj.aircraft.AircraftFactory;
import ro.academyplus.avaj.aircraft.Coordinates;
import ro.academyplus.avaj.aircraft.Flyable;
import ro.academyplus.avaj.exception.InvalidScenarioException;

public class ScenarioParser {
    private static final ScenarioParser instance = new ScenarioParser();
    private static final Pattern SCENARIO_PATTERN = Pattern.compile(
            "^(Helicopter|JetPlane|Balloon)\\s+\\w+\\s+-?\\d+\\s+-?\\d+\\s+\\d+$");

    private ScenarioParser() {
    }

    public static ScenarioParser getInstance() {
        return instance;
    }

    public static final class ParsedScenario {
        private final int iterations;
        private final List<Flyable> flyables;

        ParsedScenario(int iterations, List<Flyable> flyables) {
            this.iterations = iterations;
            this.flyables = flyables;
        }

        public int getIterations() {
            return iterations;
        }

        public List<Flyable> getFlyables() {
            return flyables;
        }
    }

    public ParsedScenario parse(String scenarioFile) throws IOException, InvalidScenarioException {
        Path path = Paths.get(scenarioFile);
        if (!Files.isRegularFile(path)) {
            throw new IOException("Error: Scenario file not found.");
        }

        List<String> lines = Files.readAllLines(path);
        if (lines.isEmpty()) {
            throw new InvalidScenarioException("Scenario file is empty.");
        }

        String firstLine = lines.get(0).trim();
        int iterations;
        try {
            iterations = Integer.parseInt(firstLine);
        } catch (NumberFormatException e) {
            throw new InvalidScenarioException("Invalid number of iterations: " + firstLine);
        }
        if (iterations < 0) {
            throw new InvalidScenarioException("Invalid number of iterations: " + firstLine);
        }

        List<Flyable> flyables = new ArrayList<>();
        AircraftFactory aircraftFactory = AircraftFactory.getInstance();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            if (!SCENARIO_PATTERN.matcher(line).matches()) {
                throw new InvalidScenarioException(
                        "Invalid scenario line <TYPE NAME LONGITUDE LATITUDE HEIGHT>: " + line);
            }
            flyables.add(createAircraftFromLine(line, aircraftFactory));
        }

        return new ParsedScenario(iterations, flyables);
    }

    private Flyable createAircraftFromLine(String line, AircraftFactory aircraftFactory)
            throws InvalidScenarioException {
        String[] parts = line.split("\\s+");
        String type = parts[0];
        String name = parts[1];
        int longitude;
        int latitude;
        int height;
        try {
            longitude = Integer.parseInt(parts[2]);
            latitude = Integer.parseInt(parts[3]);
            height = Integer.parseInt(parts[4]);
        } catch (NumberFormatException e) {
            throw new InvalidScenarioException("Invalid coordinates in scenario line: " + line);
        }
        if (height < 0 || height > 100) {
            throw new InvalidScenarioException("Height must be between 0 and 100: " + line);
        }
        Coordinates coordinates = aircraftFactory.createCoordinates(longitude, latitude, height);
        return aircraftFactory.newAircraft(type, name, coordinates);
    }
}
