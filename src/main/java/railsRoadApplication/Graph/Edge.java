package railsRoadApplication.Graph;

public class Edge  implements Comparable<Edge>{
    Vertex from , to ;
    long weight ;

    Edge() {};
    public Edge(Vertex from, Vertex to, long weight) {
        this.from = from ;
        this.to = to ;
        this.weight = weight;
    };

    public Long getWeight() {
        return weight;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        if (this.weight > o.getWeight())
            return - 1 ;
        if (this.weight == o.getWeight())
            return 0 ;
        return 1 ;
    }
}
