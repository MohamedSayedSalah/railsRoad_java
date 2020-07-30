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

    public Solver(){} ;
    public Solver(String fileName) {
        FileParser fileParser = new FileParser();
        graph = new Graph();
        mem = new long [26][10000] ;
        Arrays.stream(mem).forEach(x->Arrays.fill(x,-1));
        fileParser.fileReader(fileName, graph);
    }

    /**
     *
     * @param trip  trip  from rules array like ABC
     * @return number of steps until you reach your destination
     *
     * this solution is using adj_matrix to calculate directly the steps from to -> destination in O(1) constant time factor
     *  ABC will be  =  adj_matrix[0][1] + adj_matrix[1][2]
     *
     */

    public long totalTripDistance(String trip) {
        int n = trip.length();
        long steps = 0;
        for (int i = 0; i + 1 < n; i++) {
            long cellValue = graph.getAdjMatrix()[trip.charAt(i) - 'A'][trip.charAt(i + 1) - 'A'];
            steps += cellValue == 1 << 30 ? -1 * (1 << 30) : cellValue;
        }
        return steps > 0 ? steps : 0;
    }

    /**
     * just for parsing the rules array
     */
    public void preDefinedRoutesTripDistance(){
        IntStream.range(0, rules.length).forEach(
                nbr -> {
                    if (totalTripDistance(rules[nbr]) > 0) {
                        System.out.println("Output #" + (nbr + 1) + ": " + totalTripDistance(rules[nbr]));
                    } else {
                        System.out.println("Output #" + (nbr + 1) + ":" + " NO SUCH ROUTE");
                    }
                }
        );
    }


    /**
     *
     * @param start initial source
     * @param end endup destination
     * @param stops how many stops do you still have so far
     * @param exact is it has to be exact number of steps or it can be maximum number of steps
     * @param current your current vertex
     * @return number of different route that you could take from source to destination with provided steps
     *
     * for each vertex you visit all of its edges
     * and for each new visited vertex you subtract your remaining steps by 1
     * until you reach your destination this will be considered a valid route
     *
     */

    public int numberOfTrips(char start, char end, int stops, boolean exact, char current) {

        if (stops < 0) return 0;
        if (current == end && ((stops == 0 && exact) || (stops >= 0 && !exact))) return 1;
        if (current == '*') current = start;// default value

        List<Edge> edges = graph.getAdjList().get(current - 'A').getEdges();
        return edges.stream().mapToInt(
                edge -> numberOfTrips(start, end, stops - 1, exact, edge.getTo().getId())
        ).sum();
    }



    /**
     *
     * @param s  your source vertex
     * @param d  your destination vertex
     * @return  the shortest path from source to destination
     *
     * every iteration :
     *          - pick the shortest edge from your source that has not been visited yet
     *          - relax
     *          - for every vertex v test if distance from source s to every other_vertex i in distance array is the shortest or not    s->v->i < s->i (horrible explanation I know)
     *          - if not update the array and mark v as visited
     *
     *
     *          the solution complexity is O (V*V)
     */

   public long dijkstra(char s, char d) { // V * V

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


    /**
     *
     * @param s  your source vertex
     * @param d  your destination vertex
     * @return  the shortest path from source to destination
     *
     * every iteration :
     *          - push a new edge to the queue with 0 weight
     *          - priority queue will sort by least weight
     *          - relax
     *          - for every vertex v test if distance from source s to every other_vertex i in distance array is the shortest or not    s->v->i < s->i
     *          - if not update the array and mark v as visited
     *
     *
     *          the solution complexity is O (V * LOG(E))
     */

    public long dijkstraWithPriorityQueue(char s, char d) {


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


    /**
     *
     * @param current  you current vertex
     * @param e you destination
     * @param distance the upTo distance
     * @param soFar what is your steps soFar
     * @return number of different routes
     *
     *  for each vertex you visit all of its edges with added weight to the total steps
     *  and route considered to be a different route if and only if you visited the vertex with different steps so far
     *  and mem array for memoization and for saving the visited state however using HashMap for keeping the state was a better solution
     */


    public long differentRoutes(char current, char e, long distance, long soFar ) {
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

        preDefinedRoutesTripDistance();
        System.out.println("Output #6:" + numberOfTrips('C', 'C', 3, false, '*'));
        System.out.println("Output #7:" + numberOfTrips('A', 'C', 4, true, '*'));
        System.out.println("Output #8:" + dijkstraWithPriorityQueue('A', 'C'));
        System.out.println("Output #9:" + dijkstra('B', 'B'));
        System.out.println("Output #10:" + differentRoutes('C' ,'C'  , 30,0));

    }

}
