import GraphTools.Graph;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
        Graph graphDrone = new Graph();
        graphDrone.fillGraph("arrondissements.txt",true );
        graphDrone.printSimple();
    }
}
