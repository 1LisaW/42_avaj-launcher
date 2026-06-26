package ro.academyplus.avaj.aircraft;

import ro.academyplus.avaj.exception.InvalidScenarioException;

public class AircraftFactory {
    private static final AircraftFactory instance = new AircraftFactory();
    private long idCounter = 0;

    private AircraftFactory() {
        // Private constructor to prevent instantiation
    }

    public static AircraftFactory getInstance() {
        return instance;
    }

    public Coordinates createCoordinates(int longitude, int latitude, int height) {
        return new Coordinates(longitude, latitude, height);
    }

    public Flyable newAircraft(String p_type, String p_name, Coordinates p_coordinates) {
        String type = p_type.trim();
        long id = ++idCounter; // Generate a unique ID

        switch (type) {
            case "Helicopter":
                return new Helicopter(id, p_name, p_coordinates);
            case "JetPlane":
                return new JetPlane(id, p_name, p_coordinates);
            case "Balloon":
                return new Balloon(id, p_name, p_coordinates);
            default:
                throw new InvalidScenarioException("Unknown aircraft type: " + type);
        }
    }
}
