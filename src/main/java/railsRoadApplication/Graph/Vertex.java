package railsRoadApplication.Graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    char id  ;
    List<Edge> edges ;

    Vertex(){} ;
    public Vertex(char id){
        this.id = id ;
        this.edges = new ArrayList<>() ;
    };

    public char getId() {
        return id;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setId(char id) {
        this.id = id;
    }
}


