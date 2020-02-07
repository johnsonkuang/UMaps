package graph.implTest;

import graph.Graph;
import graph.Node;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;


public class GraphTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    //Graphs for convenience
    Graph empty = new Graph();
    Graph singleNode = new Graph();
    Graph connectedGraph = new Graph();

    //Nodes for convenience
    Node[] nodes =  new Node[]{
            new Node("node"),
            new Node("parent"),
            new Node("child"),
            new Node("isolated node"),
            new Node("singly connected node")
    };

    @Before
    public void setUp(){
        singleNode.addNode(nodes[0]);
        connectedGraph.addNode(nodes[1]);
        connectedGraph.addNode(nodes[2]);
        connectedGraph.addEdge(nodes[1], nodes[2], "connecting edge");
    }

    @Test
    public void testGetNodes() {

    }

    @Test
    public void testAddNode() {
    }

    @Test
    public void testAddEdge() {
    }

    @Test
    public void testListNodes() {
    }

    @Test
    public void testListChildren() {
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }
}
