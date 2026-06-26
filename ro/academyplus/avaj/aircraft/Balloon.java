package ro.academyplus.avaj.aircraft;

public class Balloon extends Aircraft {
    private static final int[][] WEATHER_MODIFIERS = {
            {2, 0, 4},   // SUN
            {0, 0, -5},  // RAIN
            {0, 0, -3},  // FOG
            {0, 0, -15}  // SNOW
    };
    private static final String[] WEATHER_RESPONSES = {
            "🎈 + ☀ Let's enjoy the good weather and take some pics.",
            "🎈 + 🌧 Damn you rain! You messed up my balloon.",
            "🎈 + 🌫 I can't see anything!",
            "🎈 + ❄ It's snowing. We're gonna crash."
    };
    public Balloon(long p_id, String p_name, Coordinates p_coordinates) {
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
        return "Balloon#" + this.name + "(" + this.id + ")";
    }
}
