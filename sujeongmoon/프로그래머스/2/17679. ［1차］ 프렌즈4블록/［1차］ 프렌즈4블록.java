class Solution {
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        
        /**
        [ 문제 해설 ]
        2*2 형태로 4개가 붙어있을 경우 사라지면서 점수를 얻음
        블록이 지워지면 위에 있는 블록이 아래로 떨어지면서 빈 공간을 채움
        
        [ 입력값 ]
        m: 판의 높이
        n: 판의 폭
        board: 보드
        
        [ 제약 조건 ]
        n,m은 2 <= 30
        블록을 나타내는 문자는 A-Z가 사용
        
        [ 푸는 방법 ]
        
        * 매 턴 arr을 돌면서 지워지는 블럭을 찾는다
        * isNowTurnDead 표시로 해당 블럭이 지워졌는지 아닌지 확인
        * isNowTurnDead가 체크되는 경우 isRemoved를 true로 설정(false가 되는 경우 더이상 지워질 블록이 없으므로 리턴)
        * isNowTurnDead를 다 표시했다면, 해당 배열을 돌면서 true인 경우를 체크해서 answer에 누적
        
        * 각 열마다 행을 맨 밑에서부터 올라가면서 탐색
        * 이번 턴에 지워지지 않았고, 빈칸도 아닌 블록만 아래부터 차례로 채움(투포인터)
        * 남은 위쪽은 모두 '0'으로 변경
        
        * 종료조건 - 한 번도 지워지지 않았을 때를 체크해서 종료
        
        */
        
        boolean[][] isReallyDead = new boolean[m][n];
        boolean isRemoved = true;
        char[][] charBoard = new char[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                charBoard[i][j] = board[i].charAt(j);
            }
        }
        
        
        while(isRemoved) {
            
            isRemoved = false;
            boolean[][] isNowTurnDead = new boolean[m][n];
            
            // 맵을 돌면서 죽은 말이 있는지 체크
            for (int i = 0; i < m-1; i++) {
                for (int j = 0; j < n-1; j++) {
                 
                    char now = charBoard[i][j];
                    
                    if (now == '0') {
                        // isNowTurnDead[i][j] = true;
                        continue; // 이미 죽어있는 말이라면 건너뛰기
                    }
                        
                    if (!((now == charBoard[i][j+1]) && (now == charBoard[i+1][j]) && (now == charBoard[i+1][j+1]))) {
                        // 말이 같지 않다면 건너뛰기
                        continue;
                    }
                    
                    // 지워질 수 있는 경우
                    isNowTurnDead[i][j] = true;
                    isNowTurnDead[i+1][j] = true;
                    isNowTurnDead[i][j+1] = true;
                    isNowTurnDead[i+1][j+1] = true;  
                    
                    isRemoved = true; // 이번 턴에 말이 지워진 경우 체크
                }
            }
            
            // answer 값 세주기
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (isNowTurnDead[i][j]) {
                        answer++;
                    }
                }
            }
            
            // board 값 옮기기
            // 밑에서부터 위로 돌기
            for (int j = 0; j < n; j++) {
                int idx = m-1; // 살아있는 블록이 채워질 아래쪽 위치(투포인터)
                
                for (int i = m-1; i >= 0; i--) {
                    // 살아남은 블록만 아래로 남기기
                    if (!isNowTurnDead[i][j] && charBoard[i][j] != '0') {
                        charBoard[idx--][j] = charBoard[i][j];
                    }
                }
                
                // 블록 다 당기고 남은 위쪽 빈칸들은 모두 '0'
                while (idx >= 0) {
                    charBoard[idx--][j] = '0';
                    
                }
            }

        }
        
        return answer;
    }
}