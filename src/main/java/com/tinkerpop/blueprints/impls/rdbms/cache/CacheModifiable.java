// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms.cache;


import com.tinkerpop.blueprints.impls.rdbms.Keyed;

public class CacheModifiable<T extends Keyed> implements RevCache<T> {
    @Override
    public T get(int id) {
        return null;
    }
    @Override
    public Iterable<T> list() {
        return null;
    }
    @Override
    public boolean contains(int id) {
        return false;
    }
    @Override
    public int size() {
        return 0;
    }
    @Override
    public void dump() {

    }
    @Override
    public void add(int id, T t) {

    }
    @Override
    public void remove(int id) {

    }
    @Override
    public void clear() {

    }
    @Override
    public void update(int id) {

    }
    @Override
    public void merge() {

    }
    @Override
    public void reset() {

    }
}
