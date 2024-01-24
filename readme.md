## 커피숍 주문 시스템
- 커피숍 주문시스템을 구현해봅시다.
- 커피 주문에 <ins>필요한 메뉴를 구성하고 조회</ins>가 가능해야 합니다.
- 커피 주문은 <ins>포인트</ins>로 가능합니다.
- 커피 주문내역을 통해 <ins>인기있는 메뉴</ins>를 추천합니다.

## 요구사항
1) 커피 메뉴 목록 조회 API (메뉴ID, 이름, 가격)
2) 포인트 충전하기 API (결제는 포인트로하고 충전, 사용자 식별)
3) 커피 주문, 결제하기 API (사용자 식별값, 메뉴ID를 입력 받아 주문을 하고 결제를 진행, 결제는 충전한 포인트 차감, 주문내역을 데이터 수집플랫폼으로 실시간 전송하는 로직- MockAPI)  
4) 인기메뉴 목록 조회 API (최근 7일간 인기있는 메뉴 3개 조회, 메뉴별 주문횟수가 정확)
- 다수서버 고려, TDD, 동시성이슈, 데이터일관성 

## 설계 및 분석
- 도메인 설계 : Menu , User, Order ( 커피숍의 주문시스템은 메뉴에서, 사용자가, 주문을 하는 형태이다.)
- 에러응답,에러코드 : CoffeeException, CoffeeShopErrors
- 대용량 트래픽 : Kafka (동시성제어를 위한 이벤트메시지브로커)
- 핵심 문제해결 전략 :가독성

## 활용기술
- Spring Boot 3.0.2
- Java 17
- JPA
- MariaDB
- Redis
- Kafka
- H2 (테스트 코드)

## TDD
- 메뉴,사용자,주문 생성 
- 잔액부족 유저의 결제/ 존재하는 회원의 유저포인트 적립
- 신규/기존 유저 포인트 저장

 
## ERD
![image](https://github.com/MyoungSoo7/coffee-shop-clone/assets/13523622/55fd2681-1263-4a56-aada-8f37e97548f0)
