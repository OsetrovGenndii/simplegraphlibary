package com.osetrov.gennadii.simpliest.api;

import javax.annotation.Nonnull;
import java.util.List;

public interface Graph<T> {
    @Nonnull
    Vertex<T> addVertex(@Nonnull T value);

    void addEdge(@Nonnull Vertex<T> from, @Nonnull Vertex<T> to);

    @Nonnull
    List<Edge<T>> getPath(@Nonnull Vertex<T> from, @Nonnull Vertex<T> to);
}
