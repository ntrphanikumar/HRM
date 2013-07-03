package com.nrkpj.commetial.hrm.core.dos;

import java.util.Set;

import com.google.common.collect.Sets;

public class AbstractMeter implements Arrearable<AbstractMeter> {
    private int meterId;
    private House house;
    private double arrears;
    private Set<Portion> portions = Sets.newHashSet();

    public int getMeterId() {
        return meterId;
    }

    public AbstractMeter setMeterId(int meterId) {
        this.meterId = meterId;
        return this;
    }

    public House getHouse() {
        return house;
    }

    public AbstractMeter setHouse(House house) {
        this.house = house;
        return this;
    }

    public double getArrears() {
        return arrears;
    }

    public AbstractMeter setArrears(double arrears) {
        this.arrears = arrears;
        return this;
    }

    public void addPortion(Portion portion) {
        portions.add(portion);
    }

    public Set<Portion> getPortions() {
        return portions;
    }

}
