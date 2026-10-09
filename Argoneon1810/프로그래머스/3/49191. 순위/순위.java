import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    void walkUp(boolean[][] grid, int n, int from) {
        // 열벡터에서 true인 모든 것을 큐에 넣는다
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(from);
        boolean[] hist = new boolean[n];
        while(!q.isEmpty()) {
            int c = q.poll();
            for(int i=0; i<n; ++i) {
                if(grid[i][c]) {
                    if(hist[i]) continue;
                    hist[i] = true;
                    q.offer(i);
                }
            }
        }
        for(int i=0; i<n; ++i) {
            // 모순 없음 보장
            grid[i][from] = hist[i];
        }
    }
    void walkDown(boolean[][] grid, int n, int from) {
        // 행벡터에서 true인 모든 것을 큐에 넣는다
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(from);
        boolean[] hist = new boolean[n];
        while(!q.isEmpty()) {
            int c = q.poll();
            for(int i=0; i<n; ++i) {
                if(grid[c][i]) {
                    if(hist[i]) continue;
                    hist[i] = true;
                    q.offer(i);
                }
            }
        }
        for(int i=0; i<n; ++i) {
            // 모순 없음 보장
            grid[from][i] = hist[i];
        }
    }
    int countIntake(boolean[][] grid, int n, int from) {
        int count = 0;
        for(int i=0; i<n; ++i)
            if(grid[i][from])
                ++count;
        return count;
    }
    int countOutgoing(boolean[][] grid, int n, int from) {
        int count = 0;
        for(int i=0; i<n; ++i)
            if(grid[from][i])
                ++count;
        return count;
    }
    public int solution(int n, int[][] results) {
        int answer = 0;
        // 모든 승패 트리화
        // 인접행렬
        boolean[][] grid = new boolean[n][n];
        for (int i=0; i<results.length; ++i) {
            int w = results[i][0]-1;
            int l = results[i][1]-1;
            // 승자가 패자를 가리키게
            grid[w][l] = true;
        }
        for (int i=0; i<n; ++i) {
            // 각 노드가 자기 조상을 직접상속
            walkUp(grid, n, i);
            // 각 노드가 자기 자손을 직접상속
            walkDown(grid, n, i);
            // intake + outgoing + 1 = n이면 등수 특정
            int possibleRank = countIntake(grid, n, i) + 1;
            int outgoing = countOutgoing(grid, n, i);
            if(possibleRank + outgoing == n)
                ++answer;
        }
        return answer;
    }
}