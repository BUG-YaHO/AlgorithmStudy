import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * 전선을 2차원 격자 칸 단위로 시뮬레이션하지 않고
 * 코어에서 가장자리까지 뻗는 축 정렬 선분으로 모델링한다.
 * 
 * 전선과 코어 사이의 교차 여부를 확인할 수 있어야 하기 때문에
 * 코어를 길이가 0인 선분으로 취급함으로써, 
 * Overlap 검사 하나로 점과 선을 일괄 처리한다.
 *
 * 문제 정의에 의하여 모든 선분은 축에 대하여 평행하므로,
 * 각 선분의 AABB 충돌 검사만으로 실제 선분의 교차 여부를 정확히 판정할 수 있다.
 *
 * 선분 배열의 앞부분에는 코어가 장애물로써 먼저 등록되며,
 * 전선 배치 시 그 뒤에 순차적으로 추가된다.
 * 
 * 백트래킹 시 별도의 Undo 연산 없이 선분 개수 파라미터만 이전 값으로 복귀시켜
 * 배열 메모리를 효율적으로 재사용한다.
 * </pre>
 */
class Solution {
	static final int[] DY = { 0, 0, -1, 1 };
	static final int[] DX = { -1, 1, 0, 0 };

	/** 코어가 가장자리에 위치하는지 (이미 전원과 연결되어 있는지) 확인. */
	boolean isOnEdge(int N, int y, int x) {
		return y == 0 || y == N-1 || x == 0 || x == N-1;
	}

	/** AABB Overlap Check (both inclusive) */
	boolean overlaps(int a1, int a2, int b1, int b2) {
		return a1 <= b2 && b1 <= a2;
	}

	/** 배치할 선분이 기존에 등록된 모든 선분들 중 하나라도 교차하는지 검사. */
	boolean intersects(
		int[] segY1, int[] segX1, int[] segY2, int[] segX2,
		int segCount, int y1, int x1, int y2, int x2
	) {
		for (int i=0; i<segCount; ++i)
			if (overlaps(segY1[i], segY2[i], y1, y2)
					&& overlaps(segX1[i], segX2[i], x1, x2))
				return true;
		return false;
	}

	/**
	 * idx번째 코어부터 4방향 연결 여부를 결정하며 백트래킹을 진행한다.
	 * 연결된 코어 수를 최대화하는 것을 우선.
	 * 코어 수가 같을 경우 전선 길이 합의 최솟값을 구한다.
	 *
	 * @param coreY 코어의 y좌표 배열
	 * @param coreX 코어의 x좌표 배열
	 * @param segY1 선분의 y좌표 시작점 배열
	 * @param segX1 선분의 x좌표 시작점 배열
	 * @param segY2 선분의 y좌표 종료점 배열
	 * @param segX2 선분의 x좌표 종료점 배열
	 * @param best [0]: 현재까지의 최대 연결 코어 수 [1]: 그때의 최소 전선 길이 합
	 * @param N 격자 크기
	 * @param coreCount 코어 개수
	 * @param segCount 선분 개수
	 * @param idx 현재 탐색 중인 코어 인덱스
	 * @param count 현재까지 연결된 코어 수
	 * @param length 현재까지 연결된 전선 길이 합
	 */
	void dfs(
		int[] coreY, int[] coreX,
		int[] segY1, int[] segX1, int[] segY2, int[] segX2,
		int[] best,
		int N, int coreCount, int segCount, int idx, int count, int length
	) {
		// Pruning:
		// 남은 코어를 모두 연결해도 현재 최대 연결 수에 미치지 못하면 탐색 중단
		int limit = count + (coreCount - idx);
		if (limit < best[0]) return;
		if (limit == best[0] && length >= best[1]) return;

		if (idx == coreCount) {
			if (count > best[0]
					|| (count == best[0] && length < best[1])) {
				best[0] = count;
				best[1] = length;
			}
			return;
		}

		int y = coreY[idx], x = coreX[idx];
		for (int i=0; i<4; ++i) {
			// 전선의 종료점: 해당 방향의 그리드 경계 좌표
			int ey = y + DY[i] * (DY[i] < 0 ? y : N-1-y);
			int ex = x + DX[i] * (DX[i] < 0 ? x : N-1-x);
			// 전선의 시작점: 시작점의 코어와 겹치지 않도록 코어 바로 인접한 칸부터 시작
			int sy = y + DY[i], sx = x + DX[i];
			int y1 = Math.min(sy, ey), y2 = Math.max(sy, ey);
			int x1 = Math.min(sx, ex), x2 = Math.max(sx, ex);
			if (intersects(segY1, segX1, segY2, segX2,
					segCount, y1, x1, y2, x2))
				continue;
			segY1[segCount] = y1;
			segX1[segCount] = x1;
			segY2[segCount] = y2;
			segX2[segCount] = x2;
			dfs(coreY, coreX, segY1, segX1, segY2, segX2, best,
					N, coreCount, segCount+1, idx+1, count+1,
					length + Math.abs(ey-y) + Math.abs(ex-x));
		}

		// 현재 코어를 연결하지 않고 다음 코어로 진행
		// 뻗었을때 막히는 경우만으로 충분하지 않고, 
		// 뻗으면 정상적으로 연결 가능한데도 연결하지 않는 경우도 고려해야 함
		dfs(coreY, coreX, segY1, segX1, segY2, segX2, best,
				N, coreCount, segCount, idx+1, count, length);
	}

	String solveInner(BufferedReader br) throws IOException {
		int N = Integer.parseInt(br.readLine().trim());
		int[] coreY = new int[12];
		int[] coreX = new int[12];
		int coreCount = 0;
		// 장애물 코어(<12)와 연결될 전선(<12)을 저장하기 위한 선분 배열
		int[] segY1 = new int[24];
		int[] segX1 = new int[24];
		int[] segY2 = new int[24];
		int[] segX2 = new int[24];
		int segCount = 0;
		for (int y=0; y<N; ++y) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int x=0; x<N; ++x) {
				if (st.nextToken().charAt(0) == '0')
					continue;
				// 코어를 길이가 0인 선분(점)으로 등록하여 전선 통과 방지
				segY1[segCount] = y;
				segY2[segCount] = y;
				segX1[segCount] = x;
				segX2[segCount] = x;
				++segCount;
				if (isOnEdge(N, y, x))
					// 가장자리에 위치한 코어는 이미 전원과 연결되어 있으므로
					// 탐색 대상에서 제외
					continue; 
				coreY[coreCount] = y;
				coreX[coreCount] = x;
				++coreCount;
			}
		}

		int[] best = new int[] { -1, 0 };
		dfs(coreY, coreX, segY1, segX1, segY2, segX2, best,
				N, coreCount, segCount, 0, 0, 0);
		return Integer.toString(best[1]);
	}

	void solve() throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		int T;
		T = Integer.parseInt(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++)
			sb.append('#').append(test_case)
					.append(' ').append(solveInner(br))
					.append('\n');
		System.out.print(sb);
	}

	public static void main(String args[]) throws Exception {
		new Solution().solve();
	}
}
