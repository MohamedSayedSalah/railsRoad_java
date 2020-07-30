package railsRoadApplication.Graph;

import railsRoadApplication.Util.RouteParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {

    long [][] adjMatrix ;
    List <Vertex > adjList ;


    public Graph() {
        this.adjMatrix = new long[26][26];
        Arrays.stream(adjMatrix).forEach(x->Arrays.fill(x,1<<30));
        this.adjList = Arrays.asList(new Vertex[26]);
    }
    public void setAdjMatrix(String direction){
        RouteParser routeParser = new RouteParser(direction) ;
        adjMatrix[routeParser.getFrom() - 'A'][routeParser.getTo() - 'A'] = routeParser.getDistance();
    }

    public  void setAdjList(String direction){
        RouteParser routeParser = new RouteParser(direction) ;
        // from
        if (adjList.get(routeParser.getFrom() - 'A') ==  null ){
            adjList.set(routeParser.getFrom() - 'A' , new Vertex(routeParser.getFrom()) );
        }

        // to
        if (adjList.get(routeParser.getTo() - 'A') ==  null ){
            adjList.set(routeParser.getTo() - 'A', new Vertex(routeParser.getTo()) ) ;
        }

        Edge edge = new Edge( adjList.get(routeParser.getFrom() - 'A'), adjList.get(routeParser.getTo() - 'A') , routeParser.getDistance() );
        adjList.get(routeParser.getFrom() - 'A').getEdges().add(edge) ;
    }

    public List<Vertex> getAdjList() {
        return adjList;
    }

    public long[][] getAdjMatrix() {
        return adjMatrix;
    }
}
