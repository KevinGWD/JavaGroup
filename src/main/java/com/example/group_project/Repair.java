package com.example.group_project;

import java.util.Date;

public class Repair {
    private int repairID;
    private int ownerID;
    private int carID;
    private Date date;
    private String description;
    private int cost;

    public Repair(int repairID, int ownerID, int carID, Date date, String description, int cost) {
        this.repairID = repairID;
        this.ownerID = ownerID;
        this.carID = carID;
        this.date = date;
        this.description = description;
        this.cost = cost;
    }

    public int getRepairID() {
        return repairID;
    }

    public void setRepairID(int repairID) {
        this.repairID = repairID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
