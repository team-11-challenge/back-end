# 👨🏻‍🏫 01 | 프로젝트 소개 [교수님 티켓팅]
![교수님 티켓팅_배너-001](https://github.com/team-11-challenge/back-end/assets/62596783/ae296937-5d21-4c88-9eaf-d457ff4d7889)

```
🏫 20K 이상의 트래픽에도 안정적인 수강 신청 플랫폼

- 빠르고 정확한 강의 조회 서비스
- 원하는 강의를 신속하게 신청할 수 있는 장바구니 서비스
- 대용량 트래픽에도 안정적으로 동시성 제어를 하는 수강 신청 서비스
- DB 과부하와 UX를 고려한 접속자 대기열 서비스
```
- 원하는 강의를 조회해볼 수 있습니다.
- 원하는 강의를 미리 장바구니에 담아둘 수 있습니다.
- 장바구니에 담아둔 강의를 수강 신청과 취소를 할 수 있습니다.
- 장바구니에 담아두지 않은 경우, 강의 조회 후 수강 신청할 수 있습니다.
- 수강 신청을 취소할 수 있습니다.
- 신청 시 대기열 기능을 통해 나의 순서를 알 수 있습니다.

# 🛒 02 | 프로젝트 목표
1️⃣ **데이터 수집 및 전처리**

- 데이터 수집
    - 실제 대학의 허락을 받고 1만 건 이상의 강의 데이터 확보
    - 학생 및 코드 더미 데이터 생성
- 데이터 전처리
    - 강의 csv 파일에서 대학, 학과, 전공, 교과목, 교수 데이터 전처리
    - 불규칙한 시간표 데이터 전처리
        
        → [데이터 전처리](https://www.notion.so/bde77b4cb95d458d8588b88416f0ea2f?pvs=21) 
        

2️⃣ **검색 성능 개선을 통한 빠른 검색 및 정확도 높은 검색 제공**

- 검색에 대한 빠른 결과 제공
- 다양한 필터에 대한 정확한 검색 결과 제공
    
    → [강의 조회 쿼리 최적화](https://www.notion.so/880708d1b8d841018cebb6e84b964c71?pvs=21) 
    

3️⃣ **4000명 이상 접속자 동시성 제어**

- 비관적 락을 통한 동시성 제어
- 분산 서버 환경에서 스케줄 락을 통한 이벤트 중복 수행 방지
    
    → [동시성 제어](https://www.notion.so/1551d33e51b74d0ca0cf351a51ee6bcb?pvs=21) 
    

4️⃣ **20K 이상의 트래픽에도 안정적인 서비스 제공**

- 로드밸런싱 및 오토스케일링
- 접속자 대기열 기능을 통한 대기순번 및 예상시간 제공
    
    → [접속자 대기열](https://www.notion.so/9bc353fe0f734d79b181a26f2033de41?pvs=21)

# 03 | 아키텍쳐
## ⚙️ 서비스 아키텍쳐
![서비스아키텍처](https://github.com/team-11-challenge/back-end/assets/95194606/e6521883-872a-4a6e-ad0d-2b2595fa8f90)

## ERD
![erd](https://github.com/team-11-challenge/back-end/assets/95194606/84c97ed6-e24e-4570-bac0-23e291c6d314)

## API 명세서 (swagger)
[Swagger](https://professor-ticketing.store/swagger-ui/index.html#/)

# 04 | 성능 개선
- 강의 조회 쿼리 최적화
    
    DB 자체 최적화는 필요가 없었지만 쿼리 테스트 결과 시간표 조회와 강의 조회 시 N+1 문제가 발생하였습니다.
    
    N+1 문제 해결과 쿼리의 성능을 향상시키기 위해 쿼리 최적화를 진행하였습니다.
    
    이 중 실행 시간을 측정하여 성능 비교를 하였습니다.
    
    ### 성능 개선 결과
    
    각 조회 기능에서의 JPA로 구현 시, QueryDSL로 구현 시 각각 시간을 비교하는 10회의 반복 테스트를 시행하였습니다. 
    
    모든 시간 단위는 ms입니다.
    
    - 시간표 조회
        
        
        |  | JPA | QueryDSL |
        | --- | --- | --- |
        | 1회 |  24 | 110 |
        | 2회 | 13 | 15 |
        | 3회 | 16 | 14 |
        | 4회 | 14 | 14 |
        | 5회 | 13 | 23 |
        | 6회 | 15 | 14 |
        | 7회 | 14 | 15 |
        | 8회 | 15 | 15 |
        | 9회 | 20 | 12 |
        | 10회 | 14 | 15 |
    - 강의 조회
        - 구분으로 조회
            
            
            |  | JPA | QueryDSL |
            | --- | --- | --- |
            | 1회 | 255 | 173 |
            | 2회 | 273 | 40 |
            | 3회 | 511 | 41 |
            | 4회 | 248 | 33 |
            | 5회 | 260 | 42 |
            | 6회 | 261 | 36 |
            | 7회 | 264 | 34 |
            | 8회 | 249 | 34 |
            | 9회 | 253 | 32 |
            | 10회 | 258 | 36 |
        - 학과명으로 조회
            
            
            |  | JPA | QueryDSL |
            | --- | --- | --- |
            | 1회 | 198 | 55 |
            | 2회 | 193 | 9 |
            | 3회 | 175 | 9 |
            | 4회 | 167 | 8 |
            | 5회 | 176 | 8 |
            | 6회 | 183 | 9 |
            | 7회 | 176 | 9 |
            | 8회 | 167 | 9 |
            | 9회 | 174 | 9 |
            | 10회 | 186 | 8 |
        - 과목 코드로 조회
            
            
            |  | JPA | QueryDSL |
            | --- | --- | --- |
            | 1회 | 31 | 19 |
            | 2회 | 31 | 17 |
            | 3회 | 26 | 17 |
            | 4회 | 27 | 17 |
            | 5회 | 26 | 17 |
            | 6회 | 29 | 15 |
            | 7회 | 29 | 16 |
            | 8회 | 40 | 16 |
            | 9회 | 33 | 15 |
            | 10회 | 42 | 17 |
        - 대학으로 조회
            
            
            |  | JPA | QueryDSL |
            | --- | --- | --- |
            | 1회 | 174 | 25 |
            | 2회 | 167 | 18 |
            | 3회 | 412 | 19 |
            | 4회 | 178 | 20 |
            | 5회 | 184 | 22 |
            | 6회 | 188 | 19 |
            | 7회 | 187 | 25 |
            | 8회 | 162 | 17 |
            | 9회 | 174 | 15 |
            | 10회 | 167 | 17 |
        - 전공명으로 조회
            
            
            |  | JPA | QueryDSL |
            | --- | --- | --- |
            | 1회 | 134 | 27 |
            | 2회 | 111 | 8 |
            | 3회 | 130 | 8 |
            | 4회 | 116 | 8 |
            | 5회 | 119 | 8 |
            | 6회 | 110 | 8 |
            | 7회 | 126 | 9 |
            | 8회 | 126 | 9 |
            | 9회 | 120 | 8 |
            | 10회 | 113 | 9 |
    
    결과를 보면 시간표 조회는 시간이 줄어들지는 않았지만 **N+1 문제를 해결**하여 Select문의 개수를 줄여 성능을 최적화시켰습니다. 또한 강의 조회를 보면 N+1 문제를 해결하여 Select문의 개수를 줄인 것뿐만 아니라 시간도 **최소 255ms에서 173ms(32.1%)**, **최대 130ms에서 8ms(93.8%)**로 줄어 성능을 최적화시켰습니다.
    
- 남은 수강신청 인원 캐시 전략
    
    수강 신청에 대해 동시성 문제를 해결했지만, 응답 속도 개선이 필요했습니다. 조회와 값 변경이 빠른 Redis를 선정하였고, ElastiCache for Redis로 구현하였습니다.
    
    다음과 같이 **수강 신청 API**의 성능을 실제 서버에서 각각 테스트하였습니다.
    
    테스트는 실제 서버 환경(EC2: t3.medium, RDS: t3.micro)에서 진행되었습니다.
    
    - 이전 로직과 변경 로직
        
        - 캐시 도입 전 로직
          ![전](https://github.com/team-11-challenge/back-end/assets/95194606/fd4760a4-b06d-40b9-b734-9cc670c63ada)
        - 캐시 도입 후 로직
          ![후](https://github.com/team-11-challenge/back-end/assets/95194606/b704a31e-f149-4758-94fc-cd4bc04429d3)

        
    
    ### **캐시 도입 이전**
    
    - 테스트 조건: 20000명의 학생 데이터로 4000 threads를 1초에 실행하고, 5번 반복.
    - 전체 통계 표
        
        ![1번](https://github.com/team-11-challenge/back-end/assets/95194606/3ec89530-41e6-4b7c-891a-72289bd1b87a)

        
        - 20000개 표본 모두 오류 없이 통과하였습니다.
        - 동시성 제어 성공하였습니다.
        - 응답 시간 평균은 21.62초, TPS는 163.23입니다.
    
    ### **캐시 도입 후**
    
    - 테스트 조건: 20000명의 학생 데이터로 4000 threads를 1초에 실행하고, 5번 반복.
    - 전체 통계 표
        
        ![2번](https://github.com/team-11-challenge/back-end/assets/95194606/633327e8-43d3-4121-a4a7-615506e1c214)

        
        - 20000개 표본 모두 오류 없이 통과하였습니다.
        - 동시성 제어 성공하였습니다.
        - 응답 시간 평균은 14.57초, TPS는 246.75입니다.
    
    ### 성능 개선 결과
    
    캐시를 통해 수강신청 로직보다 먼저 좌석 수를 확인합니다. 따라서 초과인원이 신청 시에는 수강신청 로직을 거치지 않도록 하였습니다. 결과적으로 기존보다 **응답 속도가 7.05초(32.6%)** 개선되었음.
# 🎯 05 | 트러블 슈팅
- 시간표 중복 확인 로직
    
    ### 문제 상황
    
    수강신청 시, 강의 시간표가 겹치는지 확인하는 과정이 필요함
    
    ### 1차 알고리즘 구상
    
    - 시간표는 다음과 같이
        - `“요일1 교시1,요일2 교시1 교시2, ..”`
        - 문자열 안에 요일과 교시를 공백으로 구분하고, 요일은 콤마로 구분
    - 해당 학생이 수강신청한 강의들의 **시간표들**과 신청할 강의의 **시간표**를 비교해야함
    - 시간표를 다음과 같이 **2차원 boolean 배열**로 바꿔 해당 시간의 강의 유무를 표시한 후 비교
        
        
        |  | 월(0) |
        | --- | --- |
        | 1교시 | T |
    - 그러나 2차원 배열이라 비교하는 데 **O(n^2)**이 걸림
        - n이 매우 작은 경우이지만 더 개선할 수 있을거라 판단
    
    ### 2차 알고리즘 구상
    
    - 기존 2차원 boolean 배열에서 **1차원 int 배열**로 변경
        
        
        | 월(0) | 18 |
        | --- | --- |
    - **비트마스크** 사용
        - (n+1)번째 비트에 1을 표시하여 n교시를 표현해줌: `1 << n`
        - 예) 1교시, 4교시인 경우 → `10010(2)` → 18로 표시
    - 비교 로직을 **O(n)**으로 개선
    
    ### 문제 해결
  ```java
  // 비교할 배열로 만들기
  private int[] makeIntTimetable(String rawTimetable) {
      enum DayOfWeek { 월, 화, 수, 목, 금, 토, 일 }
      int[] timetable = new int[7];
      for (String t : rawTimetable.split(",")) {
          int i = DayOfWeek.valueOf(t.substring(0, 1)).ordinal();
          String[] periods = t.substring(2).split(" ");
          for (String period : periods) {
              // 비트마스크 사용해 (n + 1) 번째 비트에 1표시
              int p = **1 << Integer.parseInt(period)**;
              **timetable[i] |= p;**
          }
      }
      return timetable;
  }
  
  // 시간표 비교
  private static void compareTimetable(int[] timetable1, int[] timetable2) {
      for (int i = 0; i < 7; i++) {
          if ((**timetable1[i] & timetable2[i]**) > 0) { // 겹치는게 있을 경우 &연산시 0보다 큰값이 나옴
              throw new CourseTimeConflictException(COURSE_TIME_CONFLICT);
          }
      }
  }
  ```
- 동시성 제어
    
    ### 문제 상황
    
    1. 단일 서버 환경에서 동시성 제어가 되지 않아 수강 정원보다 많은 인원이 신청되었음.
    2. 장바구니 신청의 성능 테스트 중에는 데드락이 발생하였음.
    
    ### 원인 분석
    
    - Mysql InnoDB의 특성
        - Mysql InnoDB: 5.5버전 이후 기본 스토리지 엔진으로 사용되고 MVCC를 통해 Snapshot을 만들어 각각의 트랜젝션을 일관되게 관리함.
        - Isolation Level: Default 로 REPEATABLE READ. 따라서 하위 단계의 문제점을 해결함.
            - READ UNCOMMITTED, READ COMMITTED, REPEATABLE READ, SERIALIZABLE 중에서 3단계
        - Next-Key Lock: Record Lock과 Gap Lock을 사용하여 REPEATABLE READ 에서 발생하는 PHANTOM READ도 다른 DB들과 달리 이미 해결하고 있음.
    - Write Skew 문제
        - InnoDB의 기본 격리 레벨인 REPEATABLE READ 에서 발생할 수 있는 문제로 다른 트랜젝션이 서로 다른 객체에 write하는 경우 발생하는 문제
        - 같은 강의를 동시에 신청한다면 A의 강의 신청 트랜잭션이 진행 중일 때, 아직 강의 엔티티의 신청인원이 늘어난 것이 아니기 때문에 B학생도 강의를 신청 가능하다.
        - 따라서 A와 B모두 수강제한 인원을 초과하더라도 수강신청 테이블에 추가되는 일이 발생한다.
    - 데드락 문제
        - 서로 필요한 자원을 요청하면서 교착상태가 발생함.
        - 즉, 자원 선점 시 독점적으로 자원 접근을 막을 필요가 있음.
    
    ### 해결 방안 및 의견 결정
    
    - 격리레벨의 조정
        - 격리 레벨을 상위 단계인 SERIALIZABLE로 높이는 방법.
        - 하지만 이는 트랜젝션 진행 중 모든 접근을 차단하기 때문에 정합성이 보장되나, 성능이 매우 떨어지고 데드락이 쉽게 발생할 우려가 있어서 제외함.
    - 낙관적 락과 비관적 락
        - JPA를 통한 락의 구현은 낙관적 락과 비관적 락으로 나눌 수 있는데, 현재 수강신청 로직은 유저 플로우 상 충돌이 빈번히 일어나기 때문에 비관적 락을 도입하는 것이 효율적임.
        - 또한, 낙관적 락으로 처리하게 되면 rollback 후 처리 로직을 구현해야 하고, 잦은 충돌로 인한 rollback 많이 발생하여 오히려 성능이 떨어질 것이라 생각함.
        - 따라서 비관적 락을 통한 Mysql의 Locking Read를 도입하기로 결정.
            - 비관적 락 중에서도 read와 write를 모두 제한하는 exclusive lock을 선택.
    
    ### 문제 해결
    
    - 다음과 같이 수강 신청, 장바구니 신청 로직 안의 조회 부분에 대하여 exclusive lock을 도입
      ```java
      public interface CourseRepository extends JpaRepository<Course, Long> {
      
         @Lock(LockModeType.PESSIMISTIC_WRITE)
         @Query("SELECT c FROM Course c WHERE c.id = :courseId")
         Optional<Course> findCourseByIdAndLock(Long courseId);
      
      }
      ```
      - **추가 동시성 제어 방법 비교**
    - **개요**
        
        비관적 락을 적용하여 동시성 문제를 해결했지만, DB LOCK 이외의 LOCK 을 적용했을 때 성능을 비교하여 최종적인 동시성 제어 방법을 선정하고자 했다. 테스트는 로컬 환경(13th Gen Intel(R) Core(TM) i5-1335U / 16GB)에서 진행되었다.
        
    - **비관적락(DB의 Exclusive Lock)**
        - 테스트 조건: 20000명의 학생 데이터로 4000 threads를 1초에 실행하고, 5번 반복.
        - 전체 통계 표
            
            ![비ㅗㄱ](https://github.com/team-11-challenge/back-end/assets/95194606/a2af2cf9-4aa1-46b5-8e1d-b6b2630355a2)

            
        - 20000개 표본 모두 오류 없이 통과
        - 동시성 제어 성공
        - 응답 시간 평균: 6.34초, TPS: 544.62
    - **Spin Lock**
        - 테스트 조건: 20000명의 학생 데이터로 4000 threads를 1초에 실행하고, 5번 반복.
        - 전체 통계 표
            
            ![전체2](https://github.com/team-11-challenge/back-end/assets/95194606/9d5cfd6c-6e0e-4214-8aa2-5e93df88754d)

            
        - 20000개 표본 모두 오류 없이 통과
        - 동시성 제어 성공
        - 응답 시간 평균: 20.58초, TPS: 162.03
    - **Redisson Lock**
        - 테스트 조건: 20000명의 학생 데이터로 4000 threads를 1초에 실행하고, 5번 반복.
        - 전체 통계 표
            
           ![전체3](https://github.com/team-11-challenge/back-end/assets/95194606/80f1cc5a-c42c-4bad-a76b-d2d2b08c4289)

            
        - 20000개 표본 모두 오류 없이 통과
        - 동시성 제어 성공
        - 응답 시간 평균: 11.85초, TPS: 297.66
    - **결론: 비관적락 적용**
        - Spin Lock은 현 프로젝트의 예상 동시 접속(4000명)을 고려했을 때, 응답시간, TPS 수치가 낮았다. 또한, 계속해서 LOCK을 획득하려고 시도하기 때문에 CPU 사용이 높아서 고스펙의 서버가 아니라면 수용하기 어렵다고 판단함.
        - Redisson Lock은 락의 Lease Time과 Time Out을 설정할 수 있는 장점이 있지만, 현재의 예상 트래픽 수준에서 오히려 비관적락 보다 낮은 성능 수치를 보임.
        - 결과적으로 비관적락(DB의 exclusive lock)이 Mysql의 격리 레벨을 유지한 상태에서 적용 시 안전성, 성능 측면에서 가장 적합하다고 판단함.
     
- 접속자 대기열
    - 대기열 도입
        
        ### 도입이유
        
        동시에 오는 수많은 요청을 동시에 수행하지 않고 일정한 대기열을 만들어 순차적으로 요청을 수행하여 DB부하를 방지할 수 있고, 사용자들에게 대기순번과 예상 대기시간에 대한 정보를 제공함으로써 UX를 높이기 위함
        
        ### 문제 상황
        
        수강신청 시, 대규모 트래픽으로 인해 DB 부하 및 대기 시간에 대한 처리 필요
        
        ### 해결 방안
        
        1. Kafka: 미들웨어를 활용한 정확한 메세지 큐 시스템 구성 가능
        2. Redis: Sorted Set을 활용하면 score 기준으로 정렬이 가능하고, Queue처럼 사용 가능
        
        ### 의견 조율
        
        1. Kafka: 속도 및 안정성이 좋지만 구축 경험이 없어 시간적 비용이 큼
        2. Redis: 빠른 속도를 자랑하고 구축 경험이 있어 비교적 시간적 비용이 작지만 안정성 문제가 있음
        
        ### 의견 결정
        
        대규모 데이터 스트리밍 및 이벤트 처리를 위해 내구성, 확장성 및 내결함성이 뛰어난 메시지 대기열이 필요한 경우 Kafka가 적합하겠지만, 적은 대기 시간, 단순성 및 실시간 응답성을 우선시하여 Redis가 더 적합하다 판단
        
    - 대기열로 인한 응답 분리 문제
        
        ### 도입이유
        
        수강 신청 요청과 응답을 분리하게 되면서 서버에서 클라이언트로 따로 응답을 보내주기 위함
        
        ### 문제 상황
        
        수강 신청 요청을 바로 수행하지 않고 대기열에 추가한 후 따로 요청을 처리하면서 요청에 대한 응답을 컨트롤러로 바로 반환해줄 수 없음
        
        ### 해결 방안
        
        1. Long Polling: 서버에서 접속 시간을 길게 하는 방식
        2. SSE(Server-Sent-Event): 서버의 데이터를 실시간, 지속적으로 Streaming하는 기술
        3. Websocket: 양방향 채널을 이용한 실시간 양방향 통신
        
        ### 의견 조율
        
        1. Long Polling: 다수의 클라이언트에게 동시에 이벤트가 발생될 경우에는 서버의 부담이 급증
        2. SSE(Server-Sent-Event): 가볍고 구현하기 쉽지만, 최대 동시 접속 수가 제한이 있음
        3. Websocket: 최대 동시 접속 수가 제한이 없고 stomp를 사용하면 pub/sub 모델 사용 가능
        
        ### 의견 결정
        
        동시 접속자가 많이 몰릴 수 있으므로, **websocket & stomp**를 사용해 각 클라이언트별 응답을 구독해둔 후 실시간으로 메세지를 보내주는 방식을 선택
        
    
    ### **문제 해결**
    
    <img width="904" alt="대기열" src="https://github.com/team-11-challenge/back-end/assets/95194606/ba214b0f-a83f-4765-b621-95ccf81e30cd">

    
    1. 수강신청 요청이 오면 Redis의 Sorted Set(대기열)에 요청(key: 수강신청, member: 요청내용, score: 시간)을 저장합니다.
    2. 스케쥴링
        1. 500ms마다 대기열에서 zrange 명령어를 통해 100개씩 정보를 가져와 수강신청 요청을 처리한 후 결과를 클라이언트로 보내줍니다.
        2. 100ms마다 zrank 명령어를 통해 score(시간)에 대한 오름차순 순위(사용자의 대기순번)을 클라이언트로 보내줍니다.
- 스케줄러 중복 수행 문제
    
    ### 문제 상황
    
    - 현재 프로젝트에는 수강신청 API에 대해 접속자 대기열이 존재.
    - 대량의 요청 발생 시 DB부하를 방지하기 위해 일정한 대기열을 만들어 순차적으로 요청을 수행하도록 하는 것이 원래 목적.
    - 하지만 분산 서버 환경에서 대기열 기능의 스케줄러가 중복 수행되는 문제 발생.
    
    ### 원인 분석
    
    - 분산 서버 환경에서 각각의 서버에 동일한 어플리케이션이 실행 중.
    - 따라서 스케줄러가 중복으로 실행되면, 같은 정보를 계속 중복 수행하게 되었음.
    
    ### 해결방안 및 의견결정
    
    - 스케줄러를 동시에 실행하지 않는 방법으로 스케줄링 서버 분리와 락 설정 등이 있었음.
        - 하지만, 스케줄링 서버 분리가 ShedLock에 의한 동시성 제어보다 시간적, 자원적 소모가 더 크다고 판단함.
    - 따라서, 스케줄러에 ShedLock을 도입하기로 결정함.
    
    ### 문제 해결
    
    - 스케줄락에 대한 테이블 생성
    - LockProvider를 구현하여 적용하였고, 서버가 번갈아가며 락을 획득하고 스케줄을 수행하게 되었음.
     ```java
    @Scheduled(fixedDelay = 100)
    @SchedulerLock(
        name = "scheduledLockGetOrder",
        lockAtLeastFor = "1s",
        lockAtMostFor = "2s"
    )
    public void getOrder() throws JsonProcessingException {
        queueService.getOrder(Event.REGISTRATION);
    }
    
    @Scheduled(fixedDelay = 500)
    @SchedulerLock(
        name = "scheduledLockRegister",
        lockAtLeastFor = "4s",
        lockAtMostFor = "8s"
    )
    public void registerByScheduled() throws JsonProcessingException {
        queueService.publish(Event.REGISTRATION);
    }
    ```
 - 분산 서버로 인한 웹소켓 통신 문제
    
    ### 문제 상황
    
    기존 로직에서는 수강 신청 요청을 보내면 웹소켓을 활용하여 수강신청 요청에 대한 결과와 요청에 관한 대기열 정보를 구독하여 결과 메시지를 송신해주는 방식으로 구현하였습니다.
    
    ![소](https://github.com/team-11-challenge/back-end/assets/95194606/812cdcd6-f2d9-4eda-8e8b-bda1b2b1bb25)


    
    그러나 분산 서버 환경에서 연결과 구독은 서버1에서 처리되었는데, 요청에 관한 처리를 서버2가 담당하게 되면서, 서버2가 전송하는 결과 메시지 송신에 문제가 발생하였습니다.
    
    ![소1](https://github.com/team-11-challenge/back-end/assets/95194606/a5b8f2a9-a221-4182-b5a8-b6b67caf0044)


    
    ### 문제 해결
    
    **첫번째 시도**
    
    소켓 통신을 서버 1에게 전임하는 방식을 채택하여 문제를 해결하였습니다.
    
    ![소2](https://github.com/team-11-challenge/back-end/assets/95194606/b78486ff-fc0f-44f5-ac90-397a164ff9d1)

    
    그러나 서비스 특성상 서버가 마비될 우려가 있어 웹 소켓 통신을 겸하는 서버가 마비될 경우 UX에 치명적인 문제가 발생될 것을 예상하였습니다. 
    
    **최종 문제 해결**
    
    최종적으로 웹 소켓 전용 서버를 생성하는 것으로 대기열을 구현하였습니다.
   ![소3](https://github.com/team-11-challenge/back-end/assets/95194606/73dc461e-1576-457c-b4ea-3beafde7b2fc)
- 서버 부하 관리
    
    ### 문제 상황
    
    서비스 특성상 동시에 발생하는 트래픽의 규모를 예측하기 쉽지 않습니다. 따라서 서버의 마비를 방지하기위해 트래픽을 감지하여 가변적으로 서버가 증설되어야 했습니다.
    
    ### 해결 방안
    
    AWS의 Auto Scaling 을 통해 문제를 해결할 수 있었습니다.
    
    ### 적용한 오토 스케일링 정책
    
    ![오1](https://github.com/team-11-challenge/back-end/assets/95194606/26ac2ac6-fafe-4581-8656-f29b998c0760)

    
    기본적으로 저희는 상시 유지 1대 서버와 자동 증설 서버 1대 총 2대의 서버를 유지하고 있습니다.
    
    **동적 크기 조정 정책:** 인스턴스를 생성할 조건을 적용합니다.
    
    ![오2](https://github.com/team-11-challenge/back-end/assets/95194606/284b334c-40ee-48c2-b67b-b38080d51541)

    
    주로 사용되는 정책은 Auto Scaling group에 속한 인스턴스들의 평균 CPU 로드율로 조정하는 것과 로드밸런서를 통해 특정 대상 그룹에 들어가는 분당 요청수로 조정하는 것이 있습니다.
    
    저희는 분당 요청수 대상 그룹의 분당 요청수가 4천으로 유지될 수 있도록 동적 크기 조정 정책을 설정했습니다.
    
    **기간 정책:** 특정 기간에 동적 크기 조정 정책으로 생성될 수 있는 최소 최대 개수를 설정합니다.
    
    ![오3](https://github.com/team-11-challenge/back-end/assets/95194606/7fa904fc-d911-40d9-bf09-06f6fc56efe3)

    
    가장 트래픽이 몰리는 당일에 자동적으로 최대 인스턴스 개수를 늘리고 서버 한 대를 증설하여 상시 유지 2대 동적 추가 2대로 설정했습니다.
    
    ![오4](https://github.com/team-11-challenge/back-end/assets/95194606/762622c4-9f8e-4f12-a8cf-a918393d9d93)

# 🛠️ 06 | 기술 스택

보통 첫 날에 트래픽이 많이 몰리고 급격하게 줄어들기 때문에 2일 후 기본값으로 되돌아 가도록 설정했습니다.
    
| Backend | Tech | Spring Boot Spring JPA Spring Security QueryDSL  |
| --- | --- | --- |
|  | Platform | Ubuntu |
|  | DB | AWS RDS(Mysql) AWS ElastiCache(Redis) |
|  | DevOps | AWS EC2 Docker |
|  | CI/CD | Github Actions |
|  | Test | JUnit5 Apache Jmeter |
|  | Monitoring | Grafana Prometheus(JVM, Node Exporter) |
|  | logging | logstash Amazon Open Search  |
| Frontend | Tech | HTML CSS JavaScript Bootstrap |
|  | CI/CD | Netlify |
| Protocol |  | HTTPS Websocket & STOMP |

- 기술적 의사 결정
    - **Github Action**: CI/CD가 처음이라 Jenkins보다 설정이 단순하고 Github와 연동이 용이한 Github Action으로 결정하였습니다.
    - **Netlify**: Github와 연동되며 배포 관리가 용이한 Netlify로 결정하였습니다.
    - **Docker, Docker Compose**:  자원이 한정 되어 있어 큰 규모의 트래픽을 테스트를 하기 위해 scale-out을 해야했기 때문에 이식성이 좋은 Docker를 채택하였습니다. 또한, 다수의 컨테이너를 관리하고 CI/CD를 위해 Docker Compose를 채택하였습니다.
    
    ---
    
    - **JMeter**: Testing Tool 중 사용이 어렵지 않고, Java 기반이며 참고할 Reference가 많다는 점이 주요했습니다. 또한, 세밀한 성능테스트가 필요하지 않은 현 프로젝트에 적합하다 판단하여 JMeter를 채택하였습니다.
    - **Prometheus**, **Grafana**: Prometheus는 다양한 소스에서 메트릭을 수집할 수 있으며 Grafana는 다양한 데이터 소스와 통합할 수 있습니다. 또한 Grafana는 다양한 시각화 유형을 제공하여 메트릭을 보다 효과적으로 분석할 수 있습니다. 따라서, Prometheus와 Grafana를 함께 사용하면 시스템의 모든 메트릭을 한 곳에서 시각화할 수 있기 때문에 채택하였습니다.
    - **Logback**: log4j2가 성능이 제일 좋지만 현 프로젝트에서 logging에 높은 성능을 요하지 않고, 설정이 간단한 logback이 적합하다고 판단하여 logback을  채택하였습니다.
    
    ---
    
    - **QueryDSL**: 시간표 조회와 강의 조회에서 N+1문제가 발생하였습니다. 이를 해결하기 위해서 Fetch Join을 사용하기 위해 문법 오류를 방지하고, 복잡한 쿼리를 쉽게 작성하고 표준화된 방식으로 쿼리를 작성할 수 있어 가독성이 좋은 QueryDSL를 채택하였습니다.
    - **Elastic Cache for Redis**: 대기열 및 락 접근 전에 수강 신청 가능 여부를 확인하기 위해 조회가 빠른 Redis를 선정하였습니다. 또한 동시성 제어를 따로 해줄 필요 없는 Elastic Cache for Redis를 채택하였습니다.
    
    ---
    
    - **ALB**: AWS의 ACM과 Route53을 이용해 SSL을 발급 받아 인증 받고 해당 도메인과 인스턴스를 연결해주는 과정에서 ALB가 사용되기 때문에 간편하게 로드밸런싱까지 할 수 있는 ALB를  채택하였습니다.
    - **AWS Auto Scaling**: 서비스 특성상 특정 기간에 트래픽이 몰리는 것을 예측할 수 있기 때문에 ASG의 기간 정책을 사용하여 자동으로 서버를 증설할 수 있어 채택하였습니다.
    - **Amazon Opensearch**: logback과 logstash를 이용하여 로그의 중앙집중화를 구축하기 용이하고, 비용적인 측면에서도 저렴하여 채택하였습니다.
    
    ---
    
    - **HTTPS**: 웹사이트의 무결성을 보호해주므로 HTTP보다 안전한 HTTPS를 선택하였습니다.
    - **Websocket & STOMP**: 대기열 적용 후 수강신청 요청과 응답의 분리가 필요했습니다. 이에 대한 선택지인 Long Polling, SSE, Websocket 중 최대 동시 접속자에 제한이 없고, 실시간으로 빠른 통신이 가능한 Websocket을 선택하였고, Websocket과 함께 STOMP를 통한 pub/sub 모델을 사용하여 개별적으로 응답을 보내주었습니다.

# 👨‍👩‍👧‍👦 07 | 팀원
| 이름 | 담당 역할 | Github |
| --- | --- | --- |
| 정명주(팀장) | - 강의 데이터 수집 및 전처리 후 DB 적재<br>- 수강신청, 조회, 삭제 기능 구현<br> - Redis를 활용한 접속자 대기열 기능 구현<br>- AWS EC2 환경 구축<br>- Docker를 활용한 인프라 구축<br>- CI/CD(Github Actions) 적용<br>- Junit5와 Mockito를 이용한 테스트 코드 작성<br>- JMeter를 활용한 부하 테스트 수행<br>- 전체 Front-End UI 디자인 수정<br>- 접속자 대기열 Front-End 구현 <br>| https://github.com/thing-zoo/ |
| 장미 | - 강의 데이터 수집 및 전처리 후 DB 적재<br>- 시간표 조회 기능 구현<br>- 학생 정보, 기간 조회 구현<br>- 사간표 조회 및 강의 조회 Query 최적화<br>- 수강신청 로직에 Redis Cache 도입<br>- Swagger 적용<br>- Front-End 시간표 작업<br>- Junit5와 Mockito를 이용한 테스트 코드 작성<br>- JMeter를 활용한 부하 테스트 수행 | https://github.com/klettermi |
| 류이환 | - Spring Security, Jwt 기반의 로그인 기능 구현<br>- 장바구니 조회, 신청, 삭제 기능 구현<br>- 동시성 제어(Lock 비교, 테스트, 적용)<br>   - 단일 서버: 비관적 락 적용<br>   - 분산 서버: 스케줄 락 적용<br>- logging 출력 최적화 및 중앙 집중형 시스템 구축<br>- Front-End 초기 설정 및 UI 작업, CI/CD 적용<br>- Junit5와 Mockito를 이용한 테스트 코드 작성<br>- JMeter를 활용한 부하 테스트 수행<br>- JMeter 전체 테스트 결과 분석 및 정리 | https://github.com/YiHwanRyu |
| 김재익 | - Bakc-End 베이스 코드 구축 (디렉토리 구조, 공통 응답 클래스, 응답코드 ENUM 등)<br>- 강의 조회 기능 구현<br>- AWS DNS 환경 구축<br>- AWS ALB, ASG 를 이용한 로드밸런싱, 오토스케일링 구현<br>- 모니터링 기능 구현 (Grafana, Prometheus)<br>- 대기열 기능 개선 및 개편<br>- Front-End 초기 설정 및 UI 작업<br>- Junit5와 Mockito를 이용한 테스트 코드 작성<br>- JMeter를 활용한 부하 테스트 수행  | https://github.com/Eulga |
