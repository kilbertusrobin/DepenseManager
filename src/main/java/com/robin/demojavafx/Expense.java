package com.robin.demojavafx;

public class Expense {

    private String date;
    private double housing;
    private double food;
    private double goingOut;
    private double transportation;
    private double travel;
    private double tax;
    private double other;

    public Expense(String date, double housing, double food, double goingOut, double transportation, double travel, double tax, double other) {
        this.date = date;
        this.housing = housing;
        this.food = food;
        this.goingOut = goingOut;
        this.transportation = transportation;
        this.travel = travel;
        this.tax = tax;
        this.other = other;
    }

    public String getDate() {
        return date;
    }

    public double getHousing() {
        return housing;
    }

    public double getFood() {
        return food;
    }

    public double getGoingOut() {
        return goingOut;
    }

    public double getTransportation() {
        return transportation;
    }

    public double getTravel() {
        return travel;
    }

    public double getTax() {
        return tax;
    }

    public double getOther() {
        return other;
    }
}
