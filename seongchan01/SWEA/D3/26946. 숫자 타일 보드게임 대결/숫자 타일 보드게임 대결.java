import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제 해석]
 * 도윤/세아 -> 0~9까지 숫자가 적힌 타일이 한 줄, 위에서부터 차례대로 한 장씩 번갈아가며 집어감
 * 도윤 -> 세아 -> 도윤 -> 세아 ... 순서
 * 다음 승리 조건 두 가지 중 하나라도 만들어지면 그 사람이 승리
 * 
 * 승리 조건
 * 	1) 트리플: 같은 숫자 타일 3장 이상 모은 경우
 * 	2) 스트레이트: 연속된 숫자(예. 4,5,6)의 타일을 각각 1장씩 3개 이어서 모은 경우
 * 
 * 도윤이가 이기면 1, 세아가 이기면 2, 아무도 이기지 못하면 0 출력
 * 
 * [풀이]
 * 도윤과 세아가 가진 각 숫자 타일의 개수를 배열에 저장한다
 * 타일을 한 장 가져올 때마다 트리플과 스트레이트를 각각 확인한다
 */

public class Solution {

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			int[] arr = new int[12];

			StringTokenizer st = new StringTokenizer(br.readLine());

			for (int i = 0; i < 12; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}

			// 0~9 숫자 타일을 몇 개 가지고 있는지
			int[] doyun = new int[10];
			int[] sea = new int[10];

			int answer = 0;

			for (int i = 0; i < 12; i++) {

				// 도윤 : 짝수만
				if (i % 2 == 0) {
					doyun[arr[i]]++;

					// 트리플
					for (int j = 0; j < 10; j++) {
						if (doyun[j] >= 3) {
							answer = 1;
							break;
						}
					}

					// 스트레이트
					if (answer == 0) {
						for (int j = 0; j <= 7; j++) {
							if (doyun[j] > 0 && doyun[j + 1] > 0 && doyun[j + 2] > 0) {
								answer = 1;
								break;
							}
						}
					}

					// 세아
				} else {
					sea[arr[i]]++;

					// 트리플
					for (int j = 0; j < 10; j++) {
						if (sea[j] >= 3) {
							answer = 2;
							break;
						}
					}

					// 스트레이트
					if (answer == 0) {
						for (int j = 0; j <= 7; j++) {
							if (sea[j] > 0 && sea[j + 1] > 0 && sea[j + 2] > 0) {
								answer = 2;
								break;
							}
						}
					}
				}

				if (answer != 0) {
					break;
				}
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}
}