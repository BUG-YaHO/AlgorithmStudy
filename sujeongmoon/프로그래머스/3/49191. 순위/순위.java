import java.util.*;

class Solution {
    
    /*
    [ 문제 설명 ]
    n명의 권투선수가 1번부터 n번까지 번호를 받음
    권투 경기는 1대1 방식으로 진행, 실력에 따라서 항상 이김
    심판은 주어진 경기 결과를 가지고 선수들의 순위를 매기려 함, 하지만 몇몇 경기 결과를 분실하여 정확하게 순위를 매길 수 없음
    
    [ 출력 ]
    정확하게 순위를 매길 수 있는 선수의 수 answer를 리턴
    
    [ 입력 ]
    n: 선수의 수
    results: 경기 결과를 담은 2차원 배열
    
    [ 제한 사항 ]
    선수의 수 1 <= n <= 100
    경기결과 results.length는 1 <= r.l <= 4500
    results[i] 의 [A, B] : A가 B 선수를 이겼다
    
    [ 풀이 방법 ]
     List<Integer>[] winList : 각 인덱스가 플레이어. 리스트 안에는 해당 플레이어가 이긴 플레이어들의 번호
     List<Integer>[] loseList : 각 인덱스가 플레이어. 리스트 안에는 해당 플레이어가 패배한 플레이어들의 번호
     
     results 배열을 돌면서 각각의 리스트들을 다 채운 다음, n만큼의 플레이어들을 돌면서 upDfs, downDfs를 진행한다
     upDfs와 downDfs를 돌면서 isVisited를 추가해준다
     for문을 돌면서 isVisited가 달성되지 않은 경우 순위를 매길 수 없는 것
     isVisited가 전부 채워져있으면 answer++
     
    */
    
    
    public int solution(int n, int[][] results) {
        int answer = 0; // 정확하게 순위를 매길 수 있는 선수의 수
        
        List<Integer>[] winList = new ArrayList[n+1];
        List<Integer>[] loseList = new ArrayList[n+1];
        
        // 리스트 초기화
        for (int i = 1; i <= n; i++) {
            winList[i] = new ArrayList<>();
            loseList[i] = new ArrayList<>();
        }
        
        // 리스트 채우기
        for (int i = 0; i < results.length; i++) {
            int[] now = results[i];
            winList[now[0]].add(now[1]);
            loseList[now[1]].add(now[0]);
        }
        
        // 각 노드별 dfs 돌기
        for (int i = 1; i <= n; i++) {
            boolean[] isVisited = new boolean[n+1];
            
            dfs(winList, isVisited, i, 0);
            isVisited[i] = false; // 패배한 경우의 dfs도 돌아야 하므로 현재 노드의 방문배열 false 처리
            dfs(loseList, isVisited, i, 0);
            
            boolean isRanked = true;
            for (int j = 1; j <= n; j++) {
                if (!isVisited[j]) {
                    isRanked = false;
                    break;
                }
            }
            if (isRanked) {
                answer++;
            }
        }
        
        return answer;
    }
    
    static void dfs(List<Integer>[] listArr, boolean[] isVisited, int now, int count) {
        
        if (isVisited[now]) {
            return;
        }
        
        isVisited[now] = true;
        
        for (int next : listArr[now]) {
            dfs(listArr, isVisited, next, count+1);
        }
    }
}