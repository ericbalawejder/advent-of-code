package aoc.year2020.day10;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdapterArray {

    private static final int CHARGING_OUTLET_RATING = 0;

    public static void main(String[] args) throws IOException {
        final SortedSet<Integer> outputJoltage =
                readFileToTreeSet("src/main/java/aoc/year2020/day10/test2.txt");
        System.out.println(productOfJoltDifference(outputJoltage));
        System.out.println(findAllCombinationsOfAdapters(outputJoltage));

        final Graph<Integer, DefaultEdge> adapterGraph = generateDirectedGraph(outputJoltage);
        //visualizeGraph(adapterGraph); // Only for test data. Input data is too large.
        System.out.println(countAllPaths(adapterGraph, outputJoltage));
    }

    private static int productOfJoltDifference(SortedSet<Integer> sortedAdapters) {
        int currentJolt = CHARGING_OUTLET_RATING;
        int oneJolt = 0;
        int threeJolt = 0;
        for (Integer jolt : sortedAdapters) {
            int difference = jolt - currentJolt;
            if (difference == 1) {
                oneJolt++;
            } else if (difference == 3) {
                threeJolt++;
            }
            currentJolt = jolt;
        }
        return oneJolt * threeJolt;
    }

    private static long findAllCombinationsOfAdapters(SortedSet<Integer> sortedAdapters) {
        final Map<Integer, Long> combinations = new HashMap<>();
        combinations.put(0, 1L);

        for (Integer adapter : sortedAdapters) {
            long arrangements = 0;
            for (int difference = 1; difference <= 3; difference++) {
                int previous = adapter - difference;
                if (combinations.containsKey(previous)) {
                    arrangements += combinations.get(previous);
                }
            }
            combinations.put(adapter, arrangements);
        }
        return combinations.get(sortedAdapters.last());
    }

    static long countAllPaths(
            Graph<Integer, DefaultEdge> directedGraph, SortedSet<Integer> sortedAdapters) {

        final SortedSet<Integer> adapters = Stream.concat(Stream.of(0), sortedAdapters.stream())
                .collect(Collectors.toCollection(TreeSet::new));
        final AllDirectedPaths<Integer, DefaultEdge> paths = new AllDirectedPaths<>(directedGraph);
        final List<GraphPath<Integer, DefaultEdge>> longestPath =
                paths.getAllPaths(adapters.first(), adapters.last(), true, null);

        return longestPath.size();
    }

    static Graph<Integer, DefaultEdge> generateDirectedGraph(SortedSet<Integer> sortedAdapters) {
        final List<Integer> adapters = Stream.concat(Stream.of(0), sortedAdapters.stream()).toList();
        final Graph<Integer, DefaultEdge> directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        for (int i = 0; i < adapters.size() - 1; i++) {
            final List<Integer> others = adapters.subList(i + 1, adapters.size());
            for (Integer other : others) {
                if (other <= adapters.get(i) + 3) {
                    directedGraph.addVertex(adapters.get(i));
                    directedGraph.addVertex(other);
                    directedGraph.addEdge(adapters.get(i), other);
                }
            }
        }
        return directedGraph;
    }

    private static void visualizeGraph(Graph<Integer, DefaultEdge> directedGraph) throws IOException {
        JGraphXAdapter<Integer, DefaultEdge> graphAdapter = new JGraphXAdapter<>(directedGraph);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image = mxCellRenderer.createBufferedImage(
                graphAdapter, null, 2,
                Color.WHITE, true, null);

        File imgFile = new File("src/main/java/aoc/year2020/day10/graph.png");
        ImageIO.write(image, "PNG", imgFile);
    }

    private static SortedSet<Integer> readFileToTreeSet(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            SortedSet<Integer> adapters = stream.map(Integer::parseInt)
                    .collect(Collectors.toCollection(TreeSet::new));
            adapters.add(adapters.last() + 3);
            return Collections.unmodifiableSortedSet(new TreeSet<>(adapters));
        } catch (IOException e) {
            e.printStackTrace();
            return new TreeSet<>();
        }
    }

}
