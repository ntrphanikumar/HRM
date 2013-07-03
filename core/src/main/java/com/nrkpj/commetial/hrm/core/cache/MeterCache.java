package com.nrkpj.commetial.hrm.core.cache;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;

import com.google.common.base.Function;
import com.nrkpj.commetial.hrm.core.dos.AbstractMeter;
import com.nrkpj.commetial.hrm.core.dos.House;

public class MeterCache<T extends AbstractMeter> extends EntityByIdentifierCache<T, Integer> {

    private final HouseCache houseCache;
    private final Class<T> clazz;

    MeterCache(Resource seedFile, HouseCache houseCache, Class<T> clazz) throws IOException, ClassNotFoundException {
        super(seedFile, new Function<T, Integer>() {
            public Integer apply(T meter) {
                return meter.getMeterId();
            };
        });
        this.houseCache = houseCache;
        this.clazz = clazz;
    }

    @Override
    protected Object getTransformedValue(String value, Class<?> toClass) {
        if (House.class.equals(toClass)) {
            return houseCache.getById(Integer.parseInt(value));
        }
        if (clazz.equals(toClass)) {
            return StringUtils.isNotBlank(value) ? getById(Integer.parseInt(value)) : null;
        }
        return super.getTransformedValue(value, toClass);
    }

    @Override
    protected Class<T> getEntityClass() {
        return clazz;
    }

}
