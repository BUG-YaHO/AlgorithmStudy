import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static final int[][] GRID;
	static final int GRID_SIZE;
	static final int SHIFT_UP;
	
	static final int X, Y, DIR, ENERGY;

	static final int[][] DT; // y x
	

	static {
		GRID_SIZE = 1000*2*2+1;
		// 정상 그리드 사이즈 [-1000, 1000]
		// 소수점 충돌 대비용 스케일업 [-2000, 2000]
		// (모든 원소의 속도가 1이므로 소수점에서 충돌해도 반드시 0.5에서만 충돌)
		// 시프트업 [0, 4000]
		// 한 사이클이 다 돌면 모든 칸이 0이게끔 설계되어 있으므로 재사용 가능
		GRID = new int[GRID_SIZE][GRID_SIZE];
		SHIFT_UP = 2000;
		Y = 0;
		X = 1;
		DIR = 2;
		ENERGY = 3;
		DT = new int[][] {
			{1, 0}, // 0: 상
			{-1, 0}, // 1: 하
			{0, -1}, // 2: 좌 
			{0, 1}, // 3: 우
		};
	}

	boolean isOffgrid(int x, int y) {
		return x < 0 || y < 0 || x >= GRID_SIZE || y >= GRID_SIZE;
	}

	void preconditionHistogram(int x, int y) {
		if(GRID[y][x] > 1)
			GRID[y][x]*=-1;
	}

	boolean shouldPop(int x, int y) {
		return GRID[y][x]<0;
	}

	int pop(int[] currentAtom) {
		int toReturn = currentAtom[ENERGY];
		currentAtom[ENERGY] = 0;
		return toReturn;
	}

	// 없어야 동작한다.
	// /**
	//  * 원자를 그리드에 최초 배치한다
	//  */
	// void init(int[][] atom, int N) {
	// 	for(int i=0; i<N; ++i) {
	// 		// 처음부터 겹치는 경우는 없다고 했음
	// 		GRID[atom[i][Y]][atom[i][X]] = 1;
	// 	}
	// }

	/**
	 * 모든 원자를 반 틱 움직인다
	 * @params grid 맵
	 * @params atom 원자
	 * @params N 원자의 수
	 * @returns 이번에 그리드 바깥으로 나간 원자
	 */
	int tick(int[][] atom, int N) {
		int gridOut = 0;
		for (int i=0; i<N; ++i) {
			int[] current = atom[i];
			if(current[ENERGY]==0)
				continue; // 이번 턴 이전에 이미 죽거나 나간 원자
			// 여기 들어왔으면 일단 원자는 살아있음
			int nx = current[X]+DT[current[DIR]][X];
			int ny = current[Y]+DT[current[DIR]][Y];
			if(isOffgrid(nx, ny)) {
				// 이번 턴에 그리드 밖으로 나감
				// 1. 원자는 터뜨리고, 반환된 에너지를 그냥 버림
				pop(current);
				// 2. 그리드에서 나간 원자 개수 갱신
				++gridOut;
				// 3. 그리드 밖으로 나가기 전에 히스토그램 청소
				GRID[current[Y]][current[X]] = 0;
				continue;
			}
			// 여기 들어왔으면 유효한 이동
			// 그리드 히스토그램 채우기
			current[X] = nx;
			current[Y] = ny;
			++GRID[current[Y]][current[X]];
		}
		return gridOut;
	}

	/**
	 * 터져야 하는 셀이면 터진다
	 * @params grid 맵
	 * @params atom 원자
	 * @params releasedEnergy 방출된 에너지
	 * @params N 원자의 수
	 * @returns 이번에 터진 원자
	 */
	int checkRelease(int[][] atom, int[] releasedEnergy, int N) {
		int popped = 0;
		int releasedEnergyCheckpoint = releasedEnergy[0];
		for (int i=0; i<N; ++i) {
			int[] current = atom[i];
			if(current[ENERGY]==0)
				continue; // 이번 턴 이전에 이미 죽거나 나간 원자
			// 판정의 편의를 위해 한 셀에 두개 이상의 원소가 있을 때
			// 히스토그램의 값을 음수로 반전
			preconditionHistogram(current[X], current[Y]);
			if(shouldPop(current[X], current[Y])) {
				// 이번 턴에 충돌 발생
				// 1. 원자를 터뜨리고, 반환된 에너지는 저장
				releasedEnergyCheckpoint+=pop(current);
				// 2. 터진 원자 개수 갱신
				++popped;
				// 3. 음수인 히스토그램을 업데이트
				++GRID[current[Y]][current[X]];
			}
		}
		releasedEnergy[0] = releasedEnergyCheckpoint;
		return popped;
	}

	/**
	 * 히스토그램 리셋
	 */
	void prepareNextTick(int[][] atom, int N) {
		for(int i=0; i<N; ++i) {
			int[] current = atom[i];
			// 그리드 밖인 원소
			// - 어차피 그리드 안에 존재하지 않음
			// - 나가기 전에 스스로 청소하고 갔음
			// 터진 원소
			// - 터지는 과정에서 히스토그램이 이미 청소 되어 있음
			// 둘 다 에너지가 0인 것으로 판정 가능
			if(current[ENERGY]==0)
				continue;
			GRID[atom[i][Y]][atom[i][X]] = 0;
		}
	}

	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 원자 수. [1,1000]
		// 0: y좌표, 1: x좌표, 2: 운동방향, 3: 에너지량
		int[][] atom = new int[N][4];
		for(int n=0; n<N; ++n) {
			st = new StringTokenizer(br.readLine());
			atom[n][X] = Integer.parseInt(st.nextToken()) * 2 + SHIFT_UP;
			atom[n][Y] = Integer.parseInt(st.nextToken()) * 2 + SHIFT_UP;
			atom[n][DIR] = Integer.parseInt(st.nextToken());
			atom[n][ENERGY] = Integer.parseInt(st.nextToken());
		}
		int[] releasedEnergy = new int[1];
		int releasedOrDeadAtomCnt = 0;
		// init(grid, atom, N);
		while(releasedOrDeadAtomCnt < N) {
			releasedOrDeadAtomCnt += tick(atom, N);
			releasedOrDeadAtomCnt += checkRelease(atom, releasedEnergy, N);
			prepareNextTick(atom, N);
		}
		return Integer.toString(releasedEnergy[0]);
	}

	void solve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for (int tc=1; tc<=T; tc++) {
			sb.append('#')
				.append(tc)
				.append(' ')
				.append(solveInner(br))
				.append('\n');
		}
		System.out.print(sb.toString());
	}
	public static void main(String...args) throws IOException {
		new Solution().solve();
	}
}