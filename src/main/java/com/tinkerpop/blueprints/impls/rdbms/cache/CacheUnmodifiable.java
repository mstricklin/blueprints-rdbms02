// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public interface CacheUnmodifiable<T> {

    // observe
    T get(int id);
    Iterable<T> list();
    boolean contains(int id);
    int size();
    void dump();

    // add/remove
    void add(int id, T t);
    void remove(int id);
    void clear();

    // transaction
    void merge();
    void reset();

    @Slf4j
    class Cache<T> implements CacheUnmodifiable<T> {
        public static CacheUnmodifiable.Cache of(RdbmsCache.CacheBaseline baseline) {
            return new Cache(baseline);
        }
        Cache(RdbmsCache.CacheBaseline baseline_) {
            baseline = baseline_;
        }


        @Override
        public T get(int id) {
            if (removed.contains(id))
                return null;
            T t = added.get(id);
            return (null == t) ? baseline.get(id) : t;
        }
        @Override
        public Iterable<T> list() {
            return null;
        }
        @Override
        public boolean contains(int id) {
            if (removed.contains(id))
                return false;
            return added.containsKey(id) || baseline.contains(id);
        }
        @Override
        public int size() {
            return 0;
        }
        @Override
        public void dump() {
            if (!log.isInfoEnabled())
                return;
            if ( ! removed.isEmpty()) {
                log.info("================== removed ========");
                log.info(removed.toString());
            }
            if ( ! added.isEmpty() ) {
                log.info("================== added ========");
                for (Map.Entry<Integer, T> e : added.entrySet())
                    log.info("\t{}", e.getKey(), e.getValue());
            }
            if ( ! baseline.isEmpty() ) {
                log.info("================== baseline ========");
                baseline.dump();
            }
        }
        // =================================
        @Override
        public void add(int id, T t) {
            added.put(id, t);
        }
        @Override
        public void remove(int id) {
            removed.add(id);
            added.remove(id);
        }
        @Override
        public void reset() {
            added.clear();
            removed.clear();
        }
        // =================================
        @Override
        public void merge() {
            // TODO: handle actual conflicts?
            baseline.merge(added, removed);
            reset();
        }
        @Override
        public void clear() {
            // TODO: copy from baseline to removed
            // TODO: move from added to removed
            added.clear();
            removed.clear();
        }
        // =================================
        RdbmsCache.CacheBaseline<T> baseline;
        private Map<Integer, T> added = newHashMap();
        private Set<Integer> removed = newHashSet();
    }
}
