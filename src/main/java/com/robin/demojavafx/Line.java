package com.robin.demojavafx;

public class Line {
    private String period;
    private float total;
    private float housing;
    private float food;
    private float outing;
    private float transport;
    private float travel;
    private float taxes;
    private float other;

    public Line(String period, float total, float housing, float food, float outing, float transport, float travel, float taxes, float other) {
        this.period = period;
        this.total = total;
        this.housing = housing;
        this.food = food;
        this.outing = outing;
        this.transport = transport;
        this.travel = travel;
        this.taxes = taxes;
        this.other = other;
    }

    // Convertir Line en Expense pour la base de données
    public Expense toExpense() {
        return new Expense(
                this.period,
                this.housing,
                this.food,
                this.outing,
                this.transport,
                this.travel,
                this.taxes,
                this.other
        );
    }

    // Getters et setters
    public String getPeriod() {
        return period;
    }

    public float getTotal() {
        return total;
    }

    public float getHousing() {
        return housing;
    }

    public float getFood() {
        return food;
    }

    public float getOuting() {
        return outing;
    }

    public float getTransport() {
        return transport;
    }

    public float getTravel() {
        return travel;
    }

    public float getTaxes() {
        return taxes;
    }

    public float getOther() {
        return other;
    }
}