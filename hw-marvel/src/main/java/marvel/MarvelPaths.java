package marvel;

import graph.Graph;
import graph.Node;

import java.util.Map;
import java.util.Set;

/**
 * MarvelPaths represents a mutable graph of all the characters
 * in the MCU connected through the issues they appear in together
 *
 * A MarvelPaths is represented by a series of <Char1, Char2> edges:
 *          where Char1, Char2 represent character nodes
 *            and <Char1, Char2> represents the edge in the graph
 */
public class MarvelPaths {
    private Graph marvelUniverse;

    /**
     * creates a new MarvelPaths with its Marvel Universe initialized
     */
    private MarvelPaths(){
        marvelUniverse = new Graph();
    }

    /**
     * initializes the graph
     */
    public void createGraph(){
        Map<String, Set<String>> marvelCharactersByBooks = MarvelParser.parseData("marvel.tsv");
        for(String bookName: marvelCharactersByBooks.keySet()){
            generateEdges(bookName, marvelCharactersByBooks.get(bookName));
        }
    }

    public Graph getMarvelUniverse(){
        return marvelUniverse;
    }

    private void generateEdges(String bookName, Set<String> heros){
        for(String hero1: heros){
            for(String hero2: heros){
                if(!hero1.equals(hero2)){
                    //Adding Nodes to the graph if they are not
                    Node hero1Node = new Node(hero1);
                    Node hero2Node = new Node(hero2);
                    if(!marvelUniverse.containsNode(hero1Node)){
                        marvelUniverse.addNode(hero1Node);
                    }
                    if(!marvelUniverse.containsNode(hero2Node)){
                        marvelUniverse.addNode(hero2Node);
                    }

                    // Adding in one direction, will be reverse connected on the second iteration
                    marvelUniverse.addEdge(hero1Node, hero2Node, bookName);
                }
            }
        }
    }
}
