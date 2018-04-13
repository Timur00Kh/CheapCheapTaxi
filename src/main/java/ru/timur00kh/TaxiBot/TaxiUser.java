package ru.timur00kh.TaxiBot;

import org.telegram.telegrambots.api.objects.Location;

public class TaxiUser {
    private String name;
    private int id;
    private String state;
    private TaxiQue que;
    private long chat_id;

    private String settings;



    private String TaxiType;
    private Location locationA;
    private Location locationB;
    private String time;

    int stateNum;
    String[] states = {
            "initial",
            "TaxiType",
            "time",
            "locationA",
            "locationB",
            "final",
            "waiting"
    };


    TaxiUser(String name, int id, TaxiQue que) {
        stateNum = 0;
        state = states[stateNum];
        this.que = que;
        this.name = name;
        this.id = id;
    }

    TaxiUser(int id, TaxiQue que, long chat_id) {
        stateNum = 0;
        state = states[stateNum];
        this.chat_id = chat_id;
        this.que = que;
//        this.name = name;
        this.id = id;
    }

    public void setNextState() {
        stateNum++;
        state = states[stateNum];
    }

    public void nullify() {
        this.stateNum = 0;
        state = states[stateNum];
    }

    @Override
    public String toString() {
        return "TaxiUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", state='" + state + '\'' +
                ", TaxiType='" + TaxiType + '\'' +
                ", locationA='" + locationA + '\'' +
                ", locationB='" + locationB + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public void setQue(TaxiQue que) {
        this.que = que;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public TaxiQue getQue() {

        return que;
    }

    public long getChat_id() {
        return chat_id;
    }

    public String getSettings() {
        return settings;
    }

    public String getState() {
        return state;
    }

    public String getTaxiType() {
        return TaxiType;
    }

    public Location getLocationA() {
        return locationA;
    }

    public Location getLocationB() {
        return locationB;
    }

    public String getTime() {
        return time;
    }

    public String[] getStates() {
        return states;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTaxiType(String taxiType) {
        TaxiType = taxiType;
    }

    public void setLocationA(Location locationA) {
        this.locationA = locationA;
    }

    public void setLocationB(Location locationB) {
        this.locationB = locationB;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStates(String[] states) {
        this.states = states;
    }
}
