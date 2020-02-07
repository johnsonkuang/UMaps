package graph;

import java.util.*;

/**
 <b>Graph</b> represents a mutable directed labeled graph ADT made up of
 Nodes and DirectedEdges.
 */

public final class Graph{

    /**
     * @spec.effects Constructs an empty graph
     */
    public Graph(){
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * @param nodes a list of nodes to be contained in the new Graph, all node contained in
     *              'nodes' must be initialized. Nodes can have connections initialized as well.
     * @spec.effects Constructs a new Graph using 'nodes' as part of the graph.
     */
    public Graph(List<Node> nodes) {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * @return a list of all the nodes in this graph, if the graph is empty return an empty list
     */
    public List<Node> getNodes(){
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * Adds a node into the graph and returns true on success. If a node with the same label exists in the graph
     * returns false
     *
     * @param n, the node to be added into the graph
     * @return true if successfully added, false otherwise
     * @spec.requires 'n' != null
     * @spec.modifies this graph
     * @spec.effects this graph.nodes.contains(n)
     *
     */
    public boolean addNode(Node n){
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * Creates a Edge between two Node objects that exist in the graph already
     *
     * <p>Definitions: Let a "parent node" be the start point of a DirectedEdge and a "child"
     * be the end point of that same edge. Let "edges" be the set of all the edges in the graph.</p>
     *
     * <p>For edges E, a new edge nE, and a label l, let containsLabel(E, nE, l) be defined as edges E contains an edge.equals(nE)
     * with label l already.</p>
     *
     * @param start the parent node
     * @param end the child node
     * @param label the label of this edge
     * @return true if successfully added, false otherwise
     * @spec.requires start != null && end != null && !contains(edges, newEdge, label) && this graph.contains(start)
     *                this graph.contains(end)
     * @spec.modifies start, newEdge
     * @spec.effects creates a new edge, newEdge, and adds it into the set of edges stored in 'start'
     */
    public boolean addEdge(Node start, Node end, String label){
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * Lists out all nodes in the graph in alphabetical order
     * @return a string representation of all the nodes in the graph
     */
    public String listNodes(){
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * Lists out all the child nodes of a single node
     *
     * <p>Format: the children of parentNode in graphName are:
     * and is followed, on the same line, by a space-separated list of entries of the form node(edgeLabel),
     * where node is a node in graphName to which there is an edge from parentNode and edgeLabel is the label on that
     * edge. If there are multiple edges between two nodes, there should be a separate node(edgeLabel) entry for each edge.
     * The nodes should appear in lexicographical (alphabetical) order by node name and secondarily by edge label,
     * e.g. firstNode(someEdge) secondNode(edgeA) secondNode(edgeB) secondNode(edgeC) thirdNode(anotherEdge). </p>
     *
     * @param parent a node in the graph
     * @return a string representation of all the children of the parent node and their edges
     * @spec.requires parent != null
     *
     */
    public String listChildren(Node parent){
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * Standard equality operation.
     *
     * @param obj the object to be compared for equality
     * @return true if and only if 'obj' is an instance of a Graph and 'this' and 'obj' represent
     * the same graph.
     */
    @Override
    public boolean equals(Object obj) {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    @Override
    public int hashCode() {
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }
}