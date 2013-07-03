package com.nrkpj.commetial.hrm.core.services;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.nrkpj.commetial.hrm.core.dos.AbstractMeter;
import com.nrkpj.commetial.hrm.core.dos.CurrentMeter;
import com.nrkpj.commetial.hrm.core.dos.Portion;
import com.nrkpj.commetial.hrm.core.dos.PortionMeter;

public class AmountService {

    private final ScriptEngine engine;

    private Function<PortionMeter<? extends AbstractMeter>, Integer> totalConsumedUnitsFunction = new Function<PortionMeter<? extends AbstractMeter>, Integer>() {
        public Integer apply(PortionMeter<? extends AbstractMeter> portionCurrentMeter) {
            CurrentMeter currentMeter = (CurrentMeter) portionCurrentMeter.getMeter();
            currentMeter = currentMeter.isSubMeter() ? currentMeter.getParentMeter() : currentMeter;
            return currentMeter.getCurrentReading() - currentMeter.getPrevReading();
        }
    };

    private Function<PortionMeter<? extends AbstractMeter>, Integer> consumedUnitsOfSubmetersFunction = new Function<PortionMeter<? extends AbstractMeter>, Integer>() {
        public Integer apply(PortionMeter<? extends AbstractMeter> portionCurrentMeter) {
            int consumedUnitsOfSubmeters = 0;
            CurrentMeter currentMeter = (CurrentMeter) portionCurrentMeter.getMeter();
            currentMeter = currentMeter.isSubMeter() ? currentMeter.getParentMeter() : currentMeter;
            for (CurrentMeter subMeter : currentMeter.getSubMeters()) {
                consumedUnitsOfSubmeters += subMeter.getCurrentReading() - subMeter.getPrevReading();
            }
            return consumedUnitsOfSubmeters;
        }
    };

    private Function<PortionMeter<? extends AbstractMeter>, Integer> peopleInPortionFunction = new Function<PortionMeter<? extends AbstractMeter>, Integer>() {
        public Integer apply(PortionMeter<? extends AbstractMeter> portionMeter) {
            return portionMeter.getPortion().getMembers();
        }
    };

    private Function<PortionMeter<? extends AbstractMeter>, Integer> totalPeopleSharingFunction = new Function<PortionMeter<? extends AbstractMeter>, Integer>() {
        public Integer apply(PortionMeter<? extends AbstractMeter> portionMeter) {
            int totalPeopleSharing = 0;
            for (Portion portion : portionMeter.getMeter().getPortions()) {
                totalPeopleSharing += portion.getMembers();
            }
            return totalPeopleSharing;
        }
    };

    private Function<PortionMeter<? extends AbstractMeter>, Integer> consumedUnitsFunction = new Function<PortionMeter<? extends AbstractMeter>, Integer>() {
        public Integer apply(PortionMeter<? extends AbstractMeter> portionCurrentMeter) {
            CurrentMeter meter = (CurrentMeter) portionCurrentMeter.getMeter();
            return meter.getCurrentReading() - meter.getPrevReading();
        }
    };

    private final Map<String, Function<PortionMeter<? extends AbstractMeter>, Integer>> tokenEvaluatorByToken = Maps.newHashMap();

    public AmountService() {
        this.engine = new ScriptEngineManager().getEngineByName("JavaScript");
        tokenEvaluatorByToken.put("[totalConsumedUnits]", totalConsumedUnitsFunction);
        tokenEvaluatorByToken.put("[consumedUnitsOfSubmeters]", consumedUnitsOfSubmetersFunction);
        tokenEvaluatorByToken.put("[peopleInPortion]", peopleInPortionFunction);
        tokenEvaluatorByToken.put("[totalPeopleSharing]", totalPeopleSharingFunction);
        tokenEvaluatorByToken.put("[consumedUnits]", consumedUnitsFunction);
    }

    public double getAmount(PortionMeter<? extends AbstractMeter> portionMeter) {
        String expression = portionMeter.getFormula();
        for (String token : tokenEvaluatorByToken.keySet()) {
            if (StringUtils.contains(expression, token)) {
                expression = StringUtils.replace(expression, token, tokenEvaluatorByToken.get(token).apply(portionMeter).toString());
            }
        }
        AbstractMeter meter = portionMeter.getMeter();
        if (meter instanceof CurrentMeter && ((CurrentMeter) meter).isSubMeter()) {
            meter = ((CurrentMeter) meter).getParentMeter();
        }
        try {
            return Math.ceil(Double.parseDouble(engine.eval(expression).toString()) * meter.getArrears());
        } catch (Exception e) {
            throw new RuntimeException("Encounter error computing amount for Portion[" + portionMeter.getPortion().getName() + "] and Current Meter["
                    + meter.getMeterId() + "]", e);
        }
    }
}
