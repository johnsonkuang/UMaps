package marvel;

import graph.Graph;
import graph.Node;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * MarvelPaths performs operations on given graphs
 */
public class MarvelPaths {
    /**
     * This isn't an ADT, it only has static methods
     */

    /**
     * Runner method for
     * @param args System arguments passed in on run
     */
    public static void main(String[] args) {
        try{
            Graph<String, String> MCU = MarvelPaths.createGraph("marvel.tsv");
            String[] verbs = {
                    "punched",
                    "kicked",
                    "made fun of",
                    "kissed",
                    "flexed on",
                    "ended the career of",
                    "laughed at",
                    "suplexed",
                    "manhandled",
                    "stole the favorite gummy bearr of",
                    "slid into the dms of"
            };
            Scanner keyboard = new Scanner(System.in);
            boolean exit = true;
            drawString("MARVEL", "$", 200, 30);
            System.out.println("Welcome to the interactive Marvel Universe!");

            do {
                System.out.println("Input a character\'s name: ");
                String start = keyboard.nextLine();
                System.out.println("Input another character\'s name: ");
                String dest = keyboard.nextLine();
                try{
                    List<Node<String, String>.DirectedEdge> shortestPath = MarvelPaths.findPath(MCU, start, dest);
                    System.out.println("How " + start + " and " + dest + " got in a fight:");
                    if(shortestPath == null){
                        System.out.println("they didn\'t");
                    } else {
                        String prevHero = start;
                        for(Node<String, String>.DirectedEdge e: shortestPath) {
                            System.out.println(prevHero + " " + getRandomVerb(verbs)+ " " + e.getEnd().getLabel() + " in " + e.getLabel());
                            prevHero = e.getEnd().getLabel();
                        }
                    }
                } catch (Exception e){
                    if(!MCU.containsNode(new Node<String, String>(start))){
                        System.out.println("unknown character " + start);
                    }
                    if(!MCU.containsNode(new Node<String, String>(dest))){
                        System.out.println("unknown character " + dest);
                    }
                }
                System.out.println("");
                System.out.println("");
            }while(exit);
        } catch(Exception e){
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private static String getRandomVerb(String[] verbs){
        Random rng = new Random();
        return verbs[rng.nextInt(verbs.length)];
    }

    private static void drawString(String msg, String artChar, int width, int height){

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 24));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString(msg, 10, 20);

        //save this image
        //ImageIO.write(image, "png", new File("/users/mkyong/ascii-art.png"));

        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {

                sb.append(image.getRGB(x, y) == -16777216 ? " " : artChar);

            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(sb);
        }
    }

    /**
     * Reads and initializes a graph based on the dataset stored at 'fileName'.tsv
     * @param fileName the file that will be read
     * @return a fully initialized graph to the data read from the file
     * @spec.requires fileName is a valid file in the resources/data folder.
     * @spec.throws FileNotFoundException when fileName is not a valid file
     */
    public static Graph<String, String> createGraph(String fileName) throws FileNotFoundException {
        try{
            return MarvelParser.buildGraph(MarvelParser.parseData(fileName));
        } catch(IllegalArgumentException e){
            throw new FileNotFoundException(fileName + " is not a valid file in the resources/data folder.");
        }
    }

    /**
     * Finds the shortest path between a start node with label 'startString' and destination node with label 'destString'
     * @param g Graph that will be searched
     * @param startString the label of the start node
     * @param destString the label of the destination node
     * @return the lexicographically least shortest path between start and destination nodes, returns an empty list if
     *         the start.equals(dest) nodes, returns null if no path between the two nodes could be found
     * @spec.requires g != null, startString != null, Node(startString) != null & g.contains(Node(startString)),
     *                           destString != null, Node(destString) != null & g.contains(Node(destString))
     * @spec.throws IllegalArgumentException if any of the above clauses are violated
     */
    public static List<Node<String, String>.DirectedEdge> findPath(Graph<String, String> g, String startString, String destString)
                                                throws IllegalArgumentException{
        if(g == null || startString == null || destString == null){
            throw new IllegalArgumentException();
        }

        Node<String, String> start = g.getNode(startString);
        Node<String, String> dest = g.getNode(destString);

        if(start.equals(dest)){
            return new ArrayList<>();
        }

        if(!g.containsNode(start) || !g.containsNode(dest)){
            throw new IllegalArgumentException();
        }

        // data structures initialization
        Queue<Node<String, String>> workList = new LinkedList<>();
        Map<Node<String, String>, List<Node<String, String>.DirectedEdge>> visitedPaths = new HashMap<>();

        // initial state
        workList.add(start);
        visitedPaths.put(start, new ArrayList<>());
        while(!workList.isEmpty()){
            Node<String, String> n = workList.remove();
            if(n.equals(dest)){
                return visitedPaths.get(n);
            }
            //sort nodes in lexicographically least ordering
            List<Node<String, String>.DirectedEdge> lst = sortChildren(n);
            //add nodes that have not been processed to the list
            for(Node<String, String>.DirectedEdge e: lst){
                if(!visitedPaths.containsKey(e.getEnd())){
                    // Add all edges to new path
                    List<Node<String, String>.DirectedEdge> new_paths = new ArrayList<>();
                    new_paths.addAll(visitedPaths.get(n));
                    // add current edge to end node
                    new_paths.add(e);
                    visitedPaths.put(e.getEnd(), new_paths);
                    workList.add(e.getEnd());
                }
            }
        }
        return null;
    }

    private static List<Node<String, String>.DirectedEdge> sortChildren(Node<String, String> n){
        List<Node<String, String>.DirectedEdge> lst = new ArrayList<>(n.getEdges());
        lst.sort(new Comparator<Node<String, String>.DirectedEdge>() {
            @Override
            public int compare(Node<String, String>.DirectedEdge o1, Node<String, String>.DirectedEdge o2) {
                if(o1.getEnd().equals(o2.getEnd())){
                    return o1.getLabel().compareTo(o2.getLabel());
                }
                return o1.getEnd().getLabel().compareTo(o2.getEnd().getLabel());
            }
        });
        return lst;
    }
}
