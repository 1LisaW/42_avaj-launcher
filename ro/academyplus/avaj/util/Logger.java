package ro.academyplus.avaj.util;

public class Logger {
    private static final String LOG_FILE = "simulation.txt";

    private Logger() {
        // Private constructor to prevent instantiation
    }

    static {
        // Clear the log file at the start of the program
        clearLog();
    }

    private static void log(String message) {
        try (java.io.FileWriter writer = new java.io.FileWriter(LOG_FILE, true)) {
            writer.write(message + System.lineSeparator());
        } catch (java.io.IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private static void clearLog() {
        try (java.io.FileWriter writer = new java.io.FileWriter(LOG_FILE)) {
        } catch (java.io.IOException e) {
            System.err.println("Error clearing log file: " + e.getMessage());
        }
    }

    public static void logRegistration(String message) {
        String response = "Tower says: " + message + " registered to weather tower.";
        log(response);
    }

    public static void logLanding(String message) {
        String response = message + " landing.";
        log(response);
    }

    public static void logWeatherChange(String message) {
        String response = message;
        log(response);
    }

}
