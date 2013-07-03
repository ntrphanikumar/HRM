package com.nrkpj.commetial.hrm.core.dos;

import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.nrkpj.commetial.hrm.core.services.AmountService;

public class ComputedPortion {

    private static final Predicate<PortionMeter<CurrentMeter>> WATER_MOTOR_CURRENT_METER_PREDICATE = new Predicate<PortionMeter<CurrentMeter>>() {
        public boolean apply(PortionMeter<CurrentMeter> meter) {
            return meter.getDisplay().equals("Water Motor");
        }
    };

    private String rent = "N/A", name, ownerClass = "owner";
    private double total, currentBill, waterBill, waterMotorBill;

    public ComputedPortion(Portion portion, AmountService amountService) {
        compute(portion, amountService);
    }

    public double getTotal() {
        return total;
    }

    public double getCurrentBill() {
        return currentBill;
    }

    public double getWaterBill() {
        return waterBill;
    }

    public double getWaterMotorBill() {
        return waterMotorBill;
    }

    public String getRent() {
        return rent;
    }

    public String getName() {
        return name;
    }

    public String getOwnerClass() {
        return ownerClass;
    }
    
    private void compute(Portion portion, AmountService amountService) {
        name = portion.getName();
        if (!portion.isOwner()) {
            total += portion.getRent();
            rent = String.valueOf(portion.getRent());
            ownerClass = "tenant";
        }
        Set<PortionMeter<CurrentMeter>> waterMotorMeters = Sets.filter(portion.getCurrentMeters(), WATER_MOTOR_CURRENT_METER_PREDICATE);
        for (PortionMeter<CurrentMeter> currentMeter : waterMotorMeters) {
            waterMotorBill += amountService.getAmount(currentMeter);
        }
        for (PortionMeter<CurrentMeter> currentMeter : Sets.difference(portion.getCurrentMeters(), waterMotorMeters)) {
            currentBill += amountService.getAmount(currentMeter);
        }
        for (PortionMeter<WaterMeter> waterMeter : portion.getWaterMeters()) {
            waterBill += amountService.getAmount(waterMeter);
        }
        total += waterBill + waterMotorBill + currentBill;
    }
}
