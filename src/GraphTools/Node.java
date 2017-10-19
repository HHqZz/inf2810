package GraphTools;

import java.util.ArrayList;
import java.util.List;

public class Node {
    protected String id;
    protected List<Edge> edgesList;
    protected boolean recharge;


    public Node(String id) {
        this.id = id;
        this.edgesList = new ArrayList<Edge>();

    }
    public Node (){
        this.edgesList = new ArrayList<Edge>();
    } //node vide


    public void addEdge (Edge edge){
        this.edgesList.remove(edge); //on évite de dupliquer
        this.edgesList.add(edge);
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------------- Merge ------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public void merge(Node nodeToMerge){
        this.mergeEdgeList(nodeToMerge.getEdgesList());
    }

    public void mergeEdgeList(List<Edge> edgesListToMerge){
        for (Edge edge: edgesListToMerge
             ) {
            edge.setFrom(this);//on réoriente les from qui sont appelés par référence
        }
        this.edgesList.removeAll(edgesListToMerge);
        this.edgesList.addAll(edgesListToMerge);
        this.edgesList = Edge.removeDuplicatesInList(this.edgesList);
    }




    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------- Getters & Setters ------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public List<Edge> getEdgesList() {
        return edgesList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEdgesList(List<Edge> edgesList) {
        this.edgesList = edgesList;
    }


    public boolean isRecharge() {
        return recharge;
    }

    public void setRecharge(boolean recharge) {
        this.recharge = recharge;
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Clone ------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public Node cloneButNotEdgeList(){
        Node newnode = new Node(id);
        return newnode;
    }


}
