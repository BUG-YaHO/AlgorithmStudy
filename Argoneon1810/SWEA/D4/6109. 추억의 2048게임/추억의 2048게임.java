import java.io.*;
import java.util.*;

/**
 * 그리드를 일단 다 받고 시작해도.
 * 
 * 방향이 숫자가 아니라 문자 토큰으로 주어짐
 * - 반드시 소문자로 넷 중 하나로 준다 하므로 lowercase 만들 필요는 X
 * - String equals 쓰면 실제 필요한 것 보다 연산이 무거움. 첫 글자만 써서 구분
 * 
 * 보드를 찌그리는 규칙이 뭘까
 * - 예시처럼 위쪽으로 찌그려야 한다면, 위쪽 열이 기준이다
 *   - 일반화된 함수를 굳이 만들려 하지 말고 각각 만드는게 차라리 빠르겠다
 * - 각 열/행 (찌그림 방향에 따라 다름)은 서로 독립이다.
 * - 시작 열부터:
 *   1. 현재 셀을 시작 셀로
 *   2. 자신이 0이라면 가장 가까운 다음 0이 아닌 숫자와 자신을 바꾼다.
 *   3. 다음 숫자가 존재하지 않을 때 까지 반복:
 *      - 다음 숫자를 확인:
 *        - 그 숫자가 자신과 같다면 그것과 자신을 더한다.
 *        - 그 숫자가 자신과 다르다면 현재 셀을 다음 셀로 하고 2부터 반복
 * 
 * 지금 보니 rotate1d랑 collapse를 나눠야 시간복잡도를 아낄 수 있을 거 같은데
 * 일단 시간 없으니까 그대로 간다.
 */

public class Solution {
	static final int UP=0, DOWN=1, LEFT=2, RIGHT=3;

	static int toDir(String S) {
		char c = S.charAt(0);
		switch(c) {
			default:
			case 'u':
				return 0;
			case 'd':
				return 1;
			case 'l':
				return 2;
			case 'r':
				return 3;
		}
	}

	static void swapVertical(
		int[][] board, 
		int column,
		int a,
		int b
	) {
		int temp = board[a][column];
		board[a][column] = board[b][column];
		board[b][column] = temp;
	}
	static void swapHorizontal(int[] row, int a, int b) {
		int temp = row[a];
		row[a] = row[b];
		row[b] = temp;
	}

	static void inverseVertical(
		int[][] board, 
		int N,
		int column
	) {
		for(int i=0; i<N/2; ++i)
			swapVertical(board, column, i, N-1-i);
	}

	static void inverseHorizontal(int[] row, int N) {
		for(int i=0; i<N/2; ++i)
			swapHorizontal(row, i, N-1-i);
	}

	static int findNextNonZeroIdxVertical(
		int[][] board, 
		int N,
		int column,
		int fromIdx
	) {
		for(int i=fromIdx; i>=0; --i)
			if(board[i][column] > 0)
				return i;
		return -1;
	}

	static int findNextNonZeroIdxHorizontal(
		int[] row, 
		int N,
		int fromIdx
	) {
		for(int i=fromIdx; i>=0; --i)
			if(row[i] > 0)
				return i;
		return -1;
	}

	static void collapseLineVertical(
		int[][] board, 
		int N,
		int column,
		boolean inverse
	) {
		if(inverse) inverseVertical(board, N, column);
		// down은 inverse를 주고 up으로 처리
		// 이제 vertical은 up 하나로 처리 가능

		// 시작 셀 설정 (up 기준)
		int currentIdx = N-1;
		while(currentIdx > 0) {
			// 현재 셀이 비어 있는 경우
			if(board[currentIdx][column] == 0) {
				// 현재 칸을 다음 칸으로 채워야 함
				int nextIdx = findNextNonZeroIdxVertical(board, N, column, currentIdx-1);
				// 다음 칸이 존재하지 않을 때
				if (nextIdx == -1)
					// 더 계산할 필요가 없음
					break;
				// 다음 칸이 있었다면 그것과 빈 셀을 교환
				swapVertical(board, column, currentIdx, nextIdx);
				// 이 다음 태스크는 다음 이터레이션으로 이관
				continue;
			}
			// 현재 칸이 뭔가로 차 있는 경우
			// 현재 칸과 다음 칸을 합칠 수 있으면 합쳐야 함
			int nextIdx = findNextNonZeroIdxVertical(board, N, column, currentIdx-1);
			// 다음 칸이 존재하지 않을 때
			if (nextIdx == -1)
				// 더 계산할 필요가 없음
				break;
			// 다음 칸이 있었고, 그 칸의 값이 내 값과 같으면
			if (board[currentIdx][column] == board[nextIdx][column]) {
				// 그 칸을 비우고 현재 칸의 값을 두배
				board[currentIdx][column]*=2;
				board[nextIdx][column] = 0;
			}
			// 2048의 룰에 따르면
			// 두 셀이 합쳐진 뒤에 같은 숫자가 서로 붙게 되더라도
			// 그것들을 연쇄적으로 합쳐주지는 않음
			// 따라서 현재 인덱스 갱신만 하면 됨
			// 합쳐지지 않았다면 어차피 0 처리 규칙에 의해 빈칸을 채워 주므로
			// 이 역시 인덱스 갱신만 하면 됨
			--currentIdx;
		}

		// down이었으면 원래대로 되돌리기
		if(inverse) inverseVertical(board, N, column);
	}

	static void collapseLineHorizontal(
		int[] row, 
		int N,
		boolean inverse
	) {
		if(inverse) inverseHorizontal(row, N);
		// left는 inverse를 주고 right으로 처리
		// 이제 horizontal은 right 하나로 처리 가능

		// 시작 셀 설정 (right 기준)
		int currentIdx = N-1;
		while(currentIdx > 0) {
			// 현재 셀이 비어 있는 경우
			if(row[currentIdx] == 0) {
				// 현재 칸을 다음 칸으로 채워야 함
				int nextIdx = findNextNonZeroIdxHorizontal(row, N, currentIdx-1);
				// 다음 칸이 존재하지 않을 때
				if (nextIdx == -1)
					// 더 계산할 필요가 없음
					break;
				// 다음 칸이 있었다면 그것과 빈 셀을 교환
				swapHorizontal(row, currentIdx, nextIdx);
				// 이 다음 태스크는 다음 이터레이션으로 이관
				continue;
			}
			// 현재 칸이 뭔가로 차 있는 경우
			// 현재 칸과 다음 칸을 합칠 수 있으면 합쳐야 함
			int nextIdx = findNextNonZeroIdxHorizontal(row, N, currentIdx-1);
			// 다음 칸이 존재하지 않을 때
			if (nextIdx == -1)
				// 더 계산할 필요가 없음
				break;
			// 다음 칸이 있었고, 그 칸의 값이 내 값과 같으면
			if (row[currentIdx] == row[nextIdx]) {
				// 그 칸을 비우고 현재 칸의 값을 두배
				row[currentIdx]*=2;
				row[nextIdx] = 0;
			}
			// 2048의 룰에 따르면
			// 두 셀이 합쳐진 뒤에 같은 숫자가 서로 붙게 되더라도
			// 그것들을 연쇄적으로 합쳐주지는 않음
			// 따라서 현재 인덱스 갱신만 하면 됨
			// 합쳐지지 않았다면 어차피 0 처리 규칙에 의해 빈칸을 채워 주므로
			// 이 역시 인덱스 갱신만 하면 됨
			--currentIdx;
		}
		
		// left였으면 원래대로 되돌리기
		if(inverse) inverseHorizontal(row, N);
	}

	static void collapseVertical(int[][] board, int N, boolean inverse) {
		for(int column=0; column<N; ++column)
			collapseLineVertical(board, N, column, inverse);
	}

	static void collapseHorizontal(int[][] board, int N, boolean inverse) {
		for(int row=0; row<N; ++row)
			collapseLineHorizontal(board[row], N, inverse);
	}

	/** 라우터 */
	static void collapse(int[][] board, int N, int towards) {
		switch(towards) {
		default:
		case UP:
			collapseVertical(board, N, false);
			break;
		case DOWN:
			collapseVertical(board, N, true);
			break;
		case LEFT:
			collapseHorizontal(board, N, true);
			break;
		case RIGHT:
			collapseHorizontal(board, N, false);
			break;
		}
	}

	static String getBoardString(int[][] board, int N) {
		StringBuilder sb = new StringBuilder();
		for (int j=N-1; j>=0; --j) { // 왼쪽 아래를 0,0으로
			for(int i=0; i<N; ++i) {
				if(i>0) sb.append(' ');
				sb.append(board[j][i]);
			}
			if (j>0) sb.append('\n');
		}
		return sb.toString();
	}

	static String solve(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		String S = st.nextToken();
		int[][] board = new int[N][N];
		for (int j=N-1; j>=0; --j) { // 왼쪽 아래를 0,0으로
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; ++i)
				board[j][i] = Integer.parseInt(st.nextToken());
		}
		collapse(board, N, toDir(S));
		return getBoardString(board, N);
	}

	public static void main(String...args) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for (int tc=1; tc<=T; ++tc)
			sb.append('#')
				.append(tc)
				.append('\n')
				.append(solve(br))
				.append('\n');
		System.out.print(sb.toString());
	}
}
