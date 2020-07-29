package RailsRoadTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import railsRoadApplication.Solver;


public class RailsRoadTests {

    @Test
    public void tenDifferentCitiesRouteDistance(){
        Solver solver = new Solver("test1") ;
        String[] rules = {"ABC", "AD", "ADC", "AEBCD", "AED"};
        long [] expected = {135,0,0,161,28} ;
        Assertions.assertEquals(expected[0],solver.totalTripDistance(rules[0]));
        Assertions.assertEquals(expected[1],solver.totalTripDistance(rules[1]));
        Assertions.assertEquals(expected[2],solver.totalTripDistance(rules[2]));
        Assertions.assertEquals(expected[3],solver.totalTripDistance(rules[3]));
        Assertions.assertEquals(expected[4],solver.totalTripDistance(rules[4]));
    }


    @Test
    public void intOverFlowRoute(){
        Solver solver = new Solver("test2") ;
        Assertions.assertEquals(10000000000L,solver.totalTripDistance("ABCDEFGHIJK"));
    }


   @Test
    public void routeWithMaximumNumberOfStops(){
       Solver solver = new Solver("test1") ;
       Assertions.assertEquals(1,solver.numberOfTrips('A','D',2,false, '*'));
       Assertions.assertEquals(5,solver.numberOfTrips('A','D',3,false, '*'));
       Assertions.assertEquals(9,solver.numberOfTrips('A','D',4,false, '*'));
       Assertions.assertEquals(16,solver.numberOfTrips('A','D',5,false, '*'));
       Assertions.assertEquals(9,solver.numberOfTrips('A','E',6,false, '*'));
       Assertions.assertEquals(8,solver.numberOfTrips('A','E',3,false, '*'));
   }


    @Test
    public void routeWithExactNumberOfStops(){
        Solver solver = new Solver("test1") ;
        Assertions.assertEquals(1,solver.numberOfTrips('A','D',2,true, '*'));
        Assertions.assertEquals(4,solver.numberOfTrips('A','D',3,true, '*'));
        Assertions.assertEquals(5,solver.numberOfTrips('A','D',4,true, '*'));
        Assertions.assertEquals(12,solver.numberOfTrips('A','D',5,true, '*'));
        Assertions.assertEquals(2,solver.numberOfTrips('A','E',3,true, '*'));
    }

    @Test
    public void shortestPastWithDijkstra(){
        Solver solver = new Solver("test1") ;
        Assertions.assertEquals(28,solver.dijkstra('A','D'));
        Assertions.assertEquals(14,solver.dijkstra('A','E'));
        Assertions.assertEquals(24,solver.dijkstra('A','C'));
        Assertions.assertEquals(414,solver.dijkstra('A','I'));
        Assertions.assertEquals(400,solver.dijkstra('E','I'));

    }

    @Test
    public void shortestPastWithPriorityQueueDijkstra(){
        Solver solver = new Solver("test1") ;
        Assertions.assertEquals(28,solver.dijkstraWithPriorityQueue('A','D'));
        Assertions.assertEquals(14,solver.dijkstraWithPriorityQueue('A','E'));
        Assertions.assertEquals(24,solver.dijkstraWithPriorityQueue('A','C'));
        Assertions.assertEquals(414,solver.dijkstraWithPriorityQueue('A','I'));
        Assertions.assertEquals(400,solver.dijkstraWithPriorityQueue('E','I'));

    }

   @Test

    public void routeCombinations(){
        Solver solver = new Solver( "test1") ;
       Assertions.assertEquals(13,solver.differentRoutes('A','D' ,200 , 0));
       Assertions.assertEquals(13,solver.differentRoutes('A','C' ,300 , 0));
       Assertions.assertEquals(9,solver.differentRoutes('B','D' ,200 , 0));
       Assertions.assertEquals(14,solver.differentRoutes('E','D' ,200 , 0));
   }


}



