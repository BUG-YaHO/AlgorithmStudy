import java.util.*;

class Solution {
    private int[] parent;

    public int solution(int n, int[][] costs) {
        int answer = 0;

        Arrays.sort(costs, (a, b) -> Integer.compare(a[2], b[2]));

        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int connectedEdges = 0;

        for (int[] cost : costs) {
            if (connectedEdges == n - 1) break;

            int islandA = cost[0];
            int islandB = cost[1];
            int bridgeCost = cost[2];

            if (find(islandA) != find(islandB)) {
                union(islandA, islandB);
                answer += bridgeCost;
                connectedEdges++;
            }
        }

        return answer;
    }

    private int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            parent[rootY] = rootX;
        }
    }
}
