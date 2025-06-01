//src/algorithm/Algorithm.java
package algorithm;

import network.Edge;
import network.Network;
import java.util.*;

public class Algorithm implements MaxFlow {
    @Override
    public int findMaxFlow(Network network, int source, int sink) {
        int totalFlow = 0;
        int iteration = 1;
        List<Edge>[] graph = network.getGraph();

        System.out.println("\n--- Maximum Flow Algorithm Execution ---");

        while (true) {
            Edge[] parent = new Edge[graph.length];
            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);

            while (!queue.isEmpty() && parent[sink] == null) {
                int u = queue.poll();
                for (Edge e : graph[u]) {
                    if (parent[e.to] == null && e.residualCapacity() > 0 && e.to != source) {
                        parent[e.to] = e;
                        queue.add(e.to);
                    }
                }
            }

            if (parent[sink] == null) break;

            // Reconstruct and print the augmenting path
            System.out.println("\nIteration " + iteration + ":");

            // Build the path in reverse (from sink to source)
            List<Edge> path = new ArrayList<>();
            for (Edge e = parent[sink]; e != null; e = parent[e.from]) {
                path.add(e);
            }

            // Correctly print augmenting path
            System.out.print("Augmenting Path: " + source);
            for (int i = path.size() - 1; i >= 0; i--) {
                Edge e = path.get(i);
                System.out.print(" → " + e.to);
            }
            System.out.println();

            // Find minimum residual capacity
            int pathFlow = Integer.MAX_VALUE;
            for (Edge e = parent[sink]; e != null; e = parent[e.from]) {
                pathFlow = Math.min(pathFlow, e.residualCapacity());
            }

            System.out.println("Bottleneck Capacity: " + pathFlow);

            // Add flow and print the flow updates
            System.out.println("Flow Updates:");
            for (Edge e = parent[sink]; e != null; e = parent[e.from]) {
                System.out.println("  Edge " + e.from + " → " + e.to + ": " +
                        e.flow + " → " + (e.flow + pathFlow) +
                        " (capacity: " + e.capacity + ")");
                e.addFlow(pathFlow);
            }

            totalFlow += pathFlow;
            System.out.println("Current Total Flow: " + totalFlow);

            iteration++;
        }

        System.out.println("\n--- Final Maximum Flow: " + totalFlow + " ---");
        System.out.println("\nFinal Edge Flows:");
        for (int i = 0; i < graph.length; i++) {
            for (Edge e : graph[i]) {
                if (e.capacity > 0) { // Only print forward edges
                    System.out.println("  " + e.from + " → " + e.to + ": " +
                            e.flow + "/" + e.capacity);
                }
            }
        }

        return totalFlow;
    }
}
