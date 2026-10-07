import java.io.*;
import java.util.*;

public class Solution {
    static class Edge {
        int from, to;
        long cost;

        Edge(int from, int to, long cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    static int[] parent;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            
            long[] x = new long[N];
            long[] y = new long[N];
            
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                x[i] = Long.parseLong(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                y[i] = Long.parseLong(st.nextToken());
            }

            double E = Double.parseDouble(br.readLine());

            ArrayList<Edge> edges = new ArrayList<>();
            
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    long dx = x[i] - x[j];
                    long dy = y[i] - y[j];
                    long L = dx * dx + dy * dy;
                    
                    edges.add(new Edge(i, j, L));
                }
            }

            Collections.sort(edges, (e1, e2) -> Long.compare(e1.cost, e2.cost));

            parent = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }

            long sum = 0;
            int cnt = 0;

            for (Edge edge : edges) {
                if (union(edge.from, edge.to)) {
                    sum += edge.cost;
                    cnt++;
                    
                    if (cnt == N - 1) break;
                }
            }

            double ans = sum * E;
            
            sb.append("#").append(tc).append(" ").append(Math.round(ans)).append("\n");
        }
        
        System.out.print(sb);
    }

    static int find(int a) {
        if (parent[a] == a) return a;
        
        return parent[a] = find(parent[a]);
    }

    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        
        if (rootA != rootB) {
            parent[rootB] = rootA;
            return true;
        }
        
        return false;
    }
}
