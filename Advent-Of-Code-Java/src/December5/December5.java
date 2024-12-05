package December5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class December5 {
    public static void main(String[] args) {
        try {
            First();
            Second();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void First() throws FileNotFoundException {
        var input = new File("src/December5/Data/input.txt");
        Scanner myReader = new Scanner(input);
        var pageOrderingRules = getPageOrderingRules(myReader);

        var middlePageSum = 0;
        while (myReader.hasNextLine()) {
            var data = myReader.nextLine().split(",");
            if (isCorrectOrder(data, pageOrderingRules)) {
                var middlePage = Integer.parseInt(data[data.length / 2]);
                middlePageSum += middlePage;
            }
        }
        System.out.println(middlePageSum);
    }

    public static void Second() throws FileNotFoundException {
        var input = new File("src/December5/Data/input.txt");
        Scanner myReader = new Scanner(input);
        var pageOrderingRules = getPageOrderingRules(myReader);

        // Now read the updates
        List<List<Integer>> updates = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine().trim();
            if (line.isEmpty()) continue; // Skip empty lines
            String[] parts = line.split(",");
            List<Integer> update = new ArrayList<>();
            for (String part : parts) {
                update.add(Integer.parseInt(part.trim()));
            }
            updates.add(update);
        }
        myReader.close();

        // Process each update
        int middlePageSum = 0;
        for (List<Integer> update : updates) {
            if (isCorrectOrder(update, pageOrderingRules)) {
                continue;
            }

            // Incorrectly ordered; fix it
            List<Integer> corrected = topologicalSort(update, pageOrderingRules);
            if (corrected == null) {
                System.out.println("Cycle detected in rules for update: " + update);
                continue; // Or handle the cycle appropriately
            }
            // Find the middle page
            int middleIndex = corrected.size() / 2;
            int middlePage = corrected.get(middleIndex);
            middlePageSum += middlePage;
        }
        System.out.println(middlePageSum);
    }

    private static Map<Integer, Set<Integer>> getPageOrderingRules(Scanner myReader) {
        Map<Integer, Set<Integer>> pageOrderingRules = new HashMap<>();

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine().trim();
            if (data.isEmpty()) break;
            String[] split = data.split("\\|");
            if (split.length != 2) continue; // Invalid rule format, skip

            int before = Integer.parseInt(split[0].trim());
            int after = Integer.parseInt(split[1].trim());

            pageOrderingRules.computeIfAbsent(before, k -> new HashSet<>()).add(after);
        }

        return pageOrderingRules;
    }

    private static boolean isCorrectOrder(String[] data, Map<Integer, Set<Integer>> pageOrderingRules) {
        var previousPages = new HashSet<>();

        for (int i = 0; i < data.length; i++) {
            var page = Integer.parseInt(data[i]);
            if (previousPages.contains(page)) {
                continue;
            }

            if (pageOrderingRules.containsKey(page)) {
                var set = pageOrderingRules.get(page);
                for (var rule : set) {
                    if (previousPages.contains(rule)) {
                        return false;
                    }
                }
            }

            previousPages.add(page);
        }
        return true;
    }

    private static boolean isCorrectOrder(List<Integer> data, Map<Integer, Set<Integer>> pageOrderingRules) {
        var previousPages = new HashSet<Integer>();
        
        for (var page : data) {
            if (previousPages.contains(page)) {
                continue;
            }

            if (pageOrderingRules.containsKey(page)) {
                var set = pageOrderingRules.get(page);
                for (var rule : set) {
                    if (previousPages.contains(rule)) {
                        return false;
                    }
                }
            }

            previousPages.add(page);
        }
        return true;
    }

    private static List<Integer> topologicalSort
            (List<Integer> update, Map<Integer, Set<Integer>> orderingRules) {
        // Extract relevant rules for the current update
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        // Initialize graph and inDegree
        for (int page : update) {
            graph.put(page, new HashSet<>());
            inDegree.put(page, 0);
        }

        // Build the graph based on ordering rules
        for (int page : update) {
            if (orderingRules.containsKey(page)) {
                for (int dependent : orderingRules.get(page)) {
                    if (graph.containsKey(dependent)) {
                        graph.get(page).add(dependent);
                        inDegree.put(dependent, inDegree.get(dependent) + 1);
                    }
                }
            }
        }

        // Kahn's algorithm for topological sorting
        Queue<Integer> queue = new LinkedList<>();
        for (int page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) {
                queue.add(page);
            }
        }

        List<Integer> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sorted.add(current);

            for (int neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Check if topological sort was possible (i.e., no cycles)
        if (sorted.size() != update.size()) {
            return null; // Cycle detected
        }

        return sorted;
    }
}
