package GraphTools ;

import java.io.IOException;


public class Hasse{
	
	//constructeur

	//todo
	public static Graph creerGrapheOriente() throws IOException {
        Graph graphRecette = new Graph();
        graphRecette.fillGraph("manger.txt",false,true );	// On cree le graph oriente
        return graphRecette;
    }
	public Hasse(){
		
	}
	
	//todo
	public void retirerTransitivite(Graph graph) {    // Pour chaque Noeud, ses 'to' doivent être differents des "to" de ses "to"  CA SE COMPREND SUR PAPIER    to = destination d'un arc
		for (Node nodes : graph.getNodes()) { // pour chaque noeud

		}
	}

	
	
	//todo
	public static void retirerReflexivite(Graph graph){		// Parcourir ARC : si from == to alors retirer arc

	graph.edges.removeIf((edge.getFrom() == edge.getTo())

		for (Edge edge: graph.getEdges()		// on épluche les edges du graphe
			 ) {

			if (edge.getFrom() == edge.getTo()){ // si les noeude to et from sont les mêmes
				graph.removeEdge(edge); //			on l'enlève du graphe
				retirerReflexivite(graph);
			}
		}
	}
	
	//todo
	public static void  genHasse(Graph graph){
		retirerReflexivite(graph);
		//retirerTransitivite(graph);
	}




	public static void printDiagHasse(Graph graph){

		genHasse(graph);
		for(int i=1 ; i<=graph.getNodes().size() ; i++){
			System.out.println("Liste "+ i +" : " + graph.getNodes().get(i));
		}
	}


	
}