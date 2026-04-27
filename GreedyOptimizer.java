import java.util.Arrays;

/**
 * MODULE 4A: OPTIMIZER — Greedy Algorithms
 *
 * Algorithms:
 *   1. Fractional Knapsack  → maximize delivery van value
 *   2. Activity Selection   → schedule max delivery slots
 *   3. Coin Change (Greedy) → minimum coins for cashback
 *
 * Greedy Principle: Pick the locally best choice at every step.
 */
class GreedyOptimizer {

    // ── 1. Fractional Knapsack ──────────────────────────────
    // Real-world: pack courier van to maximize revenue
    // Time: O(n log n)
    public static double fractionalKnapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        double[][] items = new double[n][3];
        for (int i = 0; i < n; i++) {
            items[i][0] = (double) values[i] / weights[i]; // value/weight ratio
            items[i][1] = weights[i];
            items[i][2] = values[i];
        }
        Arrays.sort(items, (a, b) -> Double.compare(b[0], a[0])); // sort desc by ratio

        double total = 0;
        int    rem   = capacity;
        System.out.println("  Greedy Knapsack (capacity=" + capacity + "):");
        for (double[] item : items) {
            if (rem == 0) break;
            int take = (int) Math.min(item[1], rem);
            total += take * item[0];
            rem   -= take;
            System.out.printf("    Take %d units (ratio=%.2f) → value+=%.2f%n",
                              take, item[0], take * item[0]);
        }
        return total;
    }

    // ── 2. Activity Selection ───────────────────────────────
    // Real-world: max non-overlapping delivery time slots
    // Time: O(n log n)
    // activities[i] = {startTime, endTime}
    public static void activitySelection(int[][] activities) {
        Arrays.sort(activities, (a, b) -> a[1] - b[1]); // sort by end time
        System.out.println("  Activity Selection:");
        int lastEnd = -1, count = 0;
        for (int[] act : activities) {
            if (act[0] >= lastEnd) {
                System.out.println("    SELECTED [" + act[0] + " - " + act[1] + "]");
                lastEnd = act[1];
                count++;
            } else {
                System.out.println("    SKIPPED  [" + act[0] + " - " + act[1] + "]");
            }
        }
        System.out.println("  Max activities = " + count);
    }

    // ── 3. Coin Change Greedy ───────────────────────────────
    // Real-world: give cashback in minimum coins
    // Time: O(n log n) — works correctly for standard denominations
    public static void greedyCoinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        System.out.println("  Greedy Coins for Rs." + amount + ":");
        int total = 0;
        for (int i = coins.length - 1; i >= 0 && amount > 0; i--) {
            int cnt = amount / coins[i];
            if (cnt > 0) {
                System.out.println("    Rs." + coins[i] + " x " + cnt);
                amount -= cnt * coins[i];
                total  += cnt;
            }
        }
        if (amount > 0) System.out.println("    Cannot make exact change");
        else            System.out.println("  Total coins: " + total);
    }
}
