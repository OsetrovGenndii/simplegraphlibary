package com.osetrov.gennadii.simpliest;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.osetrov.gennadii.simpliest.api.Edge;
import com.osetrov.gennadii.simpliest.api.Graph;
import com.osetrov.gennadii.simpliest.api.Vertex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class UndirectedGraphImplTest {


    @Test
    public void simpleUndirectedGraph() {
        TestCase testCase = new TestCase(
                l(
                        l(0, 1),
                        l(1, 0)
                ),
                new UndirectedSimpleGraphImpl<>()
        );
        testCase.assertPathExist(0, 1);
        testCase.assertPathExist(1, 0);
    }


    @Test
    public void simpleDirectedGraph() {
        TestCase testCase = new TestCase(
                l(
                        l(0, 0),
                        l(1, 0)
                ),
                new DirectedSimpleGraphImpl<>()
        );
        testCase.assertPathDoesntExist(0, 1);
        testCase.assertPathExist(1, 0);
    }

    @Test
    public void degenerateUndirectedGraph() {
        TestCase testCase = new TestCase(
                l(
                        l(1)
                ),
                new UndirectedSimpleGraphImpl<>()
        );
        testCase.assertPathExist(0, 0);
    }

    @Test
    public void degenerateUndirectedGraph2() {
        TestCase testCase = new TestCase(
                l(
                        l(0)
                ),
                new UndirectedSimpleGraphImpl<>()
        );
        testCase.assertPathDoesntExist(0, 0);
    }

    @Test
    public void degenerateDirectedGraph2() {
        TestCase testCase = new TestCase(
                l(
                        l(0)
                ),
                new DirectedSimpleGraphImpl<>()
        );
        testCase.assertPathDoesntExist(0, 0);
    }

    @Test
    public void degenerateDirectedGraph() {
        TestCase testCase = new TestCase(
                l(
                        l(1)
                ),
                new DirectedSimpleGraphImpl<>()
        );
        testCase.assertPathExist(0, 0);
    }

    @Test
    public void undirictedGraph() {
        TestCase testCase = new TestCase(
                l(
                        l(0, 0, 0, 1),
                        l(0, 0, 0, 1),
                        l(0, 0, 0, 1),
                        l(1, 1, 1, 0)
                ),
                new UndirectedSimpleGraphImpl<>()
        );
        testCase.assertPathExist(0, 1);
        testCase.assertPathExist(0, 2);
        testCase.assertPathExist(0, 3);
    }

    @Test
    public void directedGraph() {
        TestCase testCase = new TestCase(
                l(
                        l(0, 1, 0, 1),
                        l(0, 0, 0, 0),
                        l(0, 0, 0, 1),
                        l(0, 1, 1, 0)
                ),
                new DirectedSimpleGraphImpl<>()
        );
        testCase.assertPathExist(0, 3);
        testCase.assertPathExist(0, 2);
        testCase.assertPathExist(0, 1);

        testCase.assertPathDoesntExist(1, 0);
        testCase.assertPathDoesntExist(1, 2);
        testCase.assertPathDoesntExist(1, 3);
    }

    @SafeVarargs
    private static <T> List<T> l(T... values) {
        return Arrays.asList(values);
    }

    private static class TestCase {
        List<List<Integer>> matrix;
        Graph<Boolean> graph;
        BiMap<Integer, Vertex<Boolean>> vertexMap = HashBiMap.create();

        TestCase(List<List<Integer>> adjacencyMatrix, Graph<Boolean> graph) {
            this.graph = graph;
            this.matrix = adjacencyMatrix;
            for (int i = 0; i < adjacencyMatrix.size(); i++) {
                Vertex<Boolean> vertex = graph.addVertex(true);
                vertexMap.put(i, vertex);
            }
            for (int j = 0; j < adjacencyMatrix.size(); j++) {
                List<Integer> row = adjacencyMatrix.get(j);
                for (int i = 0; i < row.size(); i++) {
                    if (row.get(i) == 1) {
                        graph.addEdge(vertexMap.get(j), vertexMap.get(i));
                    }
                }
            }
        }

        void assertPathExist(int from, int to) {
            Vertex<Boolean> fromVertex = vertexMap.get(from);
            Vertex<Boolean> toVertex = vertexMap.get(to);

            List<Edge<Boolean>> path = graph.getPath(fromVertex, toVertex);
            if (path.isEmpty()) {
                fail();
            }
            for (Edge edge : path) {
                if (matrix.get(vertexMap.inverse().get(edge.getFrom())).get(vertexMap.inverse().get(edge.getTo())) != 1) {
                    fail();
                }
            }
        }

        void assertPathDoesntExist(int from, int to) {
            if (!graph.getPath(vertexMap.get(from), vertexMap.get(to)).isEmpty())
                fail();
        }

    }
}