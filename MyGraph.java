import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class MyGraph<V, E extends Integer> {

    private final boolean isDirected;
    private final Map<V, InnerVertex<V>> vertexMap;
    //--------end of nested classes-----------

    public MyGraph(boolean directed) {
        isDirected = directed;
        vertexMap = new HashMap<>();
    }

    public MyGraph() {
        this(false);
    }

    /**
     * Returns the number of vertices in the graph
     */
    public int numVertices() {
        return vertexMap.size();
    }

    /**
     * Returns the vertices of the graph as an iterable collection
     */
    public Iterable<Vertex<V>> vertices() {
        LinkedList<Vertex<V>> answer = new LinkedList<>();

        for (InnerVertex<V> v : vertexMap.values()) {
            answer.add(v);
        }
        return answer;
    }

    /**
     * Returns the number of edges in the graph
     */
    public int numEdges() {
        int count = 0;

        if (isDirected) {

            for (InnerVertex<V> v : vertexMap.values()) {
                count += v.outDegree() + v.inDegree();
            }

        } else {
            for (InnerVertex<V> v : vertexMap.values()) {
                count += v.outDegree();
            }
        }
        return count / 2;
    }

    /**
     * Returns the edges of the graph as an iterable collection
     */
    public Iterable<Edge<E>> edges() {
        LinkedList<Edge<E>> answer = new LinkedList<>();

        for (InnerVertex<V> v : vertexMap.values()) {
            for (Edge<E> e : v.getOutgoing().values()) {
                answer.add(e);
            }
        }
        return answer;
    }

    private InnerVertex<V> validate(Vertex<V> v) {
        if (!(v instanceof InnerVertex)) {
            throw new IllegalArgumentException("Invalid vertex v");
        }

        InnerVertex<V> w = (InnerVertex<V>) v;
        return w;
    }

    public int outDegree(Vertex<V> v) {
        InnerVertex<V> w = this.validate(v);
        return w.getOutgoing().size();
    }

    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        InnerVertex<V> w = this.validate(v);
        return w.getOutgoing().values();
    }

    public int inDegree(Vertex<V> v) {
        InnerVertex<V> w = this.validate(v);
        return w.getIncoming().size();
    }

    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        InnerVertex<V> w = this.validate(v);
        return w.getIncoming().values();
    }

    /**
     * Return the Vertex<V> object with element x, or null if not present
     */
    public Vertex<V> getVertex(V x) {
        return this.vertexMap.get(x);
    }

    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) {
        InnerVertex<V> o = validate(u);
        InnerVertex<V> d = validate(v);

        return o.getOutgoing().get(d);
    }

    private InnerEdge<E> validate(Edge<E> e) {
        if (!(e instanceof InnerEdge)) {
            throw new IllegalArgumentException("Invalid edge object passed");
        }

        InnerEdge<E> e2 = (InnerEdge<E>) e;
        return e2;
    }

    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge<E> e2 = validate(e);
        return e2.getEndPoints();
    }

    /**
     * Returns the vertex that is opposite vertex v on edge e.
     */
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {

        InnerEdge<E> edge = validate(e);

        Vertex<V>[] endPoints = edge.getEndPoints();

        if (endPoints[0] == v) {
            return endPoints[1];
        } else if (endPoints[1] == v) {
            return endPoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to the edge");
        }

    }

    public boolean containsVertex(V element) {
        return this.vertexMap.containsKey(element);
    }

    /**
     * Inserts and returns a new vertex with the given element.
     */
    public Vertex<V> insertVertex(V element) throws IllegalArgumentException {
        InnerVertex<V> newVertex = new InnerVertex<>(element, isDirected);

        if (vertexMap.containsKey(element)) {
            throw new IllegalArgumentException("Vertex " + element + " already exists!");
        }
        vertexMap.put(element, newVertex);
        return newVertex;
    }

    /**
     * Inserts and returns a new edge between u and v, storing given element.
     */
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {

        if (getEdge(u, v) != null) {
            throw new IllegalArgumentException("Edge from u and v lready exists");
        }

        InnerVertex<V> o = validate(u);
        InnerVertex<V> d = validate(v);

        InnerEdge<E> newEdge = new InnerEdge<>(o, d, element);

        o.getOutgoing().put(v, newEdge);
        d.getIncoming().put(u, newEdge);

        return newEdge;
    }

    /**
     * Removes a vertex and all its incident edges from the graph.
     */
    public void removeVertex(Vertex<V> x) throws IllegalArgumentException {
        InnerVertex<V> u = validate(x);

        //removing each edge incident to v
        for (Edge<E> e : u.getOutgoing().values()) {
            removeEdge(e);
        }

        for (Edge<E> e : u.getIncoming().values()) {
            removeEdge(e);
        }
    }

    /**
     * Removes a edge from the graph.
     */
    public void removeEdge(Edge<E> y) throws IllegalArgumentException {
        InnerEdge<E> e = validate(y);

        Vertex<V>[] ends = e.getEndPoints();

        InnerVertex<V> o = validate(ends[0]);
        InnerVertex<V> d = validate(ends[1]);

        o.getOutgoing().remove(d);
        d.getIncoming().remove(o);
    }

    public Map<Vertex<V>, Vertex<V>> dijkstra(Vertex<V> s, Map<Vertex<V>, Integer> d) {
        d.clear();
        HashMap<Vertex<V>, Integer> D = new HashMap<>();    // the best distance found so far
        HashMap<Vertex<V>, Vertex<V>> parent = new HashMap<>();    // shortest distance from s

        MinHeap<Integer, Vertex<V>> pq = new MinHeap<>();
        Map<Vertex<V>, Entry<Integer, Vertex<V>>> vertex_entry_map = new HashMap<>();

        for (Vertex<V> u : this.vertices()) {
            if (u == s) {
                D.put(u, 0);
                vertex_entry_map.put(u, pq.insert(0, u));
                parent.put(u, null);
            } else {
                D.put(u, Integer.MAX_VALUE);
                vertex_entry_map.put(u, pq.insert(Integer.MAX_VALUE, u));
            }
        }


        while (d.size() != this.numVertices()) {
            Entry<Integer, Vertex<V>> shortest = pq.removeMin();

            int distance = shortest.getKey();
            Vertex<V> u = shortest.getValue();

            d.put(u, distance);

            //performing relaxation on every edge u-v
            for (Edge<E> e : this.outgoingEdges(u)) {
                Vertex<V> v = this.opposite(u, e);

                if (!d.containsKey(v)) {
                    int wgt = e.getElement();

                    if (d.get(u) + wgt < D.get(v)) {
                        int newDist = d.get(u) + wgt;

                        D.put(v, newDist);
                        pq.replaceKey(vertex_entry_map.get(v), newDist);
                        parent.put(v, u);
                    }
                }
            }
        }
        return parent;

    }

    /**
     * Returns a string representation of the graph. This is used only for
     * debugging; do not rely on the string representation.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //     sb.append("Edges:");
        //     for (Edge<E> e : edges) {
        //       Vertex<V>[] verts = endVertices(e);
        //       sb.append(String.format(" (%s->%s, %s)", verts[0].getElement(), verts[1].getElement(), e.getElement()));
        //     }
        //     sb.append("\n");
        for (Vertex<V> v : vertexMap.values()) {
            sb.append("Vertex " + v.getElement() + "\n");

            if (isDirected) {
                sb.append(" [outgoing]");
            }

            sb.append(" " + outDegree(v) + " adjacencies:");

            for (Edge<E> e : outgoingEdges(v)) {
                sb.append(String.format(" (%s, %s)", opposite(v, e).getElement(), e.getElement()));
            }
            sb.append("\n");

            if (isDirected) {
                sb.append(" [incoming]");
                sb.append(" " + inDegree(v) + " adjacencies:");

                for (Edge<E> e : incomingEdges(v)) {
                    sb.append(String.format(" (%s, %s)", opposite(v, e).getElement(), e.getElement()));
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    //------Nested Vertex and Edge classes------
    private class InnerVertex<V> implements Vertex<V> {

        private V element;

        private Map<Vertex<V>, Edge<E>> outgoing;
        private Map<Vertex<V>, Edge<E>> incoming;

        public InnerVertex(V element, boolean isDirected) {
            this.element = element;
            outgoing = new HashMap<>();

            if (isDirected) {
                incoming = new HashMap<>();
            } else {
                incoming = outgoing;
            }
        }

        public V getElement() {
            return this.element;
        }

        public Map<Vertex<V>, Edge<E>> getOutgoing() {
            return this.outgoing;
        }

        public Map<Vertex<V>, Edge<E>> getIncoming() {
            return this.incoming;
        }

        public int outDegree() {
            return this.outgoing.size();
        }

        public int inDegree() {
            return this.incoming.size();
        }

        @Override
        public int hashCode() {
            return this.element.hashCode();
        }

        public boolean equals(Object o) {
            Vertex<V> v = (InnerVertex<V>) o;

            return element.equals(v.getElement());
        }

        public String toString() {
            return this.element.toString();
        }
    }

    private class InnerEdge<E> implements Edge<E> {

        private final E element;

        private final Vertex<V>[] endPoints;

        public InnerEdge(Vertex<V> u, Vertex<V> v, E element) {
            this.element = element;
            endPoints = (Vertex<V>[]) new Vertex[]{u, v};
        }

        public E getElement() {
            return this.element;
        }

        public Vertex<V>[] getEndPoints() {
            return this.endPoints;
        }
    }

}
