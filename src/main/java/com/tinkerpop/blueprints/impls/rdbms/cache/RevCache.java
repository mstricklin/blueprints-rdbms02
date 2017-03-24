// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms.cache;

import com.tinkerpop.blueprints.impls.rdbms.Keyed;

public interface RevCache<T  extends Keyed> {
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

    // mutation
    void update(int id);

    // transaction
    void merge();
    void reset();
}
