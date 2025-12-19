/**
 * @author Ayush Chachan
 */
public interface Graph<V, E> {
    int numVertices();      // Returns the number of vertices of the graph.

    Iterable<Vertex<V>> vertices();    // Returns an iteration of all the vertices of the graph.

    int numEdges();    // Returns the number of edges of the graph.

    Iterable<Edge<E>> edges();    // Returns an iteration of all the edges of the graph.

    Edge<E> getEdge(Vertex<V> u, Vertex<V> v);      // Returns the edge from vertex u to vertex v, if one exists;
    //otherwise return null. For an undirected graph, there is no
    //difference between getEdge(u, v) and getEdge(v, u).

    Vertex<V>[] endVertices(Edge<E> e);     // Returns an array containing the two endpoint vertices of
    //edge e. If the graph is directed, the first vertex is the origin
    //and the second is the destination.

    Vertex<V> opposite(Vertex<V> v, Edge<E> e);     // For edge e incident to vertex v, returns the other vertex of
    //the edge; an error occurs if e is not incident to v.

    int outDegree(Vertex<V> v);    // Returns the number of outgoing edges from vertex v.

    int inDegree(Vertex<V> v);      // Returns the number of incoming edges to vertex v. For
    //an undirected graph, this returns the same value as does
    //outDegree(v).

    Iterable<Edge<E>> outgoingEdges(Vertex<V> v);   // Returns an iteration of all outgoing edges from vertex v.

    Iterable<Edge<E>> incomingEdges(Vertex<V> v);   // Returns an iteration of all incoming edges to vertex v. For
    //an undirected graph, this returns the same collection as
    //does outgoingEdges(v).

    Vertex<V> insertVertex(V x);    // Creates and returns a new Vertex storing element x.

    Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E x);  // Creates and returns a new Edge from vertex u to vertex v,
    // storing element x; an error occurs if there already exists an
    //edge from u to v.

    void removeVertex(Vertex<V> v);    // Removes vertex v and all its incident edges from the graph.

    void removeEdge(Edge<E> e);    // Removes edge e from the graph.
}
