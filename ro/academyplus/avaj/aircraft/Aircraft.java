package ro.academyplus.avaj.aircraft;

import ro.academyplus.avaj.util.Logger;

public abstract class Aircraft extends Flyable {
    protected long id;
    protected String name;
    protected Coordinates coordinates;

    protected Aircraft(long id, String name, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
    }

    // additional method. Returns the full identifier of the aircraft
    protected void checkCoordinates() {
        if (this.coordinates.getHeight() <= 0) {
            this.weatherTower.unregister(this);
        }
    }

    // additional method. Get weather modifiers and responses for the specific aircraft type
    protected abstract int[][] getWeatherModifiers();
    // additional method. Get weather responses for the specific aircraft type
    protected abstract String[] getWeatherResponses();

    // additional method. Update the coordinates based on the current weather
    protected void updateCoordinates() {
        String weather = this.weatherTower.getWeather(this.coordinates);
        int [][] weatherModifiers = getWeatherModifiers();
        String [] weatherResponses = getWeatherResponses();
        switch (weather) {
            case "SUN":
                int[] sunModifiers = weatherModifiers[0];
                this.coordinates.modifyCoordinates(sunModifiers[0], sunModifiers[1], sunModifiers[2]);
                Logger.logWeatherChange(this.getFullIdentifier() + ": " + weatherResponses[0]);
                break;
            case "RAIN":
                int[] rainModifiers = weatherModifiers[1];
                this.coordinates.modifyCoordinates(rainModifiers[0], rainModifiers[1], rainModifiers[2]);
                Logger.logWeatherChange(this.getFullIdentifier() + ": " + weatherResponses[1]);
                break;
            case "FOG":
                int[] fogModifiers = weatherModifiers[2];
                this.coordinates.modifyCoordinates(fogModifiers[0], fogModifiers[1], fogModifiers[2]);
                Logger.logWeatherChange(this.getFullIdentifier() + ": " + weatherResponses[2]);
                break;
            case "SNOW":
                int[] snowModifiers = weatherModifiers[3];
                this.coordinates.modifyCoordinates(snowModifiers[0], snowModifiers[1], snowModifiers[2]);
                Logger.logWeatherChange(this.getFullIdentifier() + ": " + weatherResponses[3]);
                break;
        }
        checkCoordinates();
    }
}
