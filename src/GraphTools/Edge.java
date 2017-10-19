package GraphTools;


import java.util.List;

public class Edge {
protected Node from;
protected Node to;
protected double weight;


    public Edge(Node from, Node to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }


    public boolean equals(Edge edge){
        return ( this.from.getId().equals(edge.getFrom().getId()) && this.to.getId().equals(edge.getTo().getId()) && (this.weight == edge.getWeight()) );
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Getters & setters ------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeigth(double length) {
        this.weight = length;
    }

// ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Cloner ------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public Edge cloneButKeepNodes(){ // ca n'a pas d'intéret de garder les nodes mais c'est juste pour prévenir que la référence demaure
        Edge clone = new Edge(this.from,this.to,this.weight);
        return clone;
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Méthodes Statiques -----------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public static List<Edge> removeDuplicatesInList (List<Edge> edgesList){
        if (edgesList.size()<2){return edgesList;}
        for (int i = 0; i < edgesList.size(); i++) {
            Edge edge1 = edgesList.get(i);
            for (int j = edgesList.indexOf(edge1)+1; j < edgesList.size(); j++) {
                Edge edge2 = edgesList.get(j);
                boolean thereIsADuplicate = edge2!=null && edge1.equals(edge2) && edge1!=edge2 ;// si deux edges (existants) différents s'équivallent, on enlève celui de plus haut degré
                if ( thereIsADuplicate ){
                    edgesList.remove(j);
                    j--;// on reste au même j puisque le contennu de  j+1 viens de lui être associé
                }
            }
        }
        return edgesList;
    }
}
