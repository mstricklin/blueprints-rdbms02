// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

@Slf4j
class CacheBaselineImpl<T> implements RdbmsCache.CacheBaseline<T> {
    // TODO: parameterize
    // max size
    // prime cache
    // read-fail handler
    static RdbmsCache.CacheBaseline of() {
        return new CacheBaselineImpl();
    }
    // =================================
    @Override
    public T get(int id) {
        return cache.get(id);
    }
    @Override
    public Iterable<T> list() {
        return cache.values();
    }
    @Override
    public boolean contains(int id) {
        return cache.containsKey(id);
    }
    @Override
    public int size() {
        return cache.size();
    }
    // =================================
    @Override
    public void dump() {
        if (!log.isInfoEnabled())
            return;
        for (Map.Entry<Integer, T> e : cache.entrySet())
            log.info("\t{} => {}", e.getKey(), e.getValue());
    }
    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }
    public void merge(Map<Integer, T> toAdd, Set<Integer> toDelete) {
        cache.putAll(toAdd);
        for (Integer id: toDelete) {
            cache.remove(id);
        }
    }
    public void merge(Map<Integer, T> toAdd, Map<Integer, T> toMerge, Set<Integer> toDelete) {
        cache.putAll(toAdd);
        // TODO: merge in each item, if necessary
        for (Integer id: toDelete) {
            cache.remove(id);
        }
    }
    // =================================
    private Map<Integer, T> cache = newHashMap();
}
