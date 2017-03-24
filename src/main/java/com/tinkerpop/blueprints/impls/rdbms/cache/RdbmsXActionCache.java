// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms.cache;

import com.tinkerpop.blueprints.impls.rdbms.XEdge;
import com.tinkerpop.blueprints.impls.rdbms.XVertex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RdbmsXActionCache {
    public static RdbmsXActionCache of(RdbmsCache baseline) {
        return new RdbmsXActionCache(baseline);
    }

    private final RdbmsCache baseline;

    private RdbmsXActionCache(RdbmsCache baseline_) {
        baseline = baseline_;
        vertexCache = CacheUnmodifiable.of(baseline.vertexCache);
        edgeCache = CacheUnmodifiable.of(baseline.edgeCache);
    }
    public RevCache<XVertex> vertex() {
        return vertexCache;
    }
    public RevCache<XEdge> edge() {
        return edgeCache;
    }
    public void dump() {
        log.info(" ------- Vertices ------------------------ ");
        vertexCache.dump();
        log.info(" ------- Edges --------------------------- ");
        edgeCache.dump();
    }
    public void merge() {
        vertexCache.merge();
        edgeCache.merge();
    }
    public void reset() {
        vertexCache.reset();
        edgeCache.reset();
    }
    // =================================
    private final RevCache<XVertex> vertexCache;
    private final RevCache<XEdge> edgeCache;
}
