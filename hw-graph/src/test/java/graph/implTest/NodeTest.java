package graph.implTest;

import graph.Node;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class NodeTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Test
    public void testAddEdge() {
        Node n = new Node("n1");
        Node c = new Node("n2");
        Node p = new Node("n3");
        assertFalse("Edge cannot be made between null and a node", n.addEdge(null, "e1"));
        assertFalse("Edge cannot be an empty label", n.addEdge(c, ""));
        assertTrue("Edge could not be added", n.addEdge(c, "e1"));
        assertTrue(n.addEdge(c, "e2"));
        assertFalse("Edge cannot be the same label as one that exists between the two nodes", n.addEdge(c, "e1"));
    }
}