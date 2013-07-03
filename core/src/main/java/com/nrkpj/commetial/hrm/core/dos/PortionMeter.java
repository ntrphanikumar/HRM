package com.nrkpj.commetial.hrm.core.dos;

public class PortionMeter<T extends AbstractMeter> {
    private Portion portion;
    private T meter;
    private String formula;
    private String display;

    public Portion getPortion() {
        return portion;
    }

    public void setPortion(Portion portion) {
        this.portion = portion;
    }

    public T getMeter() {
        return meter;
    }

    public void setMeter(T meter) {
        this.meter = meter;
    }

    public String getFormula() {
        return formula;
    }

    @SuppressWarnings("unchecked")
    public void setFormula(String formula) {
        this.formula = formula;
        meter.addPortion(portion);
        if (meter instanceof CurrentMeter) {
            portion.addCurrentMeter((PortionMeter<CurrentMeter>) this);
        } else {
            portion.addWaterMeter((PortionMeter<WaterMeter>) this);
        }
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
