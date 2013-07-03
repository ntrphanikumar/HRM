package com.nrkpj.commetial.hrm.core.dos;

import java.util.Set;

import com.google.common.collect.Sets;

public class CurrentMeter extends AbstractMeter {

    private String scno;
    private boolean subMeter;
    private CurrentMeter parentMeter;
    private int prevReading;
    private int currentReading;

    private Set<CurrentMeter> subMeters = Sets.newHashSet();

    public CurrentMeter setHouse(House house) {
        super.setHouse(house);
        house.addCurrentMeter(this);
        return this;
    }

    public String getScno() {
        return scno;
    }

    public CurrentMeter setScno(String scno) {
        this.scno = scno;
        return this;
    }

    public boolean isSubMeter() {
        return subMeter;
    }

    public CurrentMeter setSubMeter(boolean subMeter) {
        this.subMeter = subMeter;
        return this;
    }

    public CurrentMeter getParentMeter() {
        return parentMeter;
    }

    public CurrentMeter setParentMeter(CurrentMeter parentMeter) {
        this.parentMeter = parentMeter;
        if (parentMeter != null) {
            parentMeter.addSubMeter(this);
        }
        return this;
    }

    public int getPrevReading() {
        return prevReading;
    }

    public CurrentMeter setPrevReading(int prevReading) {
        this.prevReading = prevReading;
        return this;
    }

    public double getArrears() {
        if (isSubMeter()) {
            return Math.ceil((new Double((getCurrentReading() - getPrevReading())) / new Double(getParentMeter().getCurrentReading()
                    - getParentMeter().getPrevReading()))
                    * getParentMeter().getArrears());
        }
        return super.getArrears();
    }

    public int getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(int currentReading) {
        this.currentReading = currentReading;
    }

    public void addSubMeter(CurrentMeter subMeter) {
        subMeters.add(subMeter);
    }

    public Set<CurrentMeter> getSubMeters() {
        return subMeters;
    }
}
