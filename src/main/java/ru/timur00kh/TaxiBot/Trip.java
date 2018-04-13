package ru.timur00kh.TaxiBot;

import org.telegram.telegrambots.api.objects.Location;

import java.util.ArrayList;

public class Trip {
    private ArrayList<TaxiUser> taxiUsers = new ArrayList<>(4);
    private Location locationA = new Location();
    private Location locationB = new Location();
    private String time;

    private void add(TaxiUser user) {
        //locationA.
    }

    public boolean check(TaxiUser user) {
        if (taxiUsers.size() > 4) return false;
        if (this.time != user.getTime()) return false;
        float distance1 = Math.abs(user.getLocationA().getLatitude() + locationA.getLatitude()) *
                Math.abs(user.getLocationA().getLatitude() + locationA.getLatitude()) +
                Math.abs(user.getLocationA().getLongitude() + locationA.getLongitude()) *
                Math.abs(user.getLocationA().getLongitude() + locationA.getLongitude());
        if (distance1 > 0.00448) return false;
        float distance2 = Math.abs(user.getLocationB().getLatitude() + locationB.getLatitude()) *
                Math.abs(user.getLocationB().getLatitude() + locationB.getLatitude()) +
                Math.abs(user.getLocationB().getLongitude() + locationB.getLongitude()) *
                Math.abs(user.getLocationB().getLongitude() + locationB.getLongitude());
        if (distance2 > 0.00448) return false;
        add(user);
        return true;
    }
}
