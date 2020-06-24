package com.example.project1;


import android.app.Application;

public class MyApplication extends Application {

    private String loggedId;
    private boolean isLoggedIn=false;
    private boolean payed=false;
    private boolean payedText=false;
    private int carNumber;
    private int trainNumber;
    private int route;
    private int stationNumber;
    private int selectedSeat;
    private int selectedCarLocation;
    private int endStationNumber;
    private String homeStation="1007000710";
    private String workStation="1007000710";

    public String getHomeStation() {
        return homeStation;
    }

    public void setHomeStation(String homeStation) {
        this.homeStation = homeStation;
    }

    public String getWorkStation() {
        return workStation;
    }

    public void setWorkStation(String workStation) {
        this.workStation = workStation;
    }

    public int getEndStationNumber() {
        return endStationNumber;
    }

    public void setEndStationNumber(int endStationNumber) {
        this.endStationNumber = endStationNumber;
    }

    public String getLoggedId() {
        return loggedId;
    }

    public void setLoggedId(String loggedId) {
        this.loggedId = loggedId;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public int getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(int selectedSeat) {
        this.selectedSeat = selectedSeat;
    }

    public int getSelectedCarLocation() {
        return selectedCarLocation;
    }

    public void setSelectedCarLocation(int selectedCarLocation) {
        this.selectedCarLocation = selectedCarLocation;
    }

    public boolean isPayedText() {
        return payedText;
    }

    public void setPayedText(boolean payedText) {
        this.payedText = payedText;
    }
}
