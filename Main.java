import dataingestion.Dataingestion;
import dataingestion.DatahashMap;
import cataloging.ProductCatalog;
import searchengine.BST;
import searchengine.MinHeap;
import optimizer.GreedyOptimizer;
import optimizer.DPOptimizer;
import visualization.Graph;

/**
 * AlgoVision 2.0 - Unified Analytics Platform
 * Entry point demonstrating all 5 DSA modules.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("       AlgoVision 2.0 - Demo Run         ");
        System.out.println("=========================================\n");

        // ── Module 1: Data Ingestion ──────────────────────────────
        System.out.println("━━━ MODULE 1: DATA INGESTION (Queue + Hashing) ━━━");
        DataQueue queue = new DataQueue();
        queue.enqueue("Order#101 - Laptop");
        queue.enqueue("Order#102 - Keyboard");
        queue.enqueue("Order#103 - Monitor");
        queue.displayQueue();
        System.out.println("Processing: " + queue.dequeue());

        DataHashMap hashMap = new DataHashMap(10);
        hashMap.insert("user_001", "Alice - Mumbai");
        hashMap.insert("user_002", "Bob   - Delhi");
        hashMap.insert("user_003", "Carol - Bengaluru");
        hashMap.display();
        System.out.println("Lookup user_002 → " + hashMap.search("user_002") + "\n");

        // ── Module 2: Cataloging ──────────────────────────────────
        System.out.println("━━━ MODULE 2: CATALOGING (Arrays) ━━━");
        ProductCatalog catalog = new ProductCatalog();
        catalog.addProduct(104, "Headphones", 1500.0);
        catalog.addProduct(101, "Laptop",     75000.0);
        catalog.addProduct(103, "Monitor",    18000.0);
        catalog.addProduct(102, "Keyboard",   2500.0);
        catalog.displayAll();
        catalog.sortByPrice();
        System.out.println("After sorting by price:");
        catalog.displayAll();
        System.out.println("Binary search for 'Monitor' → " + catalog.binarySearchByName("Monitor") + "\n");

        // ── Module 3: Search Engine ───────────────────────────────
        System.out.println("━━━ MODULE 3: SEARCH ENGINE (BST + Heap) ━━━");
        BST bst = new BST();
        int[] prices = {50000, 20000, 75000, 10000, 30000, 60000, 90000};
        for (int p : prices) bst.insert(p);
        System.out.print("BST In-order (sorted prices): ");
        bst.inorder();
        System.out.println("\nSearch ₹30000 → " + (bst.search(30000) ? "Found" : "Not Found"));
        System.out.println("Min price → ₹" + bst.findMin());
        System.out.println("Max price → ₹" + bst.findMax());

        MinHeap heap = new MinHeap(10);
        int[] priorities = {5, 2, 8, 1, 6, 3};
        for (int p : priorities) heap.insert(p);
        System.out.print("\nHeap extract (ascending): ");
        while (!heap.isEmpty()) System.out.print(heap.extractMin() + " ");
        System.out.println("\n");

        // ── Module 4: Optimizer ───────────────────────────────────
        System.out.println("━━━ MODULE 4: OPTIMIZER (Greedy + DP) ━━━");
        // Fractional Knapsack
        int[] weights = {2, 3, 5, 7};
        int[] values  = {10, 15, 20, 25};
        int capacity  = 10;
        double maxVal = GreedyOptimizer.fractionalKnapsack(weights, values, capacity);
        System.out.printf("Greedy Fractional Knapsack (cap=%d) → Max Value = %.2f%n", capacity, maxVal);

        // Activity Selection
        System.out.print("Activity Selection (max activities): ");
        int[][] activities = {{1,3},{2,5},{4,6},{6,8},{5,7},{8,9}};
        GreedyOptimizer.activitySelection(activities);

        // 0/1 Knapsack DP
        int dpVal = DPOptimizer.knapsack01(weights, values, capacity);
        System.out.println("DP 0/1 Knapsack (cap=" + capacity + ") → Max Value = " + dpVal);

        // Longest Common Subsequence
        String s1 = "ALGOVISION", s2 = "ALGORITHM";
        System.out.println("LCS(\"" + s1 + "\", \"" + s2 + "\") → " + DPOptimizer.lcs(s1, s2));

        // Coin Change
        int[] coins = {1, 5, 10, 25};
        System.out.println("Min coins for ₹41 → " + DPOptimizer.coinChange(coins, 41) + "\n");

        // ── Module 5: Visualization (Graph) ──────────────────────
        System.out.println("━━━ MODULE 5: VISUALIZATION (Graph) ━━━");
        Graph g = new Graph(6);
        // Logistics network: 0=Warehouse, 1=Hub-A, 2=Hub-B, 3=Delhi, 4=Mumbai, 5=Bengaluru
        String[] cities = {"Warehouse","Hub-A","Hub-B","Delhi","Mumbai","Bengaluru"};
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 3, 5);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 4, 8);
        g.addEdge(3, 4, 2);
        g.addEdge(3, 5, 6);
        g.addEdge(4, 5, 3);

        System.out.println("Logistics Network Adjacency List:");
        g.displayGraph(cities);
        System.out.print("\nBFS from Warehouse: ");
        g.bfs(0, cities);
        System.out.print("\nDFS from Warehouse: ");
        g.dfs(0, cities);
        System.out.println("\n\nDijkstra Shortest Paths from Warehouse:");
        g.dijkstra(0, cities);

        System.out.println("\n=========================================");
        System.out.println("       AlgoVision 2.0 - Complete!        ");
        System.out.println("=========================================");
    }
}
