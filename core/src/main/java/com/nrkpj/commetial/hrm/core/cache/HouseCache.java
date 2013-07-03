package com.nrkpj.commetial.hrm.core.cache;

import java.io.IOException;

import org.springframework.core.io.Resource;

import com.google.common.base.Function;
import com.nrkpj.commetial.hrm.core.dos.House;

public class HouseCache extends EntityByIdentifierCache<House, Integer> {

    private static final Function<House, Integer> uniqueIndexFunction = new Function<House, Integer>() {
        public Integer apply(House house) {
            return house.getHouseId();
        }
    };

    HouseCache(Resource seedFile) throws IOException {
        super(seedFile, uniqueIndexFunction);
    }

}
