package ro.academyplus.avaj.aircraft;

import ro.academyplus.avaj.tower.WeatherTower;

public abstract class Flyable {
    protected WeatherTower weatherTower = null;

    public abstract void updateConditions();

    /**  additional method. Returns the full identifier of the aircraft */
    public abstract String getFullIdentifier();

    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
    }
}
