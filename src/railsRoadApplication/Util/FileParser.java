package railsRoadApplication.Util;

import railsRoadApplication.Graph.Graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileParser {

    public void fileReader(String fileName , Graph graph)  {

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(x -> {
                graph.setAdjList(x);
                graph.setAdjMatrix(x);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

