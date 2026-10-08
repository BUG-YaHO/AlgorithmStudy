import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
/**
 * 1. 어떤 노드는 자신의 모든 자녀 노드에게 동시에 전화한다
 * 2. 이전 턴에 부모 노드에게 전화를 받은 모든 노드는
 *    동시에 다시 자기 자녀에게 전화를 돌린다
 * 3. 그래프는 반드시 연결되어 있는 것이 아니다
 * 4. 그래프에는 순환이 있을 수 있다.
 * 
 * - 전화를 이미 한 노드는 다시 전화하지 않아야 하므로 방문배열 필요
 * - 순환이 있으므로 위상정렬 불가
 * - 동시에 연락하므로 bfs
 * - 이미 방문한 노드를 재방문했을때 연결하지 않는 것 = 신장 트리 특성
 * - 모든 노드의 가중치가 1로 동일하므로 간선 가중치 오름차순 정렬 불가
 *   = 최소 신장 트리 불가
 * 
 * 접근법 1
 * - '마지막으로 방문된 노드'가 '내가 마지막이다' 만 알 수 있으면
 *   depth가 몇부터 시작하는지 상관 없으므로
 *   재방문 체크를 위한 방문 배열을 int로 하고
 *   첫 노드 진입시 depth를 1로 설정
 * - 새 노드를 방문할 때 마다 [깊이][최대노드수] 배열에
 *   해당 깊이의 모든 노드를 순서대로 쌓은 후,
 *   bfs 큐가 비면 최대 도달 깊이의 모든 노드 중 인덱스가 가장 큰 노드를 반환
 * 
 * 접근법 2
 * - 단순 불린 방문 배열을 두고 신장트리 형성을 마침
 * - 트리가 형성된 다음 다음과 같이 방문
 *   - 리프 노드의 랭크가 0인 표준적인 트리에서 부모의 랭크가 4라 가정
 *   - 첫 노드 기준 랭크가 3보다 작은 자녀는 방문하지 않음
 *   - 프루닝 하면서 진입하면서, 마지막에 랭크가 0인 자녀들만
 *     maxIndex 계산에 사용
 */
public class Solution {
    static final int MAX_VERTICES = 100;
    static final PrimitiveQueue pq;
    static {
        // 방문체크 할 것이므로 큐에는 최대 전체 노드까지만 들어감
        pq = new PrimitiveQueue(MAX_VERTICES);
    }
    public static void main(String...args) throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );
        // StringTokenizer st = new StringTokenizer(br.readLine());
        // int T = Integer.parseInt(st.nextToken());
        int T = 10;
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
    /**
     * 연락 인원 최대 100명
     * 입력은 순서대로:
     * numEdges startIdx
     * caller callee caller callee caller callee...
     */
    static String solve(BufferedReader br)  throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int numEdges = Integer.parseInt(st.nextToken());
        int startIdx1based = Integer.parseInt(st.nextToken());
        int[][] edges = new int[MAX_VERTICES+1][MAX_VERTICES];
        int[] tails = new int[MAX_VERTICES+1];
        st = new StringTokenizer(br.readLine());
        while(st.hasMoreTokens()) {
            int caller1based = Integer.parseInt(st.nextToken());
            int callee1based = Integer.parseInt(st.nextToken());
            edges[caller1based][tails[caller1based]++] = callee1based;
        }
        boolean[] visited = new boolean[MAX_VERTICES+1]; // 각 노드의 방문체크
        pq.enqueue(startIdx1based);
        visited[startIdx1based] = true;
        int[] maxIdxInRank = new int[MAX_VERTICES]; // 모든 노드가 1열로 연결된 최악의 경우
        int depth = 0; // 각 노드의 심도
        maxIdxInRank[depth++] = startIdx1based;
        while(pq.size() > 0) {
            int startedWithQueueSize = pq.size();
            for(int i=0; i<startedWithQueueSize; ++i) {
                int currentNode = pq.dequeue();
                for(int childIdx=0; childIdx < tails[currentNode]; ++childIdx) {
                    int nextNodeCandidate = edges[currentNode][childIdx];
                    if (visited[nextNodeCandidate]) continue;
                    visited[nextNodeCandidate] = true;
                    maxIdxInRank[depth]
                        = maxIdxInRank[depth]!=0
                            ? Integer.max(
                                    maxIdxInRank[depth], 
                                    nextNodeCandidate
                                )
                            : nextNodeCandidate;
                    pq.enqueue(nextNodeCandidate);
                }
            }
            ++depth;
        }
        // 이 시점에서 pq는 항상 비어 있으므로 softReset는 해주지 않아도 됨
        // 하지만 head, tail을 0으로 정렬해주는건 유리하므로 수행
        pq.softReset();
        // 최종 도달 심도에 등록된 값 = 목표값
        return Integer.toString(maxIdxInRank[depth-2]);
    }
    static class PrimitiveQueue {
        private int[] data;
        private int head, tail;
        PrimitiveQueue() { this(8); }
        PrimitiveQueue(int capacity) {
            data = new int[capacity];
            head = tail = 0;
        }
        private void softReset() { head = tail = 0; }
        private void resizeRouter() {
            if(size() < data.length * 0.5f) compact();
            else resize();
        }
        private void compact() {
            System.arraycopy(data, head, data, 0, size());
            tail = size(); head = 0;
        }
        private void resize() {
            int[] old = data;
            data = new int[(int)(data.length*1.4)];
            System.arraycopy(old, head, data, 0, size());
            tail = size(); head = 0;
        }
        int size() { return tail-head; }
        void enqueue(int v) {
            if(tail >= data.length) resizeRouter();
            data[tail++] = v;
        }
        int dequeue() { return data[head++]; }
        int peek() { return data[head]; }
    }
}