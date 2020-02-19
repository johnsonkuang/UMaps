package graph;

import java.util.*;

/**
 <b>Graph</b> represents a mutable directed labeled graph ADT made up of
 Nodes and DirectedEdges.
 */

public final class Graph{
    /**
     * Abstract Function:
     * AF(this): a collection of nodes and edges where all the nodes are given by
     *              {n_1, n_2, n_3...n_n} and all edges are given by {n_1.edges(), n_2.edges() ... n_n.edges()}
     *
     * Rep Invariant:
     *      nodes != null &&
     *      all elements in nodes != null
     *      no nodes in nodes have the same label
     */

    /**
     * setting for expensive (runtime O(n) or above checks in Graph ADT
     */
    private static final boolean DEBUG = false;
    private Set<Node>  nodes;

    /**
     * @spec.effects Constructs an empty graph
     */
    public Graph(){
        nodes = new LinkedHashSet<>();
        checkRep();
    }

    /**
     * @return a list of all the nodes in this graph, if the graph is empty return an empty list
     */
    public Set<Node> getNodes(){
        return Collections.unmodifiableSet(nodes);
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
        assert n != null: "Trying to add null into the graph";
        checkRep();
        boolean success = nodes.add(n);
        checkRep();
        return success;
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
     * @spec.requires start != null &amp;&amp; end != null &amp;&amp; !contains(edges, newEdge, label) &amp;&amp; this graph.contains(start)
     *                this graph.contains(end)
     * @spec.modifies start, newEdge
     * @spec.effects creates a new edge, newEdge, and adds it into the set of edges stored in 'start'
     */
    public boolean addEdge(Node start, Node end, String label){
        checkRep();
        if(nodes.contains(start) && nodes.contains(end)){
            return start.addEdge(end, label);
        }
        return false;
    }

    /**
     * Return a node based on its label
     * @param label the label of the node
     * @return the node with the specified label
     * @throws IllegalArgumentException if node with label 'label' does not exist in nodes
     */
    public Node getNode(String label){
        for(Node n: nodes){
            if(n.getLabel().equals(label)){
                return n;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Checks whether given node is in the graph
     * @param n node to check in graph
     * @return true if found, false otherwise
     */
    public boolean containsNode(Node n){
        return nodes.contains(n);
    }

    private void checkRep(){
        assert nodes != null : "nodes is null";
        if(DEBUG) {
            Set<String> uniqueLabels = new HashSet<>();
            for(Node n: nodes){
                assert n != null: "node in nodes is null";
                assert !uniqueLabels.contains(n.getLabel()): "two edges have the same label";
                uniqueLabels.add(n.getLabel());
            }
        }
    }
}