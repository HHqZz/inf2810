import GraphTools.FonctionDrones;
import GraphTools.Graph;
import GraphTools.Hasse;
import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
        Graph recetteGraph = Hasse.creerGrapheOriente();
       // recetteGraph.printGraphOriente();
        Hasse.genererHasse();
       // Hasse.printDiagHasse(recetteGraph);

//        Graph graphDrone = new Graph();
//        graphDrone.fillGraph("arrondissements.txt",true,false );
        Graph graphDrone = FonctionDrones.creerGraphe();
//        Graph graphDrone = new Graph();
        graphDrone.fillGraph("arrondissements.txt",true,false );
//        graphDrone.lireGraph();
//        FonctionDrones.plusCourtChemin(graphDrone,"1","17",3);
        FonctionDrones.plusCourtChemin(graphDrone,"1","17",2);
//        FonctionDrones.plusCourtChemin(graphDrone,"1","17",1);
//        FonctionDrones.plusCourtChemin(graphDrone,"1","7",3);
//        FonctionDrones.plusCourtChemin(graphDrone,"1","7",2);
//        FonctionDrones.plusCourtChemin(graphDrone,"1","7",1);
//        FonctionDrones.plusCourtChemin(graphDrone,"1","13",1);
//        FonctionDrones.plusCourtChemin(graphDrone,"8","15",1);
    }
}
