package GraphTools;

import java.io.IOException;

public class FonctionDrones {
    public static Graph creerGraphe() throws IOException {
        Graph graphDrone = new Graph();
        graphDrone.fillGraph("arrondissements.txt",true,false );
        return graphDrone;
    }
    public static Graph creerGraphe(String filepath) throws IOException {
        if (filepath == null|| filepath.length()==0){filepath="arrondissements.txt";}
        Graph graphDrone = new Graph();
        graphDrone.fillGraph(filepath,true,false );
        return graphDrone;
    }
    public static void plusCourtChemin(Graph graphe, String idNode_1, String idNode_2, int weightCategory){
        double powerConsumptionRate = 0.0;
        switch (weightCategory){
            case 1: powerConsumptionRate = 0.01;
            break;
            case 2: powerConsumptionRate = 0.02;
            break;
            case 3: powerConsumptionRate = 0.04;
            break;
        }
        DijkstraPlus pathSearch_3_3 = new DijkstraPlus(graphe,graphe.getNodeIndex(idNode_1),powerConsumptionRate);
        if (pathSearch_3_3.hasPathTo(graphe.getNodeIndex(idNode_2)) ){
            System.out.println("un 3.3 ampères suffit en prenant le chemin suivant :");
            pathSearch_3_3.printSP(graphe.getNodeIndex(idNode_2));
        }else{
            switch (weightCategory){
                case 1: powerConsumptionRate = 0.01;
                break;
                case 2: powerConsumptionRate = 0.015;
                break;
                case 3: powerConsumptionRate = 0.025;
                break;
            }
            DijkstraPlus pathSearch_5_0 = new DijkstraPlus(graphe,graphe.getNodeIndex(idNode_1),powerConsumptionRate);
            if (pathSearch_5_0.hasPathTo(graphe.getNodeIndex(idNode_2))){
                System.out.println("un 5.0 ampères convient en prenant le chemin suivant :");
                pathSearch_5_0.printSP(graphe.getNodeIndex(idNode_2));
            }
            else {
                System.out.println("veuillez nous excuser, les contraintes technique ne permettent pas un tel trajet");
            }

        }

    }
}
