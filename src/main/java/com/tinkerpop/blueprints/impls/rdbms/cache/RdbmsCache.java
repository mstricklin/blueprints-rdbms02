// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms.cache;

import com.tinkerpop.blueprints.impls.rdbms.XVertex;

import java.util.Map;
import java.util.Set;


public class RdbmsCache {

    public interface CacheBaseline<T> {
        T get(int id);
        Iterable<T> list();
        boolean contains(int id);
        int size();
        void dump();
        boolean isEmpty();

        void merge(Map<Integer, T> toAdd, Set<Integer> toDelete);
        void merge(Map<Integer, T> toAdd, Map<Integer, T> toMerge, Set<Integer> toDelete);
    }
    // =================================
    public CacheBaseline<XVertex> vertexCache() {
        return vertexCache;
    }
    public CacheBaseline<XVertex> edgeCache() {
        return edgeCache;
    }


    // Vertex & Edge cache
    // need read-through?
//    Vertex cache
    CacheBaseline<XVertex> vertexCache = CacheBaselineImpl.of();
    CacheBaseline<XVertex> edgeCache = CacheBaselineImpl.of();
//    CacheBaseline<XEdge> elements = CacheBaselineImpl.of();
    // Vertex & Edge mutable characteristics cache
}
