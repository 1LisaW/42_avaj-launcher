package ro.academyplus.avaj.simulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ro.academyplus.avaj.aircraft.Flyable;
import ro.academyplus.avaj.exception.InvalidArgumentException;
import ro.academyplus.avaj.exception.InvalidScenarioException;
import ro.academyplus.avaj.tower.WeatherTower;
import ro.academyplus.avaj.util.ScenarioParser;
import ro.academyplus.avaj.util.ScenarioParser.ParsedScenario;

public class Simulator {
    private static void run(String scenarioFile) throws IOException, InvalidScenarioException {
        ScenarioParser scenarioParser = ScenarioParser.getInstance();
        ParsedScenario scenario = scenarioParser.parse(scenarioFile);
        WeatherTower weatherTower = new WeatherTower();
        List<Flyable> flyables = scenario.getFlyables();
        for (Flyable flyable : flyables) {
            weatherTower.register(flyable);
            flyable.registerTower(weatherTower);
        }
        for (int i = 0; i < scenario.getIterations(); i++) {
            weatherTower.changeWeather();
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new InvalidArgumentException(
                        "Exactly one argument is required: the path to the scenario file.");
            }
            if (!Files.isRegularFile(Paths.get(args[0]))) {
                throw new InvalidArgumentException("The provided argument is not a valid file path.");
            }
            run(args[0]);
        } catch (InvalidArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
            System.exit(1);
        } catch (InvalidScenarioException e) {
            System.err.println("Invalid scenario: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
