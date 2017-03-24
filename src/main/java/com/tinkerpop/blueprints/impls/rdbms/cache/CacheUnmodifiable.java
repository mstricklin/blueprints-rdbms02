// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms.cache;

import com.google.common.base.Predicate;
import com.tinkerpop.blueprints.impls.rdbms.Keyed;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.filter;



@Slf4j
public class CacheUnmodifiable<T extends Keyed> implements RevCache<T> {

    public static RevCache of(RdbmsCache.CacheBaseline baseline) {
        return new CacheUnmodifiable(baseline);
    }
    private CacheUnmodifiable(RdbmsCache.CacheBaseline baseline_) {
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
        Iterable<T> present = concat(baseline.list(), added.values());
        return filter(present, not(inRemoved));
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
        if ( ! log.isInfoEnabled())
            return;
        if (!removed.isEmpty()) {
            log.info("================== removed ========");
            log.info(removed.toString());
        }
        if ( ! added.isEmpty()) {
            log.info("================== added ========");
            for (Map.Entry<Integer, T> e : added.entrySet())
                log.info("\t{}", e.getValue());
        }
        if ( ! baseline.isEmpty()) {
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
        // TODO...no conflicts possible from an unmodifiable cache?
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
    @Override
    public void update(int id) {
        throw new UnsupportedOperationException("update not supported on CacheUnmodifiable");
    }
    // =================================
    private final RdbmsCache.CacheBaseline<T> baseline;
    private final Map<Integer, T> added = newHashMap();
    private final Set<Integer> removed = newHashSet();

    private Predicate<T> inRemoved = new Predicate<T>() {
        @Override
        public boolean apply(T t) {
            return removed.contains( t.getRawId() );
        }
    };
}
