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
	public void retirerTransitivite(Graph graph){	// Pour chaque Noeud, ses 'to' doivent être differents des "to" de ses "to"  CA SE COMPREND SUR PAPIER    to = destination d'un arc
		for(Node nodes: graph.getNodes()){ // pour chaque noeud

		}

	
	
	//todo
	public void retirerReflexivite(Graph graph){		// Parcourir ARC : si from == to alors retirer arc
		for (Edge edge: graph.getEdges()		// on épluche les edges du graphe
			 ) {
			if (edge.getFrom() == edge.getTo()){ // si les noeude to et from sont les mêmes
				graph.removeEdge(edge); //			on l'enlève du graphe
			}
		}
	}
	
	//todo
	public void genHass(){
		retirerReflexivite();
		retirerTransitivite();
	}
	
	//va bene


	
}