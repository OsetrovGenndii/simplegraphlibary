package com.osetrov.gennadii.simpliest;

import com.osetrov.gennadii.simpliest.api.Edge;
import com.osetrov.gennadii.simpliest.api.Graph;
import com.osetrov.gennadii.simpliest.api.Vertex;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSimpleGraphImpl<T> implements Graph<T> {
    private final Map<Vertex<T>, Set<Vertex<T>>> graph = new HashMap<>();

    private static final class VertexImpl<R> implements Vertex<R> {
        private final R value;

        private VertexImpl(R value) {
            this.value = value;
        }

        @Nonnull
        @Override
        public R getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "VertexImpl{" +
                    "value=" + value +
                    '}';
        }
    }

    @Nonnull
    @Override
    public Vertex<T> addVertex(@Nonnull T value) {
        Vertex<T> vertex = new VertexImpl<>(value);
        graph.put(vertex, new HashSet<>());
        return vertex;
    }

    public void addEdge(@Nonnull Vertex<T> from, @Nonnull Vertex<T> to) {
        addEdgeToGraph(from, to);
    }

    protected abstract void addEdgeToGraph(@Nonnull Vertex<T> from, @Nonnull Vertex<T> to);

    protected void addEdgeToVertex(@Nonnull Vertex<T> vertex, @Nonnull Vertex<T> edgeTo) {
        Set<Vertex<T>> vertices = getVertexAdjacentSet(vertex);
        vertices.add(edgeTo);
    }

    @Nonnull
    @Override
    public List<Edge<T>> getPath(@Nonnull Vertex<T> from, @Nonnull Vertex<T> to) {
        List<Edge<T>> path = getPath(from, to, new HashSet<>(Collections.singleton(from)));
        Collections.reverse(path);
        return path;
    }

    @Nonnull
    private List<Edge<T>> getPath(@Nonnull Vertex<T> from, @Nonnull Vertex<T> to, @Nonnull Set<Vertex<T>> known) {
        Set<Vertex<T>> adjacent = getVertexAdjacentSet(from);
        for (Vertex<T> vertex : adjacent) {
            if (vertex.equals(to)) {
                return new ArrayList<>(Collections.singleton(new Edge<>(from, to)));
            }
            if (!known.contains(vertex)) {
                known.add(vertex);
                List<Edge<T>> path = getPath(vertex, to, known);
                if (!path.isEmpty()) {
                    path.add(new Edge<>(from, vertex));
                    return path;
                }
            }
        }
        return Collections.emptyList();
    }

    @Nonnull
    private Set<Vertex<T>> getVertexAdjacentSet(@Nonnull Vertex<T> vertex) {
        Set<Vertex<T>> vertices = graph.get(vertex);
        if (vertices == null) {
            throw new IllegalArgumentException(vertex + " wasn't found in the graph. Has been it added to the graph?");
        }
        return vertices;
    }
}
