package ro.academyplus.avaj.aircraft;

public class Coordinates {
    private int longitude;
    private int latitude;
    private int height;

    Coordinates(int longitude, int latitude, int height) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.height = height;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getHeight() {
        return height;
    }

    // additional method. Method to modify the coordinates by given deltas
    public void modifyCoordinates(int deltaLongitude, int deltaLatitude, int deltaHeight) {
        this.longitude += deltaLongitude;
        this.latitude += deltaLatitude;
        this.height += deltaHeight;

        // Ensure height is within bounds (0 to 100)
        if (this.height > 100) {
            this.height = 100;
        } else if (this.height < 0) {
            this.height = 0;
        }
    }
}
