# railsRoad_java

## INSTRUCTIONS

project structure is based on maven framwWork convention

- open your terminal 
- download railsRoad_java project `git clone git@github.com:MohamedSayedSalah/railsRoad_java.git`
- navigate to the project folder and type `mvn install` 
- this will install dependecies and run the test suit but the report was not very informative unfortunately 
- navigate to `cd src/main/java`
- compile project `javac -cp . railsRoadApplication/RailsRoad.java`
- run project `java -cp . railsRoadApplication.RailsRoad`


## SOLUTIONS

the solutoin to this problem is done using both ` adjacency Matrix ` and `adjacency List` 

- ` adjacency Matrix ->  (26*26) grid which store (from->to) weight . suppose  you have AB5 to store the value in the matrix it will be like this (adj_matrix[A-'A'][B-'A'] = 5)  -> (addj_matrix[0][1] = 5)`
- `adjacency List -> each city represented as Vertex and the city routes represented as List<Edge> inside Vertex class  and each edge has its own weight and its own destination , so the adj_list is represented as List< Vertex >  adj_list   `

- (1-5) the solution is done using adj matrix   

- (6-7) the solution is done using depth first search  with data represented as adj_list , for each `city = vertex` you traverse all of its edges and subtract the remainig steps by 1 until you reach your destination 

- (8-9) the solution is done using `dijkistra algorithm (shortest path algorithm)` using arrays with complexity of  O(V*V) there is also another solution using priority queue with a better complexity O(V log (E)) 

- (10) the solution is done using simple recursive function  with memoization technique so for each vertex you visit every edge .
and every time you reached a vertex with a different (milage or steps) it's considered to be a different route , and the memoization techinique prevent us from visiting the same state twice , however the maximum total steps it can handle is 10000 , so instead of using 2d array its better if I have used HashMap , solution complexity is (E*V)
