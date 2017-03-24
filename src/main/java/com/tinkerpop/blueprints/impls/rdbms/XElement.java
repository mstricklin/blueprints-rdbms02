// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package com.tinkerpop.blueprints.impls.rdbms;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.util.ElementHelper;

public abstract class XElement implements Element, Keyed {
    XElement(int id_, final RdbmsGraph graph_) {
        id = id_;
        graph = graph_;
    }
    @Override
    public Object getId() {
        return id;
    }
    public int getRawId() {
        return id;
    }
    @Override
    public boolean equals(final Object object) {
        return ElementHelper.areEqual(this, object);
    }


    protected final int id;
    protected final RdbmsGraph graph;
}
