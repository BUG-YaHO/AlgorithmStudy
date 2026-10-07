import java.io.*;
import java.util.*;

public class Solution {
    static class Node {
        int to, weight;

        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            ArrayList<Node>[] adj = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++) {
                adj[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());
                int C = Integer.parseInt(st.nextToken());
                
                adj[A].add(new Node(B, C));
            }

            int ans = dijkstra(N, S, E, adj);
            
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        
        System.out.print(sb);
    }

    private static int dijkstra(int N, int S, int E, ArrayList<Node>[] adj) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);
        
        dist[S] = 0;
        pq.offer(new Node(S, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (curr.weight > dist[curr.to]) continue;
            if (curr.to == E) break;

            for (Node next : adj[curr.to]) {
                if (dist[next.to] <= dist[curr.to] + next.weight) continue;
                
                dist[next.to] = dist[curr.to] + next.weight;
                pq.offer(new Node(next.to, dist[next.to]));
            }
        }

        return dist[E] == Integer.MAX_VALUE ? -1 : dist[E];
    }
}
