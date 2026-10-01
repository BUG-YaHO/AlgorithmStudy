import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {

	/*
	[ 문제 해설 ]
	전차가 바라보고 있는 방향으로 포탄을 발사하면
		- 벽돌벽에는 충돌(벽돌벽 파괴)
		- 강철벽에는 충돌(그대로)
		- 그 외에는 맵 밖으로 나갈 때까지 직진(그대로)
	초기 게임 맵의 상태와 사용자의 입력이 주어짐
	모든 입력을 처리하고 나면 게임 맵의 상태가 어떻게 될지 구해라

	[ 고려 사항 ]
	문자	의미
	.	평지(전차가 들어갈 수 있다.)
	*	벽돌로 만들어진 벽
	#	강철로 만들어진 벽
	-	물(전차는 들어갈 수 없다.)
	^	위쪽을 바라보는 전차(아래는 평지이다.)
	v	아래쪽을 바라보는 전차(아래는 평지이다.)
	<	왼쪽을 바라보는 전차(아래는 평지이다.)
	>	오른쪽을 바라보는 전차(아래는 평지이다.)

	문자	동작
	U	Up : 전차가 바라보는 방향을 위쪽으로 바꾸고, 한 칸 위의 칸이 평지라면 위 그 칸으로 이동한다.
	D	Down : 전차가 바라보는 방향을 아래쪽으로 바꾸고, 한 칸 아래의 칸이 평지라면 그 칸으로 이동한다.
	L	Left : 전차가 바라보는 방향을 왼쪽으로 바꾸고, 한 칸 왼쪽의 칸이 평지라면 그 칸으로 이동한다.
	R	Right : 전차가 바라보는 방향을 오른쪽으로 바꾸고, 한 칸 오른쪽의 칸이 평지라면 그 칸으로 이동한다.
	S	Shoot : 전차가 현재 바라보고 있는 방향으로 포탄을 발사한다.

	높이너비 H,W는 2~20
	사용자가 넣을 입력의 개수 N (0~100)

	[ 입력 ]
	첫 번째 줄에 테스트케이스의 수 T
	첫 번째 줄 - H(높이), W(너비)
	이후 H개의 줄에 W길이의 문자열
	다음 줄 - 사용자가 넣을 입력의 개수 N
	이후 사용자가 넣을 입력

  	[ 출력 ]
  	모든 입력을 처리하고 난 후의 게임 맵

	[ 풀이 ]
	static int[][] arr : H*W의 배열
	static boolean[][] isVisited
	static int nowR, nowC : 현재 전차의 위치
	static int[] commandArr

	static void move() : 전차를 움직이는 함수
		- U D L R의 경우 현재 전차의 방향을 바꿔주고 방향 이동함(평지라면)
		- dr, dc 활용

	static void shoot() : 포탄을 발사하는 함수
		- 벽돌 벽 부숨
		- 강철 벽 무시

	main에서 commandArr 돌면서 배열 수정

	 */

	static int H;
	static int W;
	static char[][] arr;
	static int nowR;
	static int nowC;
	static int N;
	static char[] commandArr;

	// ^, >, v, < 순서
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};

	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			arr = new char[H][W];

			for (int i = 0; i < H; i++) {
				String line = br.readLine();
				for (int j = 0; j < W; j++) {
					arr[i][j] = line.charAt(j);
					if (arr[i][j] == '<' || arr[i][j] == '>' || arr[i][j] == 'v' || arr[i][j] == '^') {
						nowR = i;
						nowC = j;
					}
				}
			}

			N = Integer.parseInt(br.readLine());
			commandArr = br.readLine().toCharArray();

			for (int i = 0; i < N; i++) {
				if (commandArr[i] == 'S') {
					shoot();
				} else {
					move(commandArr[i]);
				}
			}

			sb.append("#").append(t).append(" ");
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					sb.append(arr[i][j]);
				}
				sb.append("\n");
			}
		}
		System.out.println(sb);
	}

	static void move(char command) {

		int nextDir = -1; // dr, dc 인덱스 설정

		if (command == 'U') {
			arr[nowR][nowC] = '^';
			nextDir = 0;
		} else if (command == 'R') {
			arr[nowR][nowC] = '>';
			nextDir = 1;
		} else if (command == 'D') {
			arr[nowR][nowC] = 'v';
			nextDir = 2;
		} else if (command == 'L') {
			arr[nowR][nowC] = '<';
			nextDir = 3;
		}

		int nextR = nowR + dr[nextDir];
		int nextC = nowC + dc[nextDir];

		if (nextR < 0 || nextR >= H || nextC < 0 || nextC >= W) {
			return;
		}

		// 다음 위치가 평지라서 이동 가능한 경우에만 수행
		if (arr[nextR][nextC] == '.') {
			arr[nextR][nextC] = arr[nowR][nowC];
			arr[nowR][nowC] = '.';
			nowR = nextR;
			nowC = nextC;
		}
	}
	static void shoot() {
		int nextDir = -1; // dr, dc 인덱스 설정

		if (arr[nowR][nowC] == '^') {
			nextDir = 0;
		} else if (arr[nowR][nowC] == '>') {
			nextDir = 1;
		} else if (arr[nowR][nowC] == 'v') {
			nextDir = 2;
		} else if (arr[nowR][nowC] == '<') {
			nextDir = 3;
		}

		int nextR = nowR + dr[nextDir];
		int nextC = nowC + dc[nextDir];

		while (true) {

			// 맵 밖으로 나가면 끝남
			if (nextR < 0 || nextR >= H || nextC < 0 || nextC >= W) {
				return;
			}
			// 강철 - 그냥 끝남
			if (arr[nextR][nextC] == '#') {
				return;
			}

			// 벽돌 - 부서지고 끝남
			if (arr[nextR][nextC] == '*') {
				arr[nextR][nextC] = '.';
				return;
			}

			nextR += dr[nextDir];
			nextC += dc[nextDir];

		}
	}

}
