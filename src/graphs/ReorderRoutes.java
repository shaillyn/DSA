package graphs;

import java.util.HashMap;
import java.util.Map;

/**
 * reorder-routes-to-make-all-paths-lead-to-the-city-zero
 * Input: n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]]
 * Output: 3
 *
 */
public class ReorderRoutes {
    static int count = 0;
    public static void main(String[] args) {
        int[][] connections = new int[][]{{0,1},{1,3},{2,3},{4,0},{4,5}};
        System.out.println(minReorder(6, connections));
    }

    public static int minReorder(int n, int[][] connections) {
        Map<Integer, Map<Integer,Integer>> adj = new HashMap<>();
        for(int[] conn: connections) {
            adj.computeIfAbsent(conn[0], v -> new HashMap<>()).put(conn[1], 1);
            adj.computeIfAbsent(conn[1], v -> new HashMap<>()).put(conn[0], 0);
        }
        boolean[] visited = new boolean[n];
        dfs(adj, visited, 0);
        return count;
    }

    private static void dfs(Map<Integer, Map<Integer,Integer>> adj, boolean[] visited, int root) {
        visited[root] = true;
        Map<Integer,Integer> neighbor = adj.get(root);
        for(Map.Entry<Integer, Integer> nei: neighbor.entrySet()) {
            if(!visited[nei.getKey()]) {
                count += nei.getValue();
                dfs(adj, visited, nei.getKey());
            }
        }
    }
}
