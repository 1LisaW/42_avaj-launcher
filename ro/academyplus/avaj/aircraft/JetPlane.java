package ro.academyplus.avaj.aircraft;

public class JetPlane extends Aircraft {
    private static final int[][] WEATHER_MODIFIERS = {
            {0, 10, 2},   // SUN
            {0, 5, 0},    // RAIN
            {0, 1, 0},    // FOG
            {0, 0, -7}    // SNOW
    };
    private static final String[] WEATHER_RESPONSES = {
            "✈️ + ☀ Clear skies ahead. Time to pretend I'm a rocket.",
            "✈️ + 🌧 Rain again? My windshield is earning its salary.",
            "✈️ + 🌫 Visibility is terrible. Flying by optimism now.",
            "✈️ + ❄ Snow at this altitude? Someone left the freezer open."
    };

    public JetPlane(long p_id, String p_name, Coordinates p_coordinates) {
        super(p_id, p_name, p_coordinates);
    }

    @Override
    protected int[][] getWeatherModifiers() {
        return WEATHER_MODIFIERS;
    }

    @Override
    protected String[] getWeatherResponses() {
        return WEATHER_RESPONSES;
    }

    @Override
    public void updateConditions() {
        updateCoordinates();
    }

    @Override
    public String getFullIdentifier() {
        return "JetPlane#" + this.name + "(" + this.id + ")";
    }
}
