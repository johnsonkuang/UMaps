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

package marvel;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import graph.Graph;
import graph.Node;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.*;

/**
 * Parser utility to load the Marvel Comics dataset.
 */
public class MarvelParser {

    /**
     * Reads the Marvel Universe dataset. Each line of the input file contains a character name and a
     * comic book the character appeared in, separated by a tab character
     *
     * @param filename the file that will be read
     * @return iterator of data set read from the data file
     * @spec.requires filename is a valid file in the resources/data folder.
     */
    public static Iterator<MarvelModel> parseData(String filename) {
        // You can use this code as an example for getting a file from the resources folder
        // in a project like this. If you access TSV files elsewhere in your code, you'll need
        // to use similar code. If you use this code elsewhere, don't forget:
        //   - Replace 'MarvelParser' in `MarvelParser.class' with the name of the class you write this in
        //   - If the class is in src/main, it'll get resources from src/main/resources
        //   - If the class is in src/test, it'll get resources from src/test/resources
        //   - The "/" at the beginning of the path is important
        InputStream stream = MarvelParser.class.getResourceAsStream("/data/" + filename);
        if(stream == null) {
            // stream is null if the file doesn't exist.
            // You'll probably want to handle this case so you don't try to call
            // getPath and have a null pointer exception.
            // Technically, you'd be allowed to just have the NPE because of
            // the @spec.requires, but it's good to program defensively. :)
            throw new IllegalArgumentException("provided an invalid file name");
        }
        Reader reader = new BufferedReader(new InputStreamReader(stream));

        Iterator<MarvelModel> tsvMarvelIterator =
                new CsvToBeanBuilder<MarvelModel>(reader) //set input
                    .withType(MarvelModel.class) // set entry type
                    .withSeparator('\t') // \t for TSV
                    .build() // returns a CsvToBean<MarvelModel>
                    .iterator();

        return tsvMarvelIterator;
    }

    /**
     * Builds a graph based on data read from a .tsv file
     * @param tsvMarvelIterator an iterator of MarvelModels read from the .tsv data file
     * @return Graph initialized to data in the .tsv file
     * @spec.requires tsvMarvelIterator != null
     */
    public static Graph buildGraph(Iterator<MarvelModel> tsvMarvelIterator){
        //maps books to Characters that appears in it
        //all characters that appear in a specific book require edges made
        //between them

        //assumes that there are no duplicate character-book
        Graph mCU = new Graph();
        Map<String, Set<Node>> marvelCharacters = new HashMap<>();
        while(tsvMarvelIterator.hasNext()){
            MarvelModel character = tsvMarvelIterator.next();
            String bookName = character.getBook();
            if(!marvelCharacters.containsKey(bookName)) {
                marvelCharacters.put(bookName, new HashSet<>());
            }

            String heroName = character.getHero();
            Node hero = new Node(heroName);
            if(mCU.containsNode(hero)){
                hero = mCU.getNode(heroName);
            } else {
                mCU.addNode(hero);
            }
            marvelCharacters.get(bookName).add(hero);
            for(Node n: marvelCharacters.get(bookName)){
                if(!hero.equals(n)){
                    mCU.addEdge(hero, n, bookName);
                    mCU.addEdge(n, hero, bookName);
                }
            }

        }
        return mCU;
    }
}