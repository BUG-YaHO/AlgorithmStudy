import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    /**
     *
     * [문제 설명]
     * 규영이와 인영이의 1에서 18까지의 수가 적힌 18장의 카드
     * 한 번의 게임에 둘은 카드를 잘 섞어 9장씩 카드를 나눈다.
     * 라운드 당
     *  - 높은 수가 적힌 카드를 낸 사람 - 두 카드에 적힌 수의 합만큼 점수
     *  - 낮은 수가 적힌 카드를 낸 사람 - 점수 X
     * 9라운드를 끝내고 총점을 따졌을 때, 총점이 더 높은 사람이 이 게임의 승자가 된다.
     * 총점이 같으면 무승부이다.
     *
     * 규영이가 받은 9장의 카드에 적힌 수가 주어진다.
     * 규영이가 내는 카드의 순서를 고정하면, 인영이가 어떻게 카드를 내는지에 따른 9!가지 순서에 따라 규영이의 승패가 정해진다.
     * 규영이가 이기는 경우와 지는 경우가 총 몇 가지인지 구해라.
     *
     * [출력]
     * 인영이가 카드를 내는 9!가지 경우에 대해,
     * 규영이가 게임을 이기는 경우의 수와 지는 경우의 수를 공백을 두고 출력
     *
     *
     * [입력]
     * T
     * 첫 번째 줄 - 9개의 정수 (규영이가 카드를 내는 순서)
     *
     * [풀이 방법]
     * static int[9] kyArr : 규영이의 카드 배열
     * static int[9] iyArr : 인영이가 가질 수 있는 수의 카드 배열
     *  - 1~18까지 돌면서, 규영이의 카드에 없는 경우 iyArr에 넣어준다. O(n^2)
     * static boolean[9] isIyVisited : 인영카드 방문배열
     * static int kyScore : 규영이 현재까지 점수
     * static int iyScore : 인영이 현재까지 점수
     *
     * static int kyWinCount : 규영이가 게임을 이기는 경우(답)
     * static int kyLoseCount : 규영이가 게임을 지는 경우 (답)
     *
     * void dfs(int round: 현재 비교할 라운드)
     * 종료조건: round == 9
     *  - kyScore > iyScore라면 kyWinCount++
     *  - kyScore < iyScore라면 kyWinCount--;
     *  - return
     * for (9만큼 돌기)
     *  - isVisited[i] 안채워져있다면
     *   - kyArr[round]랑 iyArr[i] 비교해서 score 계산
     *   - isVisited[i] 방문처리
     *   - dfs
     *   - isVisited[i] 방문해제처리
     *   - 위에 저장해두었던 score값 다시 뺴주기(동점인 경우는 둘 다 안 빼도록 해야함)
     *
     *
     * [고려사항]
     * 카드 숫자는 1~18, 중복 없음
     *
     */

    static int[] kyArr;
    static int[] iyArr;
    static boolean[] isIyVisited;
    static int kyScore;
    static int iyScore;

    static int kyWinCount;
    static int kyLoseCount;

    public static void main(String args[]) throws Exception
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            kyArr = new int[9];
            iyArr = new int[9];
            isIyVisited = new boolean[9];

            kyScore = 0;
            iyScore = 0;
            kyWinCount = 0;
            kyLoseCount = 0;

            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int i = 0; i < 9; i++) {
                kyArr[i] = Integer.parseInt(st.nextToken());
            }

            // 인영이 카드 배열 채워주기
            int iyIndex = 0;
            for (int i = 1; i <= 18; i++) {
                boolean isKyCard = false;
                for (int j = 0; j < 9; j++) {
                    if (i == kyArr[j]) {
                        isKyCard = true;
                        break;
                    }
                }
                if (!isKyCard) {
                    iyArr[iyIndex] = i;
                    iyIndex++;
                    if (iyIndex == 9) {
                        break;
                    }
                }
            }

            dfs(0);


            sb.append("#").append(t).append(" ").append(kyWinCount).append(" ").append(kyLoseCount).append("\n");
        }

        System.out.println(sb.toString());
    }

    static void dfs(int round) {
        if (round == 9) {
            if (kyScore > iyScore) {
                kyWinCount++;
            } else if (kyScore < iyScore) {
                kyLoseCount++;
            }
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (isIyVisited[i]) {
                continue;
            }

            boolean isKyWin = false;
            boolean isIyWin = false;

            if (kyArr[round] > iyArr[i]) {
                isKyWin = true;
            } else if (kyArr[round] < iyArr[i]) {
                isIyWin = true;
            }

            int score = kyArr[round] + iyArr[i]; // 현재 계산할 점수

            if (isKyWin) {
                kyScore += score;
            } else if (isIyWin) {
                iyScore += score;
            }
            isIyVisited[i] = true;

            dfs(round + 1);

            isIyVisited[i] = false;
            if (isKyWin) {
                kyScore -= score;
            } else if (isIyWin) {
                iyScore -= score;
            }

        }
    }

}
