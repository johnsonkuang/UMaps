package graph;

/**
 * <b>Node</b> represents a mutable element in the Graph ADT
 */
public final class Node {
    /**
     *
     * @param label the label of this node
     * @spec.effects Constructs a new Node labeled 'label'
     */
    public Node(String label){
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }

    /**
     * Adds a new DirectedEdge into this.edges setting this as the parent node
     *
     * @param end child node of edge
     * @param label label of new edge
     * @return true if added successfully, false otherwise
     * @spec.requires end != null
     * @spec.modifies this.edges
     * @spec.effects this.edges.contains(newEdge)
     */
    public boolean addEdge(Node end, String label){
        // TODO: Fill in this method, then remove the RuntimeException
        throw new RuntimeException("Method has not been implemented");
    }
}
