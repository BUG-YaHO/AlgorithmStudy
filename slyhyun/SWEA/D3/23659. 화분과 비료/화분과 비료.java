import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int P = Integer.parseInt(st.nextToken());

            int[] arr1 = new int[N];
            int[] arr2 = new int[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                arr1[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                arr2[i] = Integer.parseInt(st.nextToken());
            }
						
						// DP용 2차원 배열 선언
            int[][] dp = new int[N][2];

            dp[0][0] = arr1[0];
            dp[0][1] = arr2[0];
						
						// 문제에서는 2번 연속 같은 비료를 뿌리면 지금 화분이 P만큼 덜 자란다고 했다.
						// 그런데 굳이 지금 화분에 -P 해줄 필요가 없고 이전 선택에 -P를 해준 뒤 최댓값을 구해주면 된다.
						// 그렇게 해서 연속으로 뿌려서 -P와 다른걸 뿌렸을 때 더 큰 값을 선택한다.
            for (int i = 1; i < N; i++) {
                dp[i][0] = Math.max(dp[i - 1][0] - P, dp[i - 1][1]) + arr1[i];
                dp[i][1] = Math.max(dp[i - 1][1] - P, dp[i - 1][0]) + arr2[i];
            }

            int max = Math.max(dp[N - 1][0], dp[N - 1][1]);

            sb.append("#").append(tc).append(" ").append(max).append("\n");
        }

        System.out.println(sb);
    }
}
