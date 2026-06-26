package ro.academyplus.avaj.aircraft;

public class Helicopter extends Aircraft {
    private static final int[][] WEATHER_MODIFIERS = {
            {10, 0, 2},   // SUN
            {5, 0, 0},  // RAIN
            {1, 0, 0},  // FOG
            {0, 0, -12}  // SNOW
    };
    private static final String[] WEATHER_RESPONSES = {
            "🚁 + ☀ Smooth flying today. The rotors approve.",
            "🚁 + 🌧 Rain detected. Switching to submarine mode... oh wait.",
            "🚁 + 🌫 Visibility is optional, right?",
            "🚁 + ❄ I wanted a helicopter, not a flying snow shovel."
    };

    public Helicopter(long p_id, String p_name, Coordinates p_coordinates) {
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
        return "Helicopter#" + this.name + "(" + this.id + ")";
    }
}
