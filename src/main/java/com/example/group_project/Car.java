package com.example.group_project;

public class Car {
    private int carID;
    private String make;
    private String model;
    private int carVIN;
    private int builtYear;
    private String type;

    public Car(int carID, String make, String model, int carVIN, int builtYear, String type) {
        this.carID = carID;
        this.make = make;
        this.model = model;
        this.carVIN = carVIN;
        this.builtYear = builtYear;
        this.type = type;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCarVIN() {
        return carVIN;
    }

    public void setCarVIN(int carVIN) {
        this.carVIN = carVIN;
    }

    public int getBuiltYear() {
        return builtYear;
    }

    public void setBuiltYear(int builtYear) {
        this.builtYear = builtYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
