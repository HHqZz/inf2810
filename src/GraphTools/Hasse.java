package GraphTools ;

		import java.io.IOException;
		import java.util.ArrayList;
		import java.util.List;


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

	private static void retirerTransitivite(Graph graph) {    // Pour chaque Noeud, ses 'to' doivent être differents des "to" de ses "to"  CA SE COMPREND SUR PAPIER    to = destination d'un arc
		for (Edge testEdge: graph.getEdges()){
			for (Edge EdgeEnfant :testEdge.getFrom().getEdgesList()) {
				for (Edge EdgePetitsEnfants:EdgeEnfant.getTo().getEdgesList())
					if(EdgeEnfant.getTo()==EdgePetitsEnfants.getTo()) {
						graph.removeEdge(testEdge);
						retirerTransitivite(graph);
						return;
					}
			}
		}
	}


	//todo
	public static void retirerReflexivite(Graph graph){
		for (int i = 0; i<graph.getEdges().size();i++){
			Edge edge = graph.getEdges().get(i);
			if (edge.getTo()==edge.getFrom()){
				graph.removeEdge(edge);
				retirerReflexivite(graph);
				return;
			}
		}
	}		// Parcourir ARC : si from == to alors retirer arc


	public static void  PreparerHasse(Graph graph){
		retirerReflexivite(graph);
		retirerTransitivite(graph);
	}


	public static void genererHasse() throws IOException {
		Graph graph = creerGrapheOriente();
		PreparerHasse(graph);
		ArrayList<Node> MinimumNodes = new ArrayList<>();
		ArrayList<Node> OrderNodes = new ArrayList<>();
		for(Node node: graph.getNodes()){ //on remplis tous les nodes
			MinimumNodes.add(node);
		}
		for(Edge edge: graph.getEdges()){//on enlève les non minimaux
			MinimumNodes.remove(edge.getTo());
		}
		int totalSize = graph.getNodes().size();
		while (OrderNodes.size() != totalSize){ //partie principale
			if (MinimumNodes.size()==0){break;}
			Node nodeToTake = MinimumNodes.get(0);
			OrderNodes.add(nodeToTake);
			graph.getNodes().remove(nodeToTake);
			graph.getNodesIndex().remove(nodeToTake.getId());
			for(int i = 0; i<graph.getEdges().size();i++){				//on enlève ce node du graphe
				Edge edge = graph.getEdges().get(i);
				if (edge.getFrom()==nodeToTake){
					graph.getEdges().remove(edge);
					i--;//pour ne pas perdre l'index
				}
			}
			for (int i = 0; i < nodeToTake.getEdgesList().size(); i++) {
				boolean isMinimum = true;
				for(Edge edge: graph.getEdges()){				//on ajoute les nouveaux noeuds minimaux qui sont les enfants du noeud séléctionné qui n'ont plus de parent
					if (edge.getTo()==nodeToTake.getEdgesList().get(i).getTo()){
						isMinimum = false;
					}
				}
				if (isMinimum){
					MinimumNodes.add(nodeToTake.getEdgesList().get(i).getTo());
				}
			}
			MinimumNodes.remove(0);
		}

		System.out.println("");									// on print en série
		boolean firstNode = true;
		for (Node node: OrderNodes ) {
			if (!firstNode){System.out.print(" -> ");}
			else {firstNode=false;}
			System.out.print(node.getId());
		}
		System.out.println("");
	}



}
