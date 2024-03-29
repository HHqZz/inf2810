package GraphTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstraPlus {
    protected ArrayList<Integer> previous;
    protected ArrayList<Boolean> marked;
    protected ArrayList<Double> time;
    protected ArrayList<Double> consumedEnergy;
    protected Graph baseGraph;
    protected ArrayList<Integer> brockenIndexes; // utile si ne graph n'est pas connecté
    protected ArrayList<Integer> connectedIndexes; // utile si ne graph n'est pas connecté
    protected double powerConsumptionRate;

    public DijkstraPlus(Graph graph, int startIndex, double powerConsumptionRate) {

        // --------------- on initialise ---------------
        this.powerConsumptionRate = powerConsumptionRate;
        int cardinality = graph.getOrder();
        marked =  new ArrayList<>(Arrays.asList(new Boolean[cardinality]));
        previous =  new ArrayList<>(Arrays.asList(new Integer[cardinality]));
        time =  new ArrayList<>(Arrays.asList(new Double[cardinality]));
        consumedEnergy =  new ArrayList<>(Arrays.asList(new Double[cardinality]));
        brockenIndexes = new ArrayList<Integer>();
        connectedIndexes = new ArrayList<Integer>();
        Collections.fill(marked, Boolean.FALSE);
        Collections.fill(time, Double.POSITIVE_INFINITY);
        this.baseGraph=graph;

        if (verifyNonNegative(baseGraph)){

        }else{
            System.out.println("il y a des valeurs négatives dans ce graph");
        }

        time.set(startIndex, 0.);
        consumedEnergy.set(startIndex, 0.);
        ArrayList<Double> unvisitedTime = (ArrayList<Double>)time.clone();
        int beingVisited = -1;

        // --------------- l'algorithme en soi ---------------

        while (marked.indexOf(false)!= -1){
            beingVisited = unvisitedTime.indexOf(Collections.min(unvisitedTime)); //on prend le noeud à plus petit temps/distance non visité ( on va commencer par visiter le start index, puis ses voisins...)
                if ((time.get(beingVisited)== Double.POSITIVE_INFINITY || marked.get(beingVisited))){ // ici on se prémunit d'un graphe non connexe

                    while (marked.indexOf(false)!= -1){// on a plusieurs sous graphes non connectés car le minimunm des non marqués est à l'infini
                    int brockenIndex = marked.indexOf(false);
                    brockenIndexes.add(brockenIndex);
                    marked.set(brockenIndex,true);
                }
//                brockenIndexes.add(beingVisited);
            }else{
                connectedIndexes.add(beingVisited);
                marked.set(beingVisited,true);
                unvisitedTime.set(beingVisited,Double.POSITIVE_INFINITY);// on met à l'infini ce noeud pour ne plus le re-sélectionner (d'ou le infinity dans le check de connexitée)
                List<Edge> edges = baseGraph.getNodeFromIndex(beingVisited).getEdgesList(); // on en prend les edges
                if (edges!=null){ // si le vertex a quelque lien
                    for (Edge edge:edges) {
                        int nodeTo = baseGraph.getIndexOfNode(edge.getTo());
//                      int nodeFrom = baseGraph.getIndexOfNode(edge.getFrom());
                        if (!marked.get(nodeTo)){ //on n'update que les vertex non marqués
                            Double previousTime = time.get(nodeTo);
                            int isRecharge = (edge.getTo().isRecharge()) ? 1 : 0;
                            int isNotRecharge = (edge.getTo().isRecharge()) ? 0 : 1;
                            Double newTime = time.get(beingVisited)+edge.getWeight()+isRecharge*20.0;
                            Double energyUsed = consumedEnergy.get(beingVisited) + edge.getWeight()*powerConsumptionRate;
                            if ( newTime<previousTime && energyUsed <= 0.8){            // Le changement par rapport au Dijkstra classique , on check à la fois le temps (+plus le temps de recharge échéant) et l'état des batteries !
                                time.set(nodeTo, newTime);// on update la distance au minimum entre la précédente et celle qui part de ce noeud
                                consumedEnergy.set(nodeTo,energyUsed*isNotRecharge); // on update l'énergie consommée
                                unvisitedTime.set(nodeTo,time.get(nodeTo));//on update les distances à visiter
                                previous.set(nodeTo,beingVisited);// on donne le bon précédent
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean verifyNonNegative(Graph graph){
        for (Edge edge : graph.getEdges()) {
         if (edge.getWeight()<0){return true;}
        }
        return true;
    }
    public boolean hasPathTo (int v){
        return (!time.get(v).equals(Double.POSITIVE_INFINITY)); // si c'est marqué, alors on est passé par v en partant de s
    }
    public double distTo( int v){
        return (time.get(v));
    }
    public ArrayList<Integer> pathOfIndexGetter(int v){
        ArrayList<Integer> path = new ArrayList<>();
        if (hasPathTo(v)){
            int vertex = v;
            while (time.get(vertex)>0){
                path.add(0,vertex);
                vertex = previous.get(vertex);
            }
            path.add(0,time.indexOf(0.0));
        }
        return path;
    }

    public ArrayList<String> pathOfIdGetter(int v) {
        ArrayList<String> path = new ArrayList<String>();
        ArrayList<Integer> pathIndex = pathOfIndexGetter(v);
        for (Integer i : pathIndex) {
            path.add(this.baseGraph.getNodesIndex().get(i));
        }
        return path;
    }
    // --------------- printers ---------------

    public void printSPWithNames (String nodeName){
        int index = baseGraph.getNodeIndex(nodeName);
        String pathString ="";
        ArrayList<Integer> path = getShortestPathOfIndexes(index);
        ArrayList<String> pathName = baseGraph.NameAPathOfIndexes(path);
        pathString += " "+pathName.get(0);
        if (path.size()>0){
            for (int i = 1; i <path.size() ; i++) {
                int distance = (int) Math.floor((distTo(i-1)-distTo(i))*1000);
                String nextNodeName = pathName.get(i);
                pathString = pathString + " <- "+ distance +"m -> "+ pathName.get(i);
            }
        }


        System.out.println("path : "+pathString+" | distance totale : "+this.distTo(index));
    }

    public void printSP (int v){
        System.out.println("path : ");
        printPaht(this.getShortestPathOfId(v));
        System.out.print(" | temps : "+this.distTo(v)+" minutes | changement de la baterie : "+ (1-this.consumedEnergy.get(v)));
    }
    public static void printPaht( ArrayList<String> path){
        for (int i= 0; i< path.size();i++){
            System.out.print(path.get(i));
            if (i<path.size()-1){
                System.out.print(" -> ");
            }
        }
    }

    public void print () {
        System.out.println("marked " + marked);
        System.out.println("previous " + previous);
        System.out.println("distance " + time);
    }

    // --------------- Getters ---------------
    public ArrayList<Integer> getShortestPathOfIndexes(int v){
        return pathOfIndexGetter(v);
    }
    public ArrayList<String> getShortestPathOfId(int v){
        return pathOfIdGetter(v);
    }

    public ArrayList<Integer> getPrevious() {
        return previous;
    }

    public ArrayList<Double> getDistance() {
        return time;
    }
    public ArrayList<Integer> getBrockenIndexes(){
        return brockenIndexes;
    }

    public ArrayList<Integer> getConnectedIndexes() {
        return connectedIndexes;
    }

}
