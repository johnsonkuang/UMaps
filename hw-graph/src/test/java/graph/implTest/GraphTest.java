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

    private void eq(Graph g, String target){
        String t = g.listNodes();
        assertEquals(target, t);
    }

    private void eq(Graph g, String target, String message) {
        String t = g.listNodes();
        assertEquals(message, target, t);
    }

    private void eqChildren(Graph g, Node parent, String target, String message){
        String t = g.listChildren(parent);
        assertEquals(message, target, t);
    }

    List<Node> lst_single, lst_double, lst_connected, lst_singlyConnected;

    // SetUp Method depends on Graph constructor and addEdge method
    @Before
    public void setUp(){
        empty = new Graph();



        lst_single = new ArrayList<>();
        lst_single.add(nodes[0]);
        singleNode = new Graph(lst_single);

        lst_double = new ArrayList<>();
        lst_double.add(nodes[5]);
        lst_double.add(nodes[6]);
        doubleNode = new Graph(lst_double);

        lst_connected = new ArrayList<>();
        lst_connected.add(nodes[1]);
        lst_connected.add(nodes[2]);
        connectedGraph = new Graph(lst_connected);
        connectedGraph.addEdge(nodes[1], nodes[2], "e1");

        lst_singlyConnected = new ArrayList<>();
        lst_singlyConnected.add(nodes[4]);
        singlyConnectedNode = new Graph(lst_singlyConnected);
        singlyConnectedNode.addEdge(nodes[4], nodes[4], "circular connection");
    }

    // Constructors
    @Test
    public void testEmptyCtor(){
        eq(new Graph(), "Nodes:");
    }

    @Test
    public void testOneArgCtor(){
        eq(singleNode, "Nodes: node");
        eq(connectedGraph, "Nodes: child parent");
    }

    // Test observers
    @Test
    public void testGetNodes() {
        assertArrayEquals(empty.getNodes().toArray(), new ArrayList<Node>().toArray());
        assertArrayEquals(singleNode.getNodes().toArray(), lst_single.toArray());
        assertArrayEquals(connectedGraph.getNodes().toArray(), lst_connected.toArray());
    }

    @Test
    public void testAddNode() {
        Graph singleNodeTest =  new Graph();
        singleNodeTest.addNode(nodes[0]);
        eq(singleNodeTest, singleNode.listNodes());

        List<Node> lst_existing = new ArrayList<>();
        lst_existing.add(nodes[5]);
        Graph doubleNodeTest = new Graph(lst_existing);
        doubleNodeTest.addNode(nodes[6]);
        eq(doubleNodeTest, doubleNode.listNodes());

        //can't add two of the same nodes
        assertFalse(singleNodeTest.addNode(nodes[0]));
        assertFalse(doubleNodeTest.addNode(nodes[5]));
    }

    @Test
    public void testAddEdge() {
        List<Node> lst_existing = new ArrayList<>();
        Graph edgeTest = new Graph();
        assertFalse("Edge was added when both nodes are not in the graph",edgeTest.addEdge(nodes[1],nodes[2],"e1"));
        edgeTest.addNode(nodes[1]);
        assertFalse("Edge was added when a node was not in the graph", edgeTest.addEdge(nodes[1], nodes[2], "e1"));
        edgeTest.addNode(nodes[2]);
        assertTrue("Edge could not be added when both nodes were in the graph", edgeTest.addEdge(nodes[1], nodes[2], "e1"));
        assertTrue("Edge could not be added when both nodes are in the graph and the label is different", edgeTest.addEdge(nodes[1], nodes[2], "e2"));
        assertFalse("Edge with the same label already exists in the graph", edgeTest.addEdge(nodes[1], nodes[2], "e1"));
        assertFalse("Edge cannot be made with node not in the graph", edgeTest.addEdge(nodes[1], nodes[3], "e2"));
    }

    @Test
    public void testListNodes() {
        eq(empty, "Nodes:");
        eq(singleNode, "Nodes: node");
        eq(doubleNode, "Nodes: A B");
        eq(connectedGraph, "Nodes: child parent");
    }

    @Test
    public void testListChildren() {
        eqChildren(connectedGraph, nodes[1], "the children of parent: child(e1)",
                "ListChildren() did not list the children of parent node correctly");
        eqChildren(singlyConnectedNode, nodes[4], "the children of singly connected node: singly connected node(circular connection)",
                "ListChildren() did not list the children of a circularly connected node correctly");
    }
}
