package com.nrkpj.commetial.hrm.core.cache;

import java.io.IOException;
import java.util.Set;

import org.springframework.core.io.Resource;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.nrkpj.commetial.hrm.core.dos.AbstractMeter;
import com.nrkpj.commetial.hrm.core.dos.Portion;
import com.nrkpj.commetial.hrm.core.dos.PortionMeter;

public class PortionMeterCache<T extends AbstractMeter> extends AbstractEntityCache<PortionMeter<T>> {

    private final PortionCache portionCache;
    private final EntityByIdentifierCache<? extends AbstractMeter, Integer> meterCache;

    PortionMeterCache(Resource seedFile, PortionCache portionCache, EntityByIdentifierCache<AbstractMeter, Integer> meterCache) throws IOException {
        super(seedFile);
        this.portionCache = portionCache;
        this.meterCache = meterCache;
    }

    protected Object getTransformedValue(String value, Class<?> toClass) {
        if (Portion.class.equals(toClass)) {
            return portionCache.getById(Integer.parseInt(value));
        }
        if (AbstractMeter.class.equals(toClass)) {
            return meterCache.getById(Integer.parseInt(value));
        }
        return super.getTransformedValue(value, toClass);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Class getEntityClass() {
        return PortionMeter.class;
    }

    public Set<PortionMeter<T>> getPortionMetersByHouseId(final int houseId) {
        return Sets.filter(getEntities(), new Predicate<PortionMeter<T>>() {
            public boolean apply(PortionMeter<T> portionCurrentMeter) {
                return portionCurrentMeter.getPortion().getHouse().getHouseId() == houseId;
            }
        });
    }
}
