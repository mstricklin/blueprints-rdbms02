// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms.cache;


public interface CacheModifiable<T> extends CacheUnmodifiable<T> {

    // mutation
    void update(int id);
}
