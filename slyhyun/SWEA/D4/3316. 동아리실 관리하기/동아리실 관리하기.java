/*
 * [문제]
 * - A, B, C, D 4명의 사람이 있다.
 * - N일간의 활동동안 4명의 사람이 참여할 수도 있고, 참여하지 않을 수도 있다.
 * - 그러나 아무도 활동에 참여하지 않을 수는 없으므로, 하루에 총 16가지의 경우의 수가 있다.
 * - N일동안 각 활동의 책임자는 무조건 참여해야 한다.
 * - 동아리 실의 열쇠를 소유하는 한 사람은 무조건 활동에 참여해야 한다.
 * - 오늘 활동에 참여하는 사람 중에 내일 활동에도 참여하는 사람이 있다면 열쇠를 넘겨줄 수 있다.
 * - 첫 번째 날에는 A가 열쇠를 가지고 있고, 모든 활동이 끝나면 누가 가지고 있어도 괜찮다.
 * - 열쇠를 넘겨줄 수 없어, 아무도 열쇠를 가지지 못하게 되는 경우는 있어서는 안된다.
 * - N일 동안의 동아리 활동을 할 수 있는 경우의 수를 출력한다.
 * 
 * [입력]
 * - 활동 기간 N: 1 ~ 10,000
 * - i번재 문자는 i번째 날의 책임자
 * 
 * [출력]
 * - 경우의 수를 1,000,000,007로 나눈 나머지를 출력
 * 
 * [설계]
 * - 완전 탐색(DFS) 시 시간 복잡도는 O(15^N)으로 시간 초과 발생.
 * - 질문자님의 DFS+메모이제이션 논리는 완벽하나, N=10,000일 때 자바 스택 제한으로 인해 런타임 에러(StackOverflow) 발생.
 * - 따라서 함수 호출이 없는 반복문(Bottom-Up) 방식의 DP로 구현하여 메모리 한계를 극복함.
 * 
 * [DP 테이블 정의]
 * - dp[day][status]: day번째 날에 status 참석 상태(1~15)일 때의 누적 경우의 수.
 */
import java.io.*;
import java.util.*;

public class Solution {
    static final int MOD = 1000000007;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            String managers = br.readLine().trim();
            int N = managers.length();

            // dp[날짜][참석상태] -> 0으로 자동 초기화되므로 -1을 채울 필요가 없습니다.
            int[][] dp = new int[N][16];

            // 1. 첫 번째 날 (day = 0) 초기화
            int firstManager = managers.charAt(0) - 'A';
            for (int curr = 1; curr < 16; curr++) {
                // [질문자님 조건 1] 첫날 책임자가 포함되어야 함
                if ((curr & (1 << firstManager)) == 0) continue;
                // [질문자님 조건 2] 첫날은 무조건 A(비트 0)가 포함되어야 함
                if ((curr & 1) == 0) continue;

                dp[0][curr] = 1; // 조건을 만족하면 경우의 수 1개로 시작
            }

            // 2. 두 번째 날부터 마지막 날까지 반복문으로 확장 (재귀 함수 호출 없음)
            for (int day = 1; day < N; day++) {
                int manager = managers.charAt(day) - 'A';

                for (int curr = 1; curr < 16; curr++) {
                    // [질문자님 조건 1] 오늘의 책임자가 오늘 조합(curr)에 없으면 패스
                    if ((curr & (1 << manager)) == 0) continue;

                    // 어제의 모든 조합(prev)을 확인
                    for (int prev = 1; prev < 16; prev++) {
                        if (dp[day - 1][prev] == 0) continue; // 어제 불가능했던 조합은 건너뜀

                        // [질문자님 조건 3] 어제 참석자(prev)와 오늘 참석자(curr) 중 겹치는 사람이 있어야 열쇠 전달 가능
                        if ((prev & curr) != 0) {
                            dp[day][curr] = (dp[day][curr] + dp[day - 1][prev]) % MOD;
                        }
                    }
                }
            }

            // 3. 마지막 날(N-1)에 쌓인 모든 경우의 수를 다 더해줌
            int totalResult = 0;
            for (int status = 1; status < 16; status++) {
                totalResult = (totalResult + dp[N - 1][status]) % MOD;
            }

            sb.append("#").append(tc).append(" ").append(totalResult).append("\n");
        }
        
        System.out.print(sb);
    }
}
