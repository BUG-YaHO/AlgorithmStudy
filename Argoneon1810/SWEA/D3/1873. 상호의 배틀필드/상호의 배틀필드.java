import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 입력:
 * 그리드 H, W (2 ≤ H, W ≤ 20)
 * 명령 N (0 < N ≤ 100)
 * 
 * 그리드의 한 셀 및 그리드의 한 글자는 반드시 하나의 캐릭터
 * 
 * 이전 구현에서
 * 전차와 그리드를 분리하고, 객체로 묶고,
 * 타일 타입을 순차적으로 판단하도록 만든 조치가
 * 오히려 코드를 복잡하게 만들었음
 * 
 * 대포 쏘는건 그때그때 루프로 처리하고
 * 탱크가 이동할 수 있는지도 그때그때 처리하도록 구현
 * 탱크 위치 좌표만 기억하고, 탱크를 떼어내지도 않음
 * 
 * up right down left
 * 0  1     2    3
 * 으로 구현했으므로
 * 오른쪽으로 돌고 싶으면 (dir+1)%4
 * 왼쪽으로 돌고 싶으면 (dir+3)%4 하면 됨
 */
public class Solution {
	static final int R = 0;
	static final int C = 1;

	static final char GROUND = '.';
	static final char WOOD_WALL = '*';
	static final char METAL_WALL = '#';

	public static void main(String...args) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; ++tc) {
			sb
				.append('#')
				.append(tc)
				.append(' ')
				.append(solve(br))
				.append('\n');
		}
		System.out.print(sb.toString());
	}

	static String solve(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 그리드 받으면서 탱크 현재 상태까지 특정
		int H, W;
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		char[][] grid = new char[H][W];
		Tank tank = new Tank();
		tank.r = -1; tank.c = -1; tank.facing = null;
		for(int r=H-1; r>=0; --r) {
			grid[r] = br.readLine().toCharArray();
			for(int c=0; c<W; ++c) {
				// 탱크 위치/방향 특정용
				switch(grid[r][c]) {
					case '<':
						tank.r = r; tank.c = c;
						tank.facing = Facing.LEFT;
						break;
					case 'v':
						tank.r = r; tank.c = c;
						tank.facing = Facing.DOWN;
						break;
					case '>':
						tank.r = r; tank.c = c;
						tank.facing = Facing.RIGHT;
						break;
					case '^':
						tank.r = r; tank.c = c;
						tank.facing = Facing.UP;
						break;
				}
			}
		}
		// 명령 수신
		// 명령 갯수 버리기
		br.readLine();
		char[] commands = br.readLine().toCharArray();
		// 명령대로 이동
		for(char command : commands) {
			switch(command) {
				case 'U':
				case 'D':
				case 'L':
				case 'R':
					tryMove(grid, tank, command);
					break;
				case 'S':
					shoot(grid, tank);
					break;
				default:
					System.out.println("You should not be seeing this");
					break;
			}
		}
		return printGrid(grid, H, W);
	}

	static String printGrid(char[][] grid, int H, int W) {
		StringBuilder sb = new StringBuilder();
		for(int r=H-1; r>=0; --r) {
			if(r<H-1) sb.append('\n');
			for(int c=0; c<W; ++c) {
				sb.append(grid[r][c]);
			}
		}
		return sb.toString();
	}

	static void tryMove(
		char[][] grid, 
		Tank tank, 
		char command
	) {
		// 명령 -> 실제 방향 변환
		// 탱크의 바라보는 방향은 반드시 업데이트되므로 즉시 업데이트
		tank.facing = Facing.toFacing(command);
		// 현재 탱크 위치에서 해당 방향으로 이동 시도
		int[] delta = tank.facing.toDT();
		int nextR = tank.r + delta[R];
		int nextC = tank.c + delta[C];
		if(isOffgrid(grid, nextR, nextC)) {
			// 그리드 밖을 향하는 요청이라면
			// 제자리에서 회전만 하고 종료
			grid[tank.r][tank.c] = tank.facing.toTank();
			return;
		}
		if(grid[nextR][nextC] != GROUND) {
			// 그리드 안쪽 요청이지만, 이동 불가한 타일로 향하는 요청이므로
			// 제자리에서 회전만 하고 종료
			grid[tank.r][tank.c] = tank.facing.toTank();
			return;
		}
		// 이동이 되었다는 말
		// 이전 위치는 빈칸으로 바꾸고
		grid[tank.r][tank.c] = GROUND;
		// 새 위치는 지정된 방향을 바라보는 탱크로
		grid[nextR][nextC] = tank.facing.toTank();
		tank.r = nextR;
		tank.c = nextC;
	}

	static void shoot(char[][] grid, Tank tank) {
		boolean hit = false;
		int[] dt = tank.facing.toDT();
		int toLookR = tank.r;
		int toLookC = tank.c;
		while(!hit) {
			// 코드 일관성을 위해 일단 다음 칸을 보고 시작
			toLookR += dt[R];
			toLookC += dt[C];
			// 그리드 밖이면 종료
			if(isOffgrid(grid, toLookR, toLookC)) break;
			// 현재 칸이 피격판정이 있는 칸인가
			boolean hittable = false;
			switch(grid[toLookR][toLookC]) {
				case WOOD_WALL:
				case METAL_WALL:
					hittable = true;
			}
			// 피격판정이 없는 칸이면 다음으로
			if(!hittable) continue;
			// 피격판정이 있는 칸이면 때림
			tryBreak(grid, toLookR, toLookC);
			// 때렸으므로 상태 갱신
			hit = true;
		}
	}

	static boolean isOffgrid(char[][] grid, int r, int c) {
		int H = grid.length;
		int W = grid[0].length;
		if (r < 0 || r >= H) return true;
		if (c < 0 || c >= W) return true;
		return false;
	}

	static void tryBreak(char[][] grid, int r, int c) {
		switch(grid[r][c]) {
			case WOOD_WALL:
				grid[r][c] = GROUND;
		}
	}

	static class Tank {
		int r, c;
		Facing facing;
	}

	static enum Facing {
		UP(0), RIGHT(1), DOWN(2), LEFT(3);

		private final int value;
		final int[][] DT = {
			{1, 0},
			{0, 1}, 
			{-1, 0}, 
			{0, -1}, 
		};
		final char[] tanks = { '^', '>', 'v', '<' };

		Facing(int value) { this.value = value; }

		public static Facing toFacing(char command) throws IllegalArgumentException {
			switch(command) {
				default:
					throw new IllegalArgumentException();
				case 'U':
					return Facing.UP;
				case 'D':
					return Facing.DOWN;
				case 'L':
					return Facing.LEFT;
				case 'R':
					return Facing.RIGHT;
			}
		}

		public int[] toDT() {
			return DT[value];
		}

		public char toTank() { return tanks[value]; }
	}
}
