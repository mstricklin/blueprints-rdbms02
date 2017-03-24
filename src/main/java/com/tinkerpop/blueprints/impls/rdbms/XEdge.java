// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.ExceptionFactory;
import com.tinkerpop.blueprints.util.StringFactory;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class XEdge extends XElement implements Edge {
    public static XEdge of(int id, int outVertexId, int inVertexId, String label, final RdbmsGraph graph) {
        return new XEdge(id, outVertexId, inVertexId, label, graph);
    }

    XEdge(int id_, int outVertexId, int inVertexId, String label_, RdbmsGraph graph_) {
        super(id_, graph_);
        outId = outVertexId;
        inId = inVertexId;
        label = label_;
    }
    @Override
    public Vertex getVertex(Direction direction) throws IllegalArgumentException {
        if (direction.equals(Direction.OUT))
            return getOutVertex();
        if (direction.equals(Direction.IN))
            return getInVertex();
        throw ExceptionFactory.bothIsNotSupported();
    }
    XVertex getOutVertex() {
        return (XVertex)graph.getVertex(outId);
    }
    XVertex getInVertex() {
        return (XVertex)graph.getVertex(inId);
    }
    // =================================
    @Override
    public String getLabel() {
        return label;
    }
    @Override
    public <T> T getProperty(String key) {
        return null;
    }
    @Override
    public Set<String> getPropertyKeys() {
        return null;
    }
    @Override
    public void setProperty(String key, Object value) {

    }
    @Override
    public <T> T removeProperty(String key) {
        return null;
    }
    @Override
    public void remove() {

    }
    // =================================
    @Override
    public int hashCode() {
        return id;
    }
    @Override
    public String toString() {
        return StringFactory.edgeString(this);
    }
    // =================================
    static class Mutable {

        private final Map<String, Object> properties = newHashMap();
    }

    private final int outId, inId;
    private final String label;
}
