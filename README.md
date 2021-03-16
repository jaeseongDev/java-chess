# java-chess

체스 게임 구현을 위한 저장소

## TDD DOMAIN

- [ ] Piece (체스말)
    - [ ] Name : "Q", "q", ....
    - [ ] Position : N, M 의 위치
    - [ ] alive : 죽었는지 살았는지 true/false
    - [ ] move()
    - [ ] isMovable()
      
      - [ ] Empty
        - [ ] 빈 칸
      - [ ] King.java
        - [ ] 상하좌우, 대각선 방향으로 각각 1칸씩만 움직일 수 있다. 1 1경기마다 각 선수는 단 1번 "캐슬링"이라는 특별 행마를 할 수 있다.
      - [ ] Rook
        - [ ] 상하좌우 방향으로 기물이 없는 칸에 한해서 칸수의 제한 없이 움직일 수 있다. 룩은 캐슬링을 할때 따라 움직인다.
      - [ ] Bishop
        - [ ] 대각선 방향으로 기물이 없는 칸에 한해서 칸수의 제한 없이 움직일 수 있다.
      - [ ] Queen
        - [ ] 상하좌우, 대각선 방향으로 기물이 없는 칸에 한해서 칸수의 제한 없이 움직일 수 있다.
      - [ ] Knight
        - [ ] 현재 위치한 칸으로부터 같은 랭크, 파일, 대각에 위치하지 않은 칸들 중에서 현재 위치와 가장 가까운 칸으로 이동한다. (수직 방향으로 한칸 움직인 후 수평 방향으로 두칸 움직이거나 수직 방향으로 두칸 움직인 후 수평 방향으로 한칸 움직이는 것으로, 이는 L자 모양으로 움직인다고 생각하면 이해하기 쉽다.) 나이트는 유일하게 다른 기물을 넘어다닐 수 있다.
      - [ ] Pawn
        - [ ] 체스의 기물 중 가장 복잡한 행마법을 가지고 있다.
          폰은 행마법과 기물을 잡는 법이 다른 유일한 기물이다.
          바로 앞의 칸이 비어 있다면 앞으로 한 칸 전진할 수 있다.(바로 앞에 상대의 기물이 있어도 잡을 수 없다.)
          경기중 단 한번도 움직이지 않은 폰은 바로 앞의 두칸이 비어 있을 때 두칸 전진할 수 있다.(한칸만 전진해도 된다.) 폰은 대각선 방향으로 바로 앞에 위치한 기물을 잡을 수 있다.(대각선 방향으로 바로 앞에 위치한 칸이 비어 있더라도 그곳으로 전진할 수 없다.)
          폰은 앞쪽으로만 움직이며 절대 뒤쪽으로 행마하지 않는다.
          폰은 앙파상과 프로모션 두가지의 특별 행마법을 가진다.(Schiller 2003, 17–19쪽).
- [ ] Grid (체스 보드)
  - [ ] List<Line>: N * N 의 Piece들의 집합
  - [ ] getter
  - [ ] 왕 잡으면 종료

- [ ] Line (체스의 가로 한 줄)
  
- [ ] Player
  - [ ] Status: (말이 잡힐때마다 업데이트)
  - [ ] 승패여부 및 점수 관리(3단계 이후)