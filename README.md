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

- [시간표 중복 확인 로직](https://github.com/team-11-challenge/back-end/wiki/%EC%8B%9C%EA%B0%84%ED%91%9C-%EC%A4%91%EB%B3%B5-%ED%99%95%EC%9D%B8-%EB%A1%9C%EC%A7%81)
- [동시성 문제 해결](https://github.com/team-11-challenge/back-end/wiki/%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)
- [접속자 대기열](https://github.com/team-11-challenge/back-end/wiki/%EC%A0%91%EC%86%8D%EC%9E%90-%EB%8C%80%EA%B8%B0%EC%97%B4)
- [분산 서버로 인한 웹소켓 통신 문제](https://github.com/team-11-challenge/back-end/wiki/%EB%B6%84%EC%82%B0-%EC%84%9C%EB%B2%84%EB%A1%9C-%EC%9D%B8%ED%95%9C-%EC%9B%B9%EC%86%8C%EC%BC%93-%ED%86%B5%EC%8B%A0-%EB%AC%B8%EC%A0%9C)
→ [레벨별 성능테스트 결과 보고서](https://github.com/team-11-challenge/back-end/wiki/%EC%84%B1%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8)

# 🛠️ 06 | 기술 스택

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
→ [기술적 의사 결정](https://github.com/team-11-challenge/back-end/wiki/%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90-%EB%B0%8F-%EA%B8%B0%EC%88%A0%EC%A0%81-%EC%9D%98%EC%82%AC%EA%B2%B0%EC%A0%95#%EA%B8%B0%EC%88%A0%EC%A0%81-%EC%9D%98%EC%82%AC-%EA%B2%B0%EC%A0%95)

# 👨‍👩‍👧‍👦 07 | 팀원
| 이름 | 담당 역할 | Github |
| --- | --- | --- |
| 정명주(팀장) | - 강의 데이터 수집 및 전처리 후 DB 적재<br>- 수강신청, 조회, 삭제 기능 구현<br> - Redis를 활용한 접속자 대기열 기능 구현<br>- AWS EC2 환경 구축<br>- Docker를 활용한 인프라 구축<br>- CI/CD(Github Actions) 적용<br>- Junit5와 Mockito를 이용한 테스트 코드 작성<br>- JMeter를 활용한 부하 테스트 수행<br>- 전체 Front-End UI 디자인 수정<br>- 접속자 대기열 Front-End 구현 <br>| https://github.com/thing-zoo/ |
| 장미 | - 강의 데이터 수집 및 전처리 후 DB 적재<br>- 시간표 조회 기능 구현<br>- 학생 정보, 기간 조회 구현<br>- 사간표 조회 및 강의 조회 Query 최적화<br>- 수강신청 로직에 Redis Cache 도입<br>- Swagger 적용<br>- Front-End 시간표 작업<br>- Junit5와 Mockito를 이용한 테스트 코드 작성<br>- JMeter를 활용한 부하 테스트 수행 | https://github.com/klettermi |
| 류이환 | - Spring Security, Jwt 기반의 로그인 기능 구현<br>- 장바구니 조회, 신청, 삭제 기능 구현<br>- 동시성 제어(Lock 비교, 테스트, 적용)<br>   - 단일 서버: 비관적 락 적용<br>   - 분산 서버: 스케줄 락 적용<br>- logging 출력 최적화 및 중앙 집중형 시스템 구축<br>- Front-End 초기 설정 및 UI 작업, CI/CD 적용<br>- Junit5와 Mockito를 이용한 테스트 코드 작성<br>- JMeter를 활용한 부하 테스트 수행<br>- JMeter 전체 테스트 결과 분석 및 정리 | https://github.com/YiHwanRyu |
| 김재익 | - Bakc-End 베이스 코드 구축 (디렉토리 구조, 공통 응답 클래스, 응답코드 ENUM 등)<br>- 강의 조회 기능 구현<br>- AWS DNS 환경 구축<br>- AWS ALB, ASG 를 이용한 로드밸런싱, 오토스케일링 구현<br>- 모니터링 기능 구현 (Grafana, Prometheus)<br>- 대기열 기능 개선 및 개편<br>- Front-End 초기 설정 및 UI 작업<br>- Junit5와 Mockito를 이용한 테스트 코드 작성<br>- JMeter를 활용한 부하 테스트 수행  | https://github.com/Eulga |
