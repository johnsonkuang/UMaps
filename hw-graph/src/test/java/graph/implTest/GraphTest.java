package graph.implTest;

import graph.Graph;
import graph.Node;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GraphTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    //Graphs for convenience
    Graph empty;
    Graph singleNode;
    Graph doubleNode;
    Graph connectedGraph;
    Graph singlyConnectedNode;

    //Nodes for convenience
    Node[] nodes =  new Node[]{
            new Node("node"),
            new Node("parent"),
            new Node("child"),
            new Node("isolated node"),
            new Node("singly connected node"),
            new Node("A"),
            new Node("B")
    };

    List<Node> lst_single, lst_double, lst_connected, lst_singlyConnected;

    // SetUp Method depends on Graph constructor and addEdge method
    @Before
    public void setUp(){
        lst_single = new ArrayList<>();
        lst_single.add(nodes[0]);

        lst_connected = new ArrayList<>();
        lst_connected.add(nodes[1]);
        lst_connected.add(nodes[2]);

        empty = new Graph();

        singleNode = new Graph();
        singleNode.addNode(nodes[0]);

        doubleNode = new Graph();
        doubleNode.addNode(nodes[5]);
        doubleNode.addNode(nodes[6]);

        connectedGraph = new Graph();
        connectedGraph.addNode(nodes[1]);
        connectedGraph.addNode(nodes[2]);
        connectedGraph.addEdge(nodes[1], nodes[2], "e1");

        singlyConnectedNode = new Graph();
        singlyConnectedNode.addNode(nodes[4]);
        singlyConnectedNode.addEdge(nodes[4], nodes[4], "circular connection");
    }

    // Test observers
    @Test
    public void testGetNodes() {
        List<Node> emptyList = new ArrayList<>();
        assertArrayEquals(empty.getNodes().toArray(), emptyList.toArray());
        assertArrayEquals(singleNode.getNodes().toArray(), lst_single.toArray());
        assertArrayEquals(connectedGraph.getNodes().toArray(), lst_connected.toArray());
    }

    @Test
    public void testAddNode() {
        Graph singleNodeTest =  new Graph();
        assertTrue(singleNodeTest.addNode(nodes[0]));

        Graph doubleNodeTest = new Graph();
        assertTrue(doubleNodeTest.addNode(nodes[5]));
        assertTrue(doubleNodeTest.addNode(nodes[6]));

        //can't add two of the same nodes
        assertFalse(singleNodeTest.addNode(nodes[0]));
        assertFalse(doubleNodeTest.addNode(nodes[5]));
    }

    @Test
    public void testAddEdge() {
        Graph edgeTest = new Graph();
        Node parent = new Node("parent");
        Node child = new Node("child");
        Node child2 = new Node("child2");
        assertFalse("Edge was added when both nodes are not in the graph",edgeTest.addEdge(parent,child,"e1"));
        edgeTest.addNode(parent);
        assertFalse("Edge was added when a node was not in the graph", edgeTest.addEdge(parent, child, "e1"));
        edgeTest.addNode(child);
        edgeTest.addNode(child2);
        assertTrue("Edge could not be added when both nodes were in the graph", edgeTest.addEdge(parent, child, "e1"));
        assertTrue("Edge could not be added between same nodes with different label", edgeTest.addEdge(parent, child, "e2"));
        assertTrue("Edge could not be added between a similar parent node and a different child",
                edgeTest.addEdge(parent, child2, "e3"));
        assertFalse("Edge with the same label already exists in the graph", edgeTest.addEdge(parent, child, "e1"));
        assertFalse("Edge cannot be made with node not in the graph", edgeTest.addEdge(parent, nodes[3], "e2"));
    }
}
