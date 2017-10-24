package GraphTools;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {
    List<Node> nodes;
    List<String>nodesIndex;
    List<Edge>edges;

    public Graph() {
        this.nodes = new ArrayList<Node>();
        this.nodesIndex = new ArrayList<String>();
        this.edges = new ArrayList<Edge>();
    }


    // ----------------------------------------------------------------------------------------------------------
    // ----------------------------------------- Creation Functions ---------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public void fillGraph(String filepath , boolean isDrone, boolean isDigraph) throws IOException { // j'ai refait une formule dsns mon coin car ils demandent une formule récursive mais l'idée est là
        if (isDrone){isDigraph=false;}
        Graph graph = this;
            List<String> rawLines = Files.readAllLines(Paths.get(filepath),StandardCharsets.UTF_8);
            boolean node_presentation = true;
            for (int index = 0; index< rawLines.size(); index++) {
                List<String> line =( Arrays.asList(rawLines.get(index).split(",", -1)));
                if (node_presentation && line.size() == 1){
                    node_presentation = false;
                    index++; // on passe directement à l'index suivant car cette ligne n'est pas utilisable
                    line =( Arrays.asList(rawLines.get(index).split(",", -1)));
                }
                if(node_presentation){ // quand on en est encore à la présentaton des noeuds, on les créé
                    Node node = new Node();
                    if (isDrone){
                        node.setId(line.get(0));
                        node.setRecharge(line.get(1).equals("1"));
                    }else{
                        node.setId(line.get(1));
                    }
                    graph.addNode(node);
                }else { // après la présentation des noeuds, on les relie entre eux
                    Node node0 = graph.getNodeFromIndex(Integer.parseInt(line.get(0))-1);
                    Node node1 = graph.getNodeFromIndex(Integer.parseInt(line.get(1))-1);

                    double weight = 1;
                            if(isDrone){
                                weight = Double.parseDouble(line.get(2));
                            }
                    Edge edge0 = new Edge(node0,node1,weight);
                    node0.addEdge(edge0);
                    graph.addEdgeInListOnly(edge0);
                    if (! isDigraph){
                        Edge edge1 = new Edge(node1,node0,weight);
                        node1.addEdge(edge1);
                        graph.addEdgeInListOnly(edge1);
                    }
                }
            }
    }



    public void addNode (Node node){
        // on vire l'existant si c'était le cas

        this.nodes.remove(node);
        this.nodesIndex.remove(node.getId());
        this.edges.removeAll(node.getEdgesList());

        //on met le neuf
        this.nodes.add(node);
        this.nodesIndex.add(node.getId());
        this.edges.addAll(node.getEdgesList());
    }
    public void addEdgeInListOnly (Edge edge){
        this.edges.remove(edge);
        this.edges.add(edge);
    }


    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------- Getters & Setters ------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------


    // --------------- les défaults ---------------

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<String> getNodesIndex() {
        return nodesIndex;
    }

    public void setNodesIndex(List<String> nodesIndex) {
        this.nodesIndex = nodesIndex;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }


    // --------------- les ajoutés ---------------
    // nodes
    public int getNodeIndex (String name){
        return this.nodesIndex.indexOf(name);
    }
    public Node getNodeFromId (String name){
        int index = getNodeIndex(name);
        if (index == -1){
            return null;
        }
        else {
            return this.nodes.get(index);
        }
    }
    public int getIndexOfNode (Node node){
        return nodes.indexOf(node);
    }
    public Node getNodeFromIndex (int index) {
        return nodes.get(index);
    }
    //graph properties
    public int getOrder (){
        if (nodesIndex ==null){return 0;}
        return nodesIndex.size();
    }
    public int getSize (){
        return edges.size();
    }

    //neighbourhood
    public ArrayList<Node> getNeighboursOfNodeIndex(int nodeIndex){
        Node node = nodes.get(nodeIndex);
        List<Edge> nodeEdges = node.getEdgesList();
        ArrayList<Node> neighbours = new ArrayList<Node>();
        for (Edge edge: nodeEdges){
            neighbours.add(edge.to);
        }
        return neighbours;
    }
    public ArrayList<Integer> getNeighboursIndexOfNodeIndex(int nodeIndex){
        ArrayList<Node> neighbours = getNeighboursOfNodeIndex(nodeIndex);
        ArrayList<Integer> neighboursIndex = new ArrayList<Integer>();
        for (Node neighbour:neighbours) {
            neighboursIndex.add(getIndexOfNode(neighbour));
        }
        return neighboursIndex;
    }


    // ----------------------------------------------------------------------------------------------------------
    // ------------------------------------------ Interpretes ---------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public ArrayList<String> NameAPathOfIndexes (ArrayList<Integer> pathOfIndexes){
        ArrayList<String> path = new ArrayList<String>();
        List<String> nodeNames = getNodesIndex();
        for (Integer nodeIndex:pathOfIndexes) {
            path.add(nodeNames.get(nodeIndex));
        }
        return path;
    }
    public ArrayList<Node> NodePathOfIndexes (ArrayList<Integer> pathOfIndexes){
        ArrayList<Node> path = new ArrayList<Node>();
        List<Node> nodes = getNodes();
        for (Integer nodeIndex:pathOfIndexes) {
            path.add(nodes.get(nodeIndex));
        }
        return path;
    }


    // ----------------------------------------------------------------------------------------------------------
    // ----------------------------------------- Print & Draw ---------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public void printSimple () {
        System.out.println(nodesIndex);
    }
    public void printWithInfos () {System.out.println("taille "+nodesIndex.size()+" nodes : " + nodesIndex);}
    public void lireGraph (){
        for (int i=0; i<this.nodes.size(); i++) {
            Node node = this.nodes.get(i);
            System.out.print("( objet"+node.id+" ");
            if (node.getEdgesList() != null && node.getEdgesList().size()>0){
                System.out.print(",(");
                for ( int j=0; j< node.getEdgesList().size();j++) {
                    Edge edge = node.getEdgesList().get(j);
                    System.out.print(" ("+edge.to.getId()+", "+edge.getWeight()+" ) ");
                }
                System.out.println(") )");
            }


        }
    }
    public void printGraphOriente(){

        for (int i=0; i<this.nodes.size(); i++) {
            Node node = this.nodes.get(i);
            System.out.print("( objet "+node.id+" ");
            if (node.getEdgesList() != null && node.getEdgesList().size()>0){
                System.out.print(",(");
                for ( int j=0; j< node.getEdgesList().size();j++) {
                    Edge edge = node.getEdgesList().get(j);
                    System.out.print(" ("+edge.to.getId()+" ) ");
                }
                System.out.println(") )");
            }


        }
    }

    // ----------------------------------------------------------------------------------------------------------
    // ------------------------------------------- Les removers -------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public void removeEdge (Edge edgeToRemove){
        edges.remove(edgeToRemove);
        edgeToRemove.from.getEdgesList().remove(edgeToRemove);
//        nodes.get(nodesIndex.indexOf(edgeToRemove.getFrom().getId())).getEdgesList().remove(edgeToRemove);
    }



}
