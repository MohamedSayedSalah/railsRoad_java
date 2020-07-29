package railsRoadApplication;

import railsRoadApplication.Graph.Edge;
import railsRoadApplication.Graph.Graph;
import railsRoadApplication.Graph.Vertex;
import railsRoadApplication.Util.FileParser;


import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Solver {
    Graph graph;
    String[] rules = {"ABC", "AD", "ADC", "AEBCD", "AED"};
    long [][] mem  ;
    public Solver() {
        FileParser fileParser = new FileParser();
        graph = new Graph();
        mem = new long [26][10000] ;
        Arrays.stream(mem).forEach(x->Arrays.fill(x,-1));
        fileParser.fileReader("input", graph);
    }

    public long totalTripDistance(String trip) {
        int n = trip.length();
        long steps = 0;
        for (int i = 0; i + 1 < n; i++) {
            long cellValue = graph.getAdjMatrix()[trip.charAt(i) - 'A'][trip.charAt(i + 1) - 'A'];
            steps += cellValue == 1 << 30 ? -1 * (1 << 30) : cellValue;
        }
        return steps > 0 ? steps : 0;
    }

    public int numberOfTrips(char start, char end, int stops, boolean exact, char current) {
        if (stops < 0) return 0;
        if (current == end && ((stops == 0 && exact) || (stops >= 0 && !exact))) return 1;
        if (current == '*') current = start;// default value

        List<Edge> edges = graph.getAdjList().get(current - 'A').getEdges();
        return edges.stream().mapToInt(
                edge -> numberOfTrips(start, end, stops - 1, exact, edge.getTo().getId())
        ).sum();
    }


    long dijkstra(char s, char d) { // V * V

        boolean[] visited = new boolean[26];
        long [] dis = new long[26];

        Arrays.fill(visited, false);
        Arrays.fill(dis, 1 << 30);

        dis[s - 'A'] = 0;

        while (true) {

            long [] valIndex = {1<<30 , -1} ;

            IntStream.range(0, 26).forEach((i) -> {
                if (dis[i] < valIndex[0] && !visited[i]) {
                    valIndex[1] = i;
                    valIndex[0] = dis[i];
                }
            });

            if (valIndex[1] == -1) break;
            visited[(int)valIndex[1]] = true;

            IntStream.range(0, 26).forEach((i) -> {
                if (valIndex[1] != i && (graph.getAdjMatrix()[(int)valIndex[1]][i] + dis[(int)valIndex[1]] < dis[i] || dis[i] == 0)) { // Relax
                    dis[i] = graph.getAdjMatrix()[(int)valIndex[1]][i] + dis[(int)valIndex[1]] ;
                }
            });
        }

        return dis[d - 'A'];
    }

    long dijkstraWithPriorityQueue(char s, char d) {


        long [] dis = new long [26];
        Arrays.fill(dis , 1 << 30);

        PriorityQueue<Edge> q = new PriorityQueue<>();
        Vertex starter = new Vertex('0');
        Vertex to = graph.getAdjList().get(s - 'A');
        q.add(new Edge(starter, to, 0));
        dis[s-'A'] = 0 ;

        while (!q.isEmpty()) {
            Edge e = q.peek();
            q.poll();
            if (e.getWeight() > dis[e.getTo().getId() - 'A']) continue;// some other states reached better already

             e.getTo().getEdges().forEach(edge ->{
                 Edge tempEdge  = new Edge(edge.getFrom() , edge.getTo() , edge.getWeight()) ;
                 if (dis[edge.getTo().getId() - 'A'] > dis[edge.getFrom().getId() - 'A'] + edge.getWeight()) {
                     dis[edge.getTo().getId() - 'A'] = dis[edge.getFrom().getId() - 'A'] + edge.getWeight();
                     tempEdge.setWeight(dis[edge.getFrom().getId() - 'A'] + e.getWeight());
                     q.add(tempEdge);
                 }
             });
        }


        return dis[d-'A'];
    }




    long differentRoutes(char current, char e, long distance, long soFar ) {
        if (soFar >= distance) {
            return 0;
        }


        if (mem[current - 'A'][(int)soFar] != -1){
            return mem[current - 'A'][(int) soFar];
        }
        Vertex vertex = graph.getAdjList().get(current - 'A');
        List<Edge> edges = vertex.getEdges();


        long  [] count = {0}  ;
        count[0] = (current == e && soFar < distance && soFar != 0 ? 1 : 0);

        edges.forEach(edge->{
            count[0] += differentRoutes(edge.getTo().getId(), e, distance,edge.getWeight() + soFar);
        });

        return mem[current - 'A'][(int) soFar] = count[0];
    }



    public void solve() {
        IntStream.range(0, rules.length).forEach(
                nbr -> {
                    if (totalTripDistance(rules[nbr]) > 0) {
                        System.out.println("Output #" + (nbr + 1) + ": " + totalTripDistance(rules[nbr]));
                    } else {
                        System.out.println("Output #" + (nbr + 1) + ":" + " NO SUCH ROUTE");
                    }
                }
        );

        System.out.println("Output #6:" + numberOfTrips('C', 'C', 3, false, '*'));
        System.out.println("Output #7:" + numberOfTrips('A', 'C', 4, true, '*'));
        System.out.println("Output #8:" + dijkstraWithPriorityQueue('A', 'C'));
        System.out.println("Output #9:" + dijkstra('B', 'B'));
        System.out.println("Output #10:" + differentRoutes('C' ,'C'  , 30,0));

    }

}
