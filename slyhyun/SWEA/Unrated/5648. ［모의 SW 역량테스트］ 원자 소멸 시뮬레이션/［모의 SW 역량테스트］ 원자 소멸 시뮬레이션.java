import java.io.*;
import java.util.*;

public class Solution {
	static class Atom {
		int c, r, d, k;
		boolean isDead;

		public Atom(int c, int r, int d, int k) {
			this.c = c * 2 + 2000;
			this.r = r * 2 + 2000;
			this.d = d;
			this.k = k;
			this.isDead = false;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int[] dr = {1, -1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		
		int[][] map = new int[4001][4001];

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			
			List<Atom> atoms = new ArrayList<>();

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				
				int c = Integer.parseInt(st.nextToken());
				int r = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				int k = Integer.parseInt(st.nextToken());
				
				atoms.add(new Atom(c, r, d, k));
			}

			int energy = 0;
			int alive = N;

			for (int step = 0; step <= 4000; step++) {
				if (alive <= 1) break;

				List<Atom> next = new ArrayList<>();

				for (Atom atom : atoms) {
					if (atom.isDead) continue;

					int nr = atom.r + dr[atom.d];
					int nc = atom.c + dc[atom.d];

					if (nr < 0 || nr > 4000 || nc < 0 || nc > 4000) {
						atom.isDead = true;
						alive--;
						continue;
					}

					atom.r = nr;
					atom.c = nc;
					
					map[nr][nc] += 1;
					
					next.add(atom);
				}

				for (Atom atom : next) {
					if (map[atom.r][atom.c] > 0) {
						if (map[atom.r][atom.c] >= 2) {
							energy += atom.k;
							atom.isDead = true;
							alive--;
						}
					}
				}

				for (Atom atom : next) {
					map[atom.r][atom.c] = 0;
				}
			}

			sb.append("#").append(tc).append(" ").append(energy).append("\n");
		}
		
		System.out.print(sb);
	}
}
