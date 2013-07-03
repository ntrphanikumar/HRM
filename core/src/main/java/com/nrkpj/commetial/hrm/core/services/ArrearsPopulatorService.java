package com.nrkpj.commetial.hrm.core.services;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.collect.Maps;
import com.nrkpj.commetial.hrm.core.cache.AbstractEntityCache;
import com.nrkpj.commetial.hrm.core.dos.Arrearable;

public class ArrearsPopulatorService<T extends Arrearable<?>> {

    private final ArrearService<T> arrearService;
    private final Map<T, Future<?>> runningArrearsPopulators = Maps.newHashMap();

    private class ArrearsPopulatorTask implements Runnable {
        private final T arrearable;

        private ArrearsPopulatorTask(T arrearable) {
            this.arrearable = arrearable;
        }

        public void run() {
            arrearable.setArrears(arrearService.getArrears(arrearable));
            runningArrearsPopulators.remove(arrearable);
        }
    }

    public ArrearsPopulatorService(ArrearService<T> arrearService, AbstractEntityCache<T> cache) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        this.arrearService = arrearService;
        Set<T> currentMeters = cache.getEntities();
        for (T arrearable : currentMeters) {
            runningArrearsPopulators.put(arrearable, executorService.submit(new ArrearsPopulatorTask(arrearable)));
        }
    }

    public boolean isPopulationComplete() {
        return runningArrearsPopulators.isEmpty();
    }
}
