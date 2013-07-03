package com.nrkpj.commetial.hrm.core.cache;

import java.io.IOException;

import org.springframework.core.io.Resource;

import com.google.common.base.Function;
import com.nrkpj.commetial.hrm.core.dos.House;
import com.nrkpj.commetial.hrm.core.dos.Portion;

public class PortionCache extends EntityByIdentifierCache<Portion, Integer> {

	private static final Function<Portion, Integer> uniqueIndexFunction = new Function<Portion, Integer>() {
		public Integer apply(Portion portion) {
			return portion.getPortionId();
		}
	};

	private final HouseCache houseCache;

	PortionCache(Resource seedFile, HouseCache houseCache) throws IOException {
		super(seedFile, uniqueIndexFunction);
		this.houseCache = houseCache;
	}

	@Override
	protected Object getTransformedValue(String value, Class<?> toClass) {
		if (House.class.equals(toClass)) {
			return houseCache.getById(Integer.parseInt(value));
		}
		return super.getTransformedValue(value, toClass);
	}

}
