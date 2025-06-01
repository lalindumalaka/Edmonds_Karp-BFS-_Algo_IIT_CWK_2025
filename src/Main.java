//src/Main.java
import algorithm.Algorithm;
import algorithm.MaxFlow;
import network.Network;
import parser.NetworkParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        try {

            String filePath = "input/1input.txt";

            System.out.println("\n====================================");
            System.out.println("Processing file: " + new File(filePath).getName());
            System.out.println("====================================");

            // Parse the network
            Network network = NetworkParser.parseFromFile(filePath);
            int source = 0;
            int sink = network.size() - 1;
            MaxFlow algorithm = new Algorithm();

            // Measure execution time
            long startTime = System.nanoTime();
            int maxFlow = algorithm.findMaxFlow(network, source, sink);
            long endTime = System.nanoTime();
            double elapsedTimeMs = (endTime - startTime) / 1_000_000.0; // Converting to milliseconds

            // Print summary to console
            System.out.println("\n====================================");
            System.out.printf("File: %-15s | Max Flow: %-7d | Time: %.2f ms%n",
                    new File(filePath).getName(), maxFlow, elapsedTimeMs);
            System.out.println("====================================\n");

            // Saving summary to file "summary.txt"
            try (PrintWriter summaryWriter = new PrintWriter(new FileWriter("summary.txt", true))) {
                summaryWriter.printf("File: %-15s | Max Flow: %-7d | Time: %.2f ms%n",
                        new File(filePath).getName(), maxFlow, elapsedTimeMs);
            }

            System.out.println("Summary saved to summary.txt.");

        } catch (IOException e) {
            System.err.println("Error reading input file or writing summary: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
        }
    }
}
