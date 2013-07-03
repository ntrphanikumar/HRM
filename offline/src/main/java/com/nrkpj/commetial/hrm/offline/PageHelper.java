package com.nrkpj.commetial.hrm.offline;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.nrkpj.commetial.hrm.core.dos.ComputedPortion;
import com.nrkpj.commetial.hrm.core.dos.House;
import com.nrkpj.commetial.hrm.core.dos.Portion;
import com.nrkpj.commetial.hrm.core.services.AmountService;

public class PageHelper {
    private final Function<Portion, ComputedPortion> portionToComputedPortionFunction;

    public PageHelper(final AmountService amountService) {
        portionToComputedPortionFunction = new Function<Portion, ComputedPortion>() {
            public ComputedPortion apply(final Portion portion) {
                return new ComputedPortion(portion, amountService);
            }
        };
    }

    public String getHousePageName(House house) {
        return house.getName() + ".html";
    }

    public Collection<ComputedPortion> getPortions(House house) {
        return Collections2.transform(house.getPortions(), portionToComputedPortionFunction);
    }
}
