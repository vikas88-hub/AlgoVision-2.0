import java.util.Arrays;

/**
 * MODULE 4B: OPTIMIZER — Dynamic Programming
 *
 * Algorithms:
 *   1. 0/1 Knapsack    → max cart value within budget
 *   2. LCS             → match product descriptions
 *   3. Coin Change     → min coins (exact, works for any denominations)
 *   4. Fibonacci       → memoization demo
 *
 * DP Principle: Break into overlapping sub-problems,
 * solve once, store in table (tabulation).
 */
class DPOptimizer {

    // ── 1. 0/1 Knapsack ─────────────────────────────────────
    // Real-world: select products to maximize value within weight/budget
    // Time: O(n x W)  |  Space: O(n x W)
    public static int knapsack01(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i-1][w]; // skip item i
                if (weights[i-1] <= w)
                    dp[i][w] = Math.max(dp[i][w], dp[i-1][w - weights[i-1]] + values[i-1]);
            }
        }

        // Trace which items selected
        System.out.print("  0/1 Knapsack selected: ");
        int w = capacity;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                System.out.print("Item" + i + "(w=" + weights[i-1] + ",v=" + values[i-1] + ") ");
                w -= weights[i-1];
            }
        }
        System.out.println();
        return dp[n][capacity];
    }

    // ── 2. Longest Common Subsequence (LCS) ─────────────────
    // Real-world: detect matching features between products
    // Time: O(m x n)  |  Space: O(m x n)
    public static int lcs(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = dp[i-1][j-1] + 1;
                else                                   dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);

        // Reconstruct LCS string
        StringBuilder sb = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) { sb.append(s1.charAt(i-1)); i--; j--; }
            else if (dp[i-1][j] > dp[i][j-1])       i--;
            else                                      j--;
        }
        System.out.println("  LCS string: \"" + sb.reverse() + "\"");
        return dp[m][n];
    }

    // ── 3. Coin Change (DP) ──────────────────────────────────
    // Real-world: payment optimization, cashback engine
    // Time: O(amount x coins)  |  Works for ANY denominations
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++)
            for (int coin : coins)
                if (coin <= i) dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ── 4. Fibonacci (Memoization) ───────────────────────────
    // Demonstrates: O(2^n) naive → O(n) with memoization
    private static long[] memo = new long[100];

    public static long fibonacci(int n) {
        if (n <= 1)       return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibonacci(n - 1) + fibonacci(n - 2);
    }
}
