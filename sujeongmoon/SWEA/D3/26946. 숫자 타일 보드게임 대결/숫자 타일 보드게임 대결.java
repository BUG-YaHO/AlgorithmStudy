import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	
/**
 * 
 * 
 * [ 문제 해설 ]
 * 가운데에는 0~9까지의 숫자가 적힌 타일들이 한 줄로 쌓여있고, 위에서부터 차례대로 한 장씩 집어간다.
 * 쌓인 순서대로 타일을 번갈아가며 한 장씩 가져간다. (도윤-세아-도윤-... 순서)
 * 
 * 타일들 중에서 두 가지 중 하나라도 만들어지면 그 사람이 승리하며 게임이 끝난다.
 * 트리플 -> 같은 숫자의 타일을 3장 이상 모음
 * 스트레이트 -> 연속된 숫자의 타일을 각각 1장 이상씩, 3개 이어서 모은 경우
 * 아무도 승리하지 못하면 0

 * 
 * [ 출력 ]
 * 도윤이 이기면 1, 세아가 이기면 2, 완성 못하면 0 출력
 * 
 * 
 * [ 입력 ]
 * T
 * t줄마다 한 판에서 가져가는 12장의 타일 숫자
 * 
 * [ 제약조건 ]
 * 
 * 
 * [ 풀이방법 ]
 * 도윤이 카드의 배열과 세아 카드의 배열을 각각 만듦
 * 각각 인원의 타일이 채워질 때마다 sort를 통해 오름차순 정렬을 해줌
 * isTriple, isStraight를 통해 트리플이나 스트레이트가 완성되는지 판별
 * 
 * isTriple
 *  - 현재 값을 저장해두고, 최근 값 3개만 돌면서, 만약 해당 값과 다음 값이 같다면 count++해서 3 완성 여부로 판별
 *  
 * isStraight
 *  - 현재 값을 저장해두고, 최근 값 3개만 돌면서, 만약 해당 값+1과 다음 값이 같다면 count++해서 3 완성 여부로 판별
 * 
 */
	
	static int[] doyoonArr;
	static int[] seaArr;
	
	static int[] arr;
	

	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			arr = new int[12];
			doyoonArr = new int[6];
			seaArr = new int[6];
			
			Arrays.fill(doyoonArr, Integer.MAX_VALUE);
			Arrays.fill(seaArr, Integer.MAX_VALUE);
			
			// 카드 배열 채우기
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 12; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
		
		
			int answer = 0;
			
			// 각각의 손패 차례대로 채우면서 판별
			for (int i = 0; i < 12; i++) {
				
				int personIdx = i/2;
				
				// i가 짝수인 경우 - 도윤의 손패
				if (i % 2 == 0) {
					doyoonArr[personIdx] = arr[i];
					Arrays.sort(doyoonArr);
					if (isTriple(i) || isStraight(i)) {
						answer = 1;
						break;
					}
				}
				
				
				// i가 홀수인 경우 - 세아의 손패
				if (i % 2 == 1) {
					seaArr[personIdx] = arr[i];
					Arrays.sort(seaArr);
					if (isTriple(i) || isStraight(i)) {
						answer = 2;
						break;
					}
				}
			}
			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}

		
		System.out.println(sb);
	}
	
	static boolean isTriple(int index) {
		
		int[] nowArr;
		
		if (index % 2 == 0) {
			nowArr = doyoonArr;
		} else {
			nowArr = seaArr;
		}
		
		int nowIdx = index/2;
		// 3개가 채워지지 않은 경우 
		if (nowIdx < 2) {
			return false;
		}
		
		int beforeNumber = nowArr[0];
		int count = 1;
		for (int i = 1; i <= nowIdx; i++) {
			
			if (beforeNumber == nowArr[i]) {
				count++;
			} else {
				count = 1;
			}
			
			if (count == 3) {
				return true;
			}
			
			beforeNumber = nowArr[i];
			
		}
		
		return false;
		
	}
	
	static boolean isStraight(int index) {
				
		int[] nowArr;
		
		if (index % 2 == 0) {
			nowArr = doyoonArr;
		} else {
			nowArr = seaArr;
		}
		
		int nowIdx = index/2;
		// 3개가 채워지지 않은 경우 
		if (nowIdx < 2) {
			return false;
		}
		
		int beforeNumber = nowArr[0];
		int count = 1;
		for (int i = 1; i <= nowIdx; i++) {
			
			if (beforeNumber+1 == nowArr[i]) {
				count++;
			} else if (beforeNumber == nowArr[i]) { // 카드가 이전과 같다면 넘김
				continue;
			} else {
				count = 1;
			}
			
			if (count == 3) {
				return true;
			}
			
			beforeNumber = nowArr[i];
			
		}

		return false;
	}
	
}

