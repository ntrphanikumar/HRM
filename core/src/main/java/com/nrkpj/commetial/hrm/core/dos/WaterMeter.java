package com.nrkpj.commetial.hrm.core.dos;

public class WaterMeter extends AbstractMeter {

    private String canNumber;

    public String getCanNumber() {
        return canNumber;
    }

    public WaterMeter setCanNumber(String canNumber) {
        this.canNumber = canNumber;
        return this;
    }

    public WaterMeter setHouse(House house) {
        super.setHouse(house);
        house.addWaterMeter(this);
        return this;
    }

}
