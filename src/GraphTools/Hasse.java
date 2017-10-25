package GraphTools ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Hasse{

	public static Graph creerGrapheOriente() throws IOException {
        Graph graphRecette = new Graph();
        graphRecette.fillGraph("manger.txt",false,true );	// On cree le graph oriente
        return graphRecette;
    }
	public Hasse(){
	}
	

//	private static void retirerTrans(Graph graph) {    // Pour chaque Noeud, ses 'to' doivent être differents des "to" de ses "to"  CA SE COMPREND SUR PAPIER    to = destination d'un arc
//
//		for (int i= 0 ; i< graph.getNodes().size(); i++) { 									// pour chaque noeud
//			List<Edge> EdgeEnfant = graph.getNodes().get(i).getEdgesList();					// On cree une liste des arcs qui partent de ce noeud
//			for (int j = 0; j<EdgeEnfant.size();j++){										// Pour chaque arc partant de ce noeud
//				List<Edge> EdgePetitsEnfants = EdgeEnfant.get(j).getTo().getEdgesList();	// On recupere les arcs partant de chaque nouveau noeud
//				for (int k=0; k<EdgePetitsEnfants.size();k++){								// Pour chacun de ces arcs
//					if(EdgePetitsEnfants.get(k).getTo()==EdgeEnfant.get(i).getTo()){
//						graph.removeEdge(EdgeEnfant.get(j));								// Si oui, on remove larc du graph
//						retirerTrans(graph);
//						return;
//
//					}		// On verifie si sa destination est  la meme que la destination de la premiere liste
//				}
//			}
//		}
//	}
private static void retirerTrans(Graph graph) {    // Pour chaque Noeud, ses 'to' doivent être differents des "to" de ses "to"  CA SE COMPREND SUR PAPIER    to = destination d'un arc
	for (Edge testEdge: graph.getEdges()){
		for (Edge EdgeEnfant :testEdge.getFrom().getEdgesList()) {
			for (Edge EdgePetitsEnfants:EdgeEnfant.getTo().getEdgesList())
				if(testEdge.getTo()==EdgePetitsEnfants.getTo()) {
					graph.removeEdge(testEdge);
					retirerTrans(graph);
					return;
				}
		}
	}
}

	public static void retirerReflexivite(Graph graph){
		for (int i = 0; i<graph.getEdges().size();i++){
			Edge edge = graph.getEdges().get(i);
			if (edge.getTo()==edge.getFrom()){
				graph.removeEdge(edge);
				retirerReflexivite(graph);
				return;
			}
		}
	}
	public static void  generateHasse(Graph graph){
		retirerReflexivite(graph);
		retirerTrans(graph);
	}




	public static void printDiagHasse(Graph graph){
		generateHasse(graph);
		List<Node> tempList = graph.getNodes();
		List<Node> nodeToRemove = new ArrayList<Node>;
		List<Node> ordre = new ArrayList<Node>();
		for(int i = 0 ; i<tempList.size() ; i++){ // Pour chaque noeud
			for(int j = 0; j<tempList.get(i).getEdgesList().size() ; j++) // Pour chaque arc de chaque noeud
			{

				if(tempList.get(i).getEdgesList().get(j).getTo() == tempList.get(i) ) // s il se prend   un truc dans le cul
					nodeToRemove.add(tempList.get(i));// on stocke les noeud qui ont un truc dans le cul



			}

		}

		for(int k=0 ; k< ordre.size(); k++)
		{
			System.out.println("Liste "+ k + " :" );

			for(int l=0; l<ordre.get(k).getEdgesList().size(); l++) {
				while (ordre.get(k).getEdgesList().get(k).getFrom() != null) // tant quil a un from, print le noeud
				{
					System.out.println(ordre.get(k).getId() + "->");
				}
			}

		}

	}
}