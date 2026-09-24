
/**
 * [문제 해석]
 * 판의 높이 m, 폭 n인 board가 있을 때, 같은 모양의 카카오프렌즈 블록이 2x2 형태로 4개가 붙어있을 경우 사라지면서 점수를 얻는다.
 * 같은 블록은 여러 2x2에 포함될 수 있고, 지워지는 조건에 만족하는 2x2 모양이 여러 개 있다면 한꺼번에 지워진다.
 * 블록이 지워진 후에는 위에 있는 블록이 아래로 떨어져 빈 공간을 채우게 된다.
 * 빈 공간을 채운 후에 다시 2x2 형태로 같은 모양의 블록이 모이면 다시 지워지고 떨어지고 반복
 * -> 지워지는 블록 총 몇 개인지 판단
 * 
 * [풀이]
 * 1. 전체 맵 탐색이 아닌 2x2 영역 탐색
 * 	- 바로 삭제가 아닌 배열에 삭제할 위치 표시
 * 2. 배열에 표시된 블록들 한꺼번에 삭제
 * 	-> boolean 배열 사용해서 한 번만 카운트
 * 3. 열을 아래에서부터 확인하면서 남아 있는 블록 이동
 *
 */

class Solution {
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        
        char[][] arr = new char[m][n];

        for (int r=0; r<m; r++) {
            arr[r] = board[r].toCharArray();
        }
        
        while(true) {
        	boolean[][] remove = new boolean[m][n];
        	
        	// 삭제할 블록 찾기
        	for(int r=0; r<m-1; r++) {
        		for(int c=0; c<n-1; c++) {
        			
        			char block = arr[r][c];
        			
        			if(block == ' ') {
        				continue;
        			}
        			
        			// 같은 블록인지 확인
        			if(block==arr[r][c+1] && block==arr[r+1][c] && block==arr[r+1][c+1]) {
        				remove[r][c] = true;
        				remove[r][c+1] = true;
        				remove[r+1][c] = true;
        				remove[r+1][c+1] = true;
        			}
        		}
        	}
        	
        	
        	// 표시된 블록 삭제
        	
        	int count = 0;
        	
        	for(int r=0; r<m; r++) {
        		for(int c=0; c<n; c++) {
        			if(remove[r][c]) {
        				arr[r][c] = ' ';
        				count++;
        			}
        		}
        	}
        	
        	if(count == 0) {
        		break;
        	}
        	
        	answer += count;
        	
        	// 블록 떨어뜨리기 -> 열 기준
        	for(int c=0; c<n; c++) {
        		
        		int lastIndex = m-1;
        		
        		for(int r=m-1; r>=0; r--) {
        			
        			if(arr[r][c] != ' ') {
        				arr[lastIndex][c] = arr[r][c];
        				lastIndex--;
        			}
        		}
        		
            	while(lastIndex >= 0) {
            		arr[lastIndex][c] = ' ';
            		lastIndex--;
            	}
            	
        	}

        }
        
        return answer;
    }
}