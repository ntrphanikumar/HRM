package com.nrkpj.commetial.hrm.core.dos;

import java.util.Set;

import com.google.common.collect.Sets;

public class Portion {

    private int portionId;
    private House house;
    private String name;
    private double rent;
    private int members;
    private Set<PortionMeter<CurrentMeter>> currentMeters = Sets.newHashSet();
    private Set<PortionMeter<WaterMeter>> waterMeters = Sets.newHashSet();
    private boolean owner;

    public int getPortionId() {
        return portionId;
    }

    public void setPortionId(int portionId) {
        this.portionId = portionId;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
        house.addPortion(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public void addCurrentMeter(PortionMeter<CurrentMeter> currentMeter) {
        currentMeters.add(currentMeter);
    }

    public Set<PortionMeter<CurrentMeter>> getCurrentMeters() {
        return currentMeters;
    }

    public void addWaterMeter(PortionMeter<WaterMeter> waterMeter) {
        waterMeters.add(waterMeter);
    }

    public Set<PortionMeter<WaterMeter>> getWaterMeters() {
        return waterMeters;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isOwner() {
        return owner;
    }
}
