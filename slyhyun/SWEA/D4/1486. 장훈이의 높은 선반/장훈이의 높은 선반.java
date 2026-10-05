/*
 * [문제]
 * - 높이가 B인 선반이 하나 있다.
 * - N명의 점원들이 선반 위에 올려놓은 물건을 사용해야 한다.
 * - 각 점원의 키는 Hi로, 점원으로 탑을 쌓아서 선반 위의 물건을 사용한다.
 * - 탑의 높이는 탑을 이룬 점원들의 키의 합
 * - 탑의 높이가 B 이상이면서 가장 낮은 탑을 알아낸다.
 * - 조건을 만족하는 탑의 높이와 선반 높이의 차를 구한다.
 *
 * [입력]
 * - 점원의 수 N: 1 ~ 20
 * - 점원들 키의 합: S
 * - 선반 높이 B: 1 ~ S
 * - 점원의 키 Hi: 1 ~ 10,000
 *
 * [설계]
 * - 뽑는 순서에 따라 결과가 달라지지 않고, 개수가 정해져있지 않으므로 부분집합 문제
 * - 탑의 높이가 B 이상이 되면, 최솟값을 갱신한다.
 * - 현재 탑의 높이가 최솟값 이상이라면 가지치기를 한다.
 */
import java.io.*;
import java.util.*;

public class Solution {
    static int N, B;
    static int[] heights;
    static int min;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());

            heights = new int[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                heights[i] = Integer.parseInt(st.nextToken());
            }

            min = Integer.MAX_VALUE;

            dfs(0, 0);

            sb.append("#").append(tc).append(" ").append(min - B).append("\n");
        }

        System.out.print(sb);
    }

    static void dfs (int idx, int sum) {
        if (sum >= min) return;
        if (sum >= B) {
            min = Math.min(min, sum);
            return;
        }
        if (idx == N) return;

        dfs(idx + 1, sum + heights[idx]);
        dfs(idx + 1, sum);
    }
}
