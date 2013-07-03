package com.nrkpj.commetial.hrm.offline;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.nrkpj.commetial.hrm.core.cache.HouseCache;
import com.nrkpj.commetial.hrm.core.dos.CurrentMeter;
import com.nrkpj.commetial.hrm.core.dos.WaterMeter;
import com.nrkpj.commetial.hrm.core.services.ArrearsPopulatorService;

public class ArrearsPopulationVerifier extends TimerTask {

    private final ArrearsPopulatorService<CurrentMeter> currentPopulatorService;
    private final ArrearsPopulatorService<WaterMeter> waterPopulatorService;
    private final HRMMobilePagesGenerator hRMMobilePagesGenerator;
    private final HouseCache houseCache;
    private final Timer timer = new Timer();

    public ArrearsPopulationVerifier(ArrearsPopulatorService<CurrentMeter> currentPopulatorService,
            ArrearsPopulatorService<WaterMeter> waterPopulatorService, HRMMobilePagesGenerator hRMMobilePagesGenerator, HouseCache houseCache) {
        this.currentPopulatorService = currentPopulatorService;
        this.waterPopulatorService = waterPopulatorService;
        this.hRMMobilePagesGenerator = hRMMobilePagesGenerator;
        this.houseCache = houseCache;
        timer.schedule(this, 0, 100);
    }

    public void run() {
        if (currentPopulatorService.isPopulationComplete() && waterPopulatorService.isPopulationComplete()) {            
            try {
                hRMMobilePagesGenerator.generate(houseCache.getEntities());
                timer.cancel();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.exit(0);
            }
        } else {
            System.out.println("Waiting for arrears population");
        }
    }
}
