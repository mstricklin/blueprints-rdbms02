// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms;

import com.tinkerpop.blueprints.*;
import com.tinkerpop.blueprints.impls.rdbms.cache.RdbmsCache;
import com.tinkerpop.blueprints.impls.rdbms.cache.RdbmsXActionCache;
import com.tinkerpop.blueprints.util.ExceptionFactory;
import com.tinkerpop.blueprints.util.StringFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class RdbmsGraph implements TransactionalGraph, IndexableGraph, KeyIndexableGraph {

    private static final Features FEATURES = new Features();

    static {
        // TODO: revisit this...
        FEATURES.supportsSerializableObjectProperty = false;
        FEATURES.supportsBooleanProperty = true;
        FEATURES.supportsDoubleProperty = true;
        FEATURES.supportsFloatProperty = true;
        FEATURES.supportsIntegerProperty = true;
        FEATURES.supportsPrimitiveArrayProperty = true;
        FEATURES.supportsUniformListProperty = true;
        FEATURES.supportsMixedListProperty = false;
        FEATURES.supportsLongProperty = true;
        FEATURES.supportsMapProperty = false;
        FEATURES.supportsStringProperty = true;

        FEATURES.supportsDuplicateEdges = true;
        FEATURES.supportsSelfLoops = true;
        FEATURES.isPersistent = false; //true; // TODO
        FEATURES.isWrapper = false;
        FEATURES.supportsVertexIteration = true;
        FEATURES.supportsEdgeIteration = true;
        FEATURES.supportsVertexIndex = true;
        FEATURES.supportsEdgeIndex = true;
        FEATURES.ignoresSuppliedIds = true;
        FEATURES.supportsTransactions = false; // true
        FEATURES.supportsIndices = true;
        FEATURES.supportsKeyIndices = true;
        FEATURES.supportsVertexKeyIndex = true;
        FEATURES.supportsEdgeKeyIndex = true;
        FEATURES.supportsEdgeRetrieval = true;
        FEATURES.supportsVertexProperties = true;
        FEATURES.supportsEdgeProperties = true;
        FEATURES.supportsThreadedTransactions = false;
    }

    // =================================
    public RdbmsGraph() {
//        baseline = new RdbmsCache();
        vertexCounter.set(77);
        edgeCounter.set(77);

    }
    RdbmsCache baselineCache = new RdbmsCache();
    // TODO: make thread-local
    RdbmsXActionCache cache = RdbmsXActionCache.of(baselineCache);

    public void cacheDump() {
        if (!log.isInfoEnabled())
            return;
        cache.dump();

        for (XVertex v: cache.vertex().list()) {
            log.info(" {}", v);
        }
    }


    @Override
    public Features getFeatures() {
        return FEATURES;
    }
    // =================================
    @Override
    public Vertex addVertex(Object id) {
        XVertex v = XVertex.of(vertexCounter.getAndIncrement(), this);
        cache.vertex().add(v.getRawId(), v);
        // TODO queue W-B
        return v;
    }
    @Override
    public Vertex getVertex(Object id) {
        if (null == id)
            throw ExceptionFactory.vertexIdCanNotBeNull();
        try {
            final Integer intID;
            if (id instanceof Integer)
                intID = (Integer) id;
            else
                intID = Integer.valueOf(id.toString());
            return cache.vertex().get(intID);
        } catch (NumberFormatException | ClassCastException e) {
            log.error("could not use vertex id {}", id);
        }
        return null;
    }
    @Override
    public void removeVertex(Vertex vertex) {
        // TODO: queue W-B
        checkNotNull(vertex);
//        for (Edge e : vertex.getEdges(Direction.BOTH))
//            removeEdge(e);
        Integer intId = (Integer) vertex.getId();
        // TODO: remove from indices
        cache.vertex().remove(intId);

    }
    @Override
    public Iterable<Vertex> getVertices() {
        return null;
    }
    @Override
    public Iterable<Vertex> getVertices(String key, Object value) {
        return null;
    }
    // =================================
    @Override
    public Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label) {
        XVertex oV = (XVertex)outVertex;
        XVertex iV = (XVertex)inVertex;
        XEdge e = XEdge.of(edgeCounter.getAndIncrement(),
                oV.getRawId(), iV.getRawId(), label, this);
        oV.addOutEdge(e.getRawId());
        iV.addInEdge(e.getRawId());
        cache.edge().add(e.getRawId(), e);
        return e;
    }
    @Override
    public Edge getEdge(Object id) {
        return null;
    }
    @Override
    public void removeEdge(Edge edge) {

    }
    @Override
    public Iterable<Edge> getEdges() {
        return null;
    }
    @Override
    public Iterable<Edge> getEdges(String key, Object value) {
        return null;
    }
    // =================================
    @Override
    public GraphQuery query() {
        return null;
    }
    public void begin() {

    }
    @Override
    public void shutdown() {

    }
    // TODO synchronize here?
    @Override
    public void commit() {
        cache.merge();
    }
    @Override
    public void rollback() {
        cache.reset();
    }
    @Deprecated
    @Override
    public void stopTransaction(Conclusion conclusion) {
    }
    // =================================
    @Override
    public <T extends Element> Index<T> createIndex(String indexName, Class<T> indexClass, Parameter[] indexParameters) {
        return null;
    }
    @Override
    public <T extends Element> Index<T> getIndex(String indexName, Class<T> indexClass) {
        return null;
    }
    @Override
    public Iterable<Index<? extends Element>> getIndices() {
        return null;
    }
    @Override
    public void dropIndex(String indexName) {

    }
    @Override
    public <T extends Element> void dropKeyIndex(String key, Class<T> elementClass) {

    }
    @Override
    public <T extends Element> void createKeyIndex(String key, Class<T> elementClass, Parameter[] indexParameters) {

    }
    @Override
    public <T extends Element> Set<String> getIndexedKeys(Class<T> elementClass) {
        return null;
    }
    // =================================
    public String toString() {
//        return StringFactory.graphString(this, "vertices:" + vertex.size() + " edges:" + edge.size());
        return StringFactory.graphString(this, "foo");
    }
    // =================================
    private final AtomicInteger vertexCounter = new AtomicInteger();
    private final AtomicInteger edgeCounter = new AtomicInteger();


}
