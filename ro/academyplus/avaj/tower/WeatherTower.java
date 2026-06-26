package ro.academyplus.avaj.tower;

import ro.academyplus.avaj.aircraft.Coordinates;
import ro.academyplus.avaj.weather.WeatherProvider;

public class WeatherTower extends Tower {
    public String getWeather(Coordinates coordinates) {
        return WeatherProvider.getProvider().getCurrentWeather(coordinates);
    }
    public void changeWeather() {
        this.conditionsChanged();
    }
}
