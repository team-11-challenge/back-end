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

2️⃣ **검색 성능 개선을 통한 빠른 검색 및 정확도 높은 검색 제공**
- 검색에 대한 빠른 결과 제공
- 다양한 필터에 대한 정확한 검색 결과 제공

3️⃣ **4000명 이상 접속자 동시성 제어**
- 비관적 락을 통한 동시성 제어
- 분산 서버 환경에서 스케줄 락을 통한 이벤트 중복 수행 방지

4️⃣ **20K 이상의 트래픽에도 안정적인 서비스 제공**
- 로드밸런싱 및 오토스케일링
- 접속자 대기열 기능을 통한 대기순번 및 예상시간 제공

# ⚙️ 03 | 아키텍쳐
## 서비스 아키텍쳐
![서비스아키텍처](https://github.com/team-11-challenge/back-end/assets/95194606/e6521883-872a-4a6e-ad0d-2b2595fa8f90)

## ERD
![erd](https://github.com/team-11-challenge/back-end/assets/95194606/84c97ed6-e24e-4570-bac0-23e291c6d314)

## API 명세서 (swagger)
→ [REST API Reference](https://github.com/team-11-challenge/back-end/wiki/REST-API-Reference)

# 04 | 성능 개선

- [강의 조회 쿼리 최적화](https://github.com/team-11-challenge/back-end/wiki/%EA%B0%95%EC%9D%98-%EC%A1%B0%ED%9A%8C-%EC%BF%BC%EB%A6%AC-%EC%B5%9C%EC%A0%81%ED%99%94)
- [남은 수강신청 인원 캐시 전략](https://github.com/team-11-challenge/back-end/wiki/%EB%82%A8%EC%9D%80-%EC%88%98%EA%B0%95%EC%8B%A0%EC%B2%AD-%EC%9D%B8%EC%9B%90-%EC%BA%90%EC%8B%9C-%EC%A0%84%EB%9E%B5)

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
