package com.osetrov.gennadii.simpliest;

import com.osetrov.gennadii.simpliest.api.Vertex;

import javax.annotation.Nonnull;

public class DirectedSimpleGraphImpl<T> extends AbstractSimpleGraphImpl<T> {
    @Override
    protected void addEdgeToGraph(@Nonnull Vertex<T> from, @Nonnull Vertex<T> to) {
        addEdgeToVertex(from, to);
    }
}
