/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel.specTest;

import graph.Graph;
import graph.Node;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    public static void main(String[] args) {
        // You only need a main() method if you choose to implement
        // the 'interactive' test driver, as seen with GraphTestDriver's sample
        // code. You may also delete this method entirely if you don't want to
        // use the interactive test driver.
    }

    /**
     * String -> MarvelPaths: maps the names of graphs to the actual graph
     **/
    private final Map<String, Graph> graphs = new HashMap<String, Graph>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "LoadGraph":
                    loadGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":

                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void loadGraph(List<String> arguments) throws FileNotFoundException{
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String fileName = arguments.get(1);

        loadGraph(graphName, fileName);
    }

    private void loadGraph(String graphName, String fileName) throws FileNotFoundException{
        graphs.put(graphName, MarvelPaths.createGraph(fileName));
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments){
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1);
        start = start.replaceAll("_", " ");
        String dest = arguments.get(2);
        dest = dest.replaceAll("_", " ");

        findPath(graphName, start, dest);
    }

    private void findPath(String graphName, String start, String dest) {
        Graph g = graphs.get(graphName);
        try{
            List<Node.DirectedEdge> shortestPath = MarvelPaths.findPath(g, start, dest);
            output.println("path from " + start + " to " + dest + ":");
            if(shortestPath == null){
                output.println("no path found");
            } else {
                String prevHero = start;
                for(Node.DirectedEdge e: shortestPath) {
                    output.println(prevHero + " to " + e.getEnd().getLabel() + " via " + e.getLabel());
                    prevHero = e.getEnd().getLabel();
                }
            }
        } catch (Exception e){
            if(!g.containsNode(new Node(start))){
                output.println("unknown character " + start);
            }
            if(!g.containsNode(new Node(dest))){
                output.println("unknown character " + dest);
            }
        }
    }
    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph g = graphs.get(graphName);
        g.addNode(new Node(nodeName));
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        Graph g = graphs.get(graphName);
        Node parent = g.getNode(parentName);
        Node child = g.getNode(childName);
        g.addEdge(parent, child, edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName +
                " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph g = graphs.get(graphName);
        List<Node> sortedNodes = new ArrayList<>();
        for(Node n: g.getNodes()){
            sortedNodes.add(n);
        }
        Collections.sort(sortedNodes, new SortbyLabel());
        String out = graphName + " contains:";
        StringBuilder str = new StringBuilder(out);
        for(Node n: sortedNodes){
            str.append(" " + n.getLabel());
        }
        output.println(str);
    }

    class SortbyLabel implements Comparator<Node>{
        public int compare(Node n1, Node n2){
            return n1.getLabel().compareTo(n2.getLabel());
        }
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph g = graphs.get(graphName);
        Node n = g.getNode(parentName);

        List<Node.DirectedEdge> lst = new ArrayList<>();
        for(Node.DirectedEdge e: n.getEdges()){
            lst.add(e);
        }
        lst.sort(new Comparator<Node.DirectedEdge>() {
            @Override
            public int compare(Node.DirectedEdge o1, Node.DirectedEdge o2) {
                if(o1.getEnd().equals(o2.getEnd())){
                    return o1.getLabel().compareTo(o2.getLabel());
                }
                return o1.getEnd().getLabel().compareTo(o2.getEnd().getLabel());
            }
        });
        String out = "the children of " + parentName + " in  " + graphName + " are:";
        StringBuilder str = new StringBuilder(out);
        for(Node.DirectedEdge e: lst){
            str.append(" " + e.getEnd().getLabel() + "(" + e.getLabel() + ")");
        }
        output.println(str);
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
