package marvel.implTest;

import marvel.MarvelParser;
import marvel.MarvelPaths;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class MarvelPathsTest {

    @Test(expected = FileNotFoundException.class)
    public void createGraph() throws FileNotFoundException{
        MarvelPaths.createGraph("fileDoesntExist.tsv");
    }
}