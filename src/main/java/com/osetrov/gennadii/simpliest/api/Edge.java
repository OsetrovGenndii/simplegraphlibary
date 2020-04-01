package com.osetrov.gennadii.simpliest.api;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Edge<T> {
    private final Vertex<T> from;
    private final Vertex<T> to;

    public Edge(@Nonnull Vertex<T> from, @Nonnull Vertex<T> to) {
        this.from = from;
        this.to = to;
    }

    @Nonnull
    public Vertex<T> getFrom() {
        return from;
    }

    @Nonnull
    public Vertex<T> getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return from == edge.from &&
                to == edge.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
