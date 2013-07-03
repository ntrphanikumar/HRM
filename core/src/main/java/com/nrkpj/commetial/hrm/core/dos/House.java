package com.nrkpj.commetial.hrm.core.dos;

import java.util.Set;

import com.google.common.collect.Sets;

public class House {
    private int houseId;
    private String name;
    private String circle;
    private String ero;
    private int members;

    private Set<Portion> portions = Sets.newHashSet();
    private Set<CurrentMeter> currentMeters = Sets.newHashSet();
    private Set<WaterMeter> waterMeters = Sets.newHashSet();

    public int getHouseId() {
        return houseId;
    }

    public House setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public String getName() {
        return name;
    }

    public House setName(String name) {
        this.name = name;
        return this;
    }

    public String getCircle() {
        return circle;
    }

    public House setCircle(String circle) {
        this.circle = circle;
        return this;
    }

    public String getEro() {
        return ero;
    }

    public House setEro(String ero) {
        this.ero = ero;
        return this;
    }

    public int getMembers() {
        return members;
    }

    public House setMembers(int members) {
        this.members = members;
        return this;
    }

    public void addPortion(Portion portion) {
        portions.add(portion);
    }

    public Set<Portion> getPortions() {
        return portions;
    }

    public void addCurrentMeter(CurrentMeter currentMeter) {
        currentMeters.add(currentMeter);
    }

    public Set<CurrentMeter> getCurrentMeters() {
        return currentMeters;
    }

    public void addWaterMeter(WaterMeter waterMeter) {
        waterMeters.add(waterMeter);
    }

    public Set<WaterMeter> getWaterMeters() {
        return waterMeters;
    }
}
