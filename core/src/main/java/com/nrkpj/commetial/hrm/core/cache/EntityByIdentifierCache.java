package com.nrkpj.commetial.hrm.core.cache;

import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.Resource;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

abstract class EntityByIdentifierCache<E, I> extends AbstractEntityCache<E> {

    private final Function<E, I> uniqueIndexFunction;

    private Map<I, E> entitiesById;

    EntityByIdentifierCache(Resource seedFile,
            Function<E, I> uniqueIndexFunction) throws IOException {
        super(seedFile);
        this.uniqueIndexFunction = uniqueIndexFunction;
        this.entitiesById = Maps.newHashMap();
    }

    protected void onEntityAdd(E entity) {
        entitiesById.put(uniqueIndexFunction.apply(entity), entity);
    }

    public E getById(I id) {
        return entitiesById.get(id);
    }

    public Map<I, E> getEntitiesById() {
        return entitiesById;
    }
}
