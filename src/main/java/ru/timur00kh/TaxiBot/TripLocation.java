package ru.timur00kh.TaxiBot;

public class TripLocation {
    private Float longitude;
    private Float latitude;

    public TripLocation(Float longitude, Float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {

        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }
}
