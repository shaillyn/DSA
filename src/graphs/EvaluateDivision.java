package graphs;

import java.util.*;

/**
 * evaluate-division
 * Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
 * Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
 *
 */
public class EvaluateDivision {

    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();
        equations.add(Arrays.asList("a","b"));
        equations.add(Arrays.asList("b","c"));
        double[] values = new double[]{2.0,3.0};
        List<List<String>> queries = new ArrayList<>();
        queries.add(Arrays.asList("a","c"));
        queries.add(Arrays.asList("b","a"));
        queries.add(Arrays.asList("a","e"));
        queries.add(Arrays.asList("a","a"));

        double[] result = calcEquation(equations, values, queries);
        for(double res: result) {
            System.out.println(res);
        }
    }

    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // Create graph from equations and values
        Map<String, Map<String,Double>> graph = new HashMap<>();
        for(int i=0;i<equations.size();i++) {
            List<String> equation = equations.get(i);
            String dividend = equation.get(0);
            String divisor = equation.get(1);
            double quotient = values[i];

            graph.computeIfAbsent(dividend, val -> new HashMap<>()).put(divisor, quotient);
            graph.computeIfAbsent(divisor, v -> new HashMap<>()).put(dividend, 1/quotient);
        }

        // Compute query using DFS
        double[] result = new double[queries.size()];
        for(int i=0;i<queries.size();i++) {
            String dividend = queries.get(i).get(0);
            String divisor = queries.get(i).get(1);
            if(!graph.containsKey(dividend) || !graph.containsKey(divisor)) {
                result[i] = -1.0;
            } else if(dividend.equals(divisor)) {
                result[i] = 1.0;
            } else {
                Set<String> visited = new HashSet<>();
                result[i] = compute(graph, visited, dividend, divisor, 1);
            }
        }
        return result;
    }

    private static double compute(Map<String, Map<String,Double>> graph, Set<String> visited, String curr, String target, double prod) {
        double res = -1.0;
        visited.add(curr);
        Map<String,Double> neighbors = graph.get(curr);
        if(neighbors.containsKey(target)) {
            res = prod * neighbors.get(target);
        } else {
            for(Map.Entry<String,Double> entry: neighbors.entrySet()) {
                String str = entry.getKey();
                double val = entry.getValue();
                if(!visited.contains(str)) {
                    res = compute(graph, visited, str, target, prod*val);
                    if(res != -1) {
                        break;
                    }
                }
            }
        }
        return res;
    }
}
