// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.blueprints.util.ElementHelper;
import com.tinkerpop.blueprints.util.StringFactory;
import lombok.extern.slf4j.Slf4j;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

@Slf4j
public class XVertex extends XElement implements Vertex {
    public static XVertex of(int id, final RdbmsGraph graph) {
        return new XVertex(id, graph);
    }

    XVertex(int id, final RdbmsGraph graph) {
        super(id, graph);
    }
    @Override
    public Iterable<Edge> getEdges(Direction direction, String... labels) {
        log.info("getEdges for {} {}", this, direction);
        if (direction.equals(Direction.OUT)) {
//            return newArrayList(getEdges(impl.outEdges, asList(labels)));
        }

        return null;
    }
    // =================================
    // annoyingly, an empty label list is a special case for 'all'
    private Iterable<Edge> getEdges(Iterable<Integer> edgeIDs, final Collection<String> labels) {
//        Iterable<Edge> i = transform(edgeIDs, lookupEdge);
//        if (labels.isEmpty())
//            return i;
//        return filter(i, new Predicate<Edge>() {
//            @Override
//            public boolean apply(Edge e) {
//                return labels.contains(e.getLabel());
//            }
//        });

        return Collections.emptyList();
    }
    // =================================
    @Override
    public Iterable<Vertex> getVertices(Direction direction, String... labels) {
        return null;
    }
    @Override
    public VertexQuery query() {
        return null;
    }
    @Override
    public Edge addEdge(String label, Vertex inVertex) {
        return graph.addEdge(null, this, inVertex, label);
    }
    // =================================
    void addOutEdge(int edgeId) {
//        qOutEdges.add(edgeId);
    }
    // =================================
    void addInEdge(int edgeId) {
//        qInEdges.add(edgeId);
    }
    @Override
    public <T> T getProperty(String key) {
        return null;
    }
    @Override
    public Set<String> getPropertyKeys() {
//        return newHashSet(impl.properties.keySet());
        return Collections.emptySet();
    }
    @Override
    public void setProperty(String key, Object value) {
//        graph.getCache()
//        Mutable impl = graph.getCache().getVertexImpl(id);
        // re-point impl at revised version
    }
    @Override
    public <T> T removeProperty(String key) {
        // re-point impl at revised version
        return null;
    }
    @Override
    public void remove() {
        // re-point impl at null?
    }
    // =================================
    @Override
    public int hashCode() {
        return id;
    }
    @Override
    public String toString() {
        return StringFactory.vertexString(this);
    }
    // =================================
    static class Mutable {

        private final Set<Integer> outEdges = newHashSet();
        private final Set<Integer> inEdges = newHashSet();
        private final Map<String, Object> properties = newHashMap();
    }

    // Use IntSet?
}
