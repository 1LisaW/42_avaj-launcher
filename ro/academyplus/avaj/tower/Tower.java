package ro.academyplus.avaj.tower;

import java.util.ArrayList;
import java.util.List;

import ro.academyplus.avaj.aircraft.Flyable;
import ro.academyplus.avaj.util.Logger;

public class Tower {
    private List<Flyable> observers = new ArrayList<Flyable>();
    public void register(Flyable flyable) {
        observers.add(flyable);
        Logger.logRegistration(flyable.getFullIdentifier());
    }
    public void unregister(Flyable flyable) {
        observers.remove(flyable);
        Logger.logLanding(flyable.getFullIdentifier());
    }
    protected void conditionsChanged() {
        for (Flyable flyable : new java.util.ArrayList<>(observers)) {
            flyable.updateConditions();
        }
    }
}
