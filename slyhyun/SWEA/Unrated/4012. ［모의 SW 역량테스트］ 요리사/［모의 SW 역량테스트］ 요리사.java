import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    static int[][] S;
    static boolean[] visited;
    static int min;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            S = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    S[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            visited = new boolean[N];
            min = Integer.MAX_VALUE;
            
            dfs(0, 0);

            sb.append("#").append(tc).append(" ").append(min).append("\n");
        }
        
        System.out.print(sb);
    }

    static void dfs(int idx, int cnt) {
        if (cnt == N / 2) {
            int sumA = 0;
            int sumB = 0;

            for (int i = 0; i < N - 1; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (visited[i] && visited[j]) {
                        sumA += S[i][j] + S[j][i];
                    }
                    else if (!visited[i] && !visited[j]) {
                        sumB += S[i][j] + S[j][i];
                    }
                }
            }

            int diff = Math.abs(sumA - sumB);
            if (diff < min) {
                min = diff;
            }
            
            return;
        }

        for (int i = idx; i < N; i++) {
            visited[i] = true;
            dfs(i + 1, cnt + 1);
            visited[i] = false;
        }
    }
}
