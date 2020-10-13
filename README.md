# BANKSALAD_STOCK

##### 2020.09.23 - 2020.09.27

미국 주식 `symbol`을 입력받아 지난 180 일 동안 최대 수익을내는 단일 매수 및 매도 날짜를 반환하는 웹 서비스입니다. 인메모리 DB를 사용하여 반복적인 요청에 대해 불필요한 리소스 낭비를 줄였습니다. 또한, 단순한 기능 구현보다는 확장성과 DDD 구조를 고민하며 개발을 진행했습니다. 아래 프로젝트 소개와 학습 내용 참고 부탁드립니다.

<br>

## # 프로젝트 소개

### Project FlowChart

- ![FlowChart2](https://user-images.githubusercontent.com/58318041/94372700-dfd5e500-013a-11eb-88ea-b9955cf8e2cb.png)

<br>

### ERD

- ![ERD](https://user-images.githubusercontent.com/58318041/94369239-87dfb400-0123-11eb-8fc3-1de09bfbd3ff.png)

<br>

### Skills

- `Java`, `Spring Boot`, `H2`, `Spring Data JPA`, `Hibernate`, `WebClient`, `JUnit4`, `Git`

<br>

### API 명세

- 미국 시간 기준으로 180일 이전의 데이터를 조회합니다.

| URL                   | Method | Description              | Response Code |
| --------------------- | ------ | ------------------------ | ------------- |
| /stock/{symbol}       | GET    | 주식 최대 수익 정보 조회 | 200           |
| /stock/{symbol}/chart | GET    | 주식 전체 차트 조회      | 200           |

- /stock/{symbol}

  ```json
  {
  	"symbol": "AAPL",
  	"date": "2020-09-25",
  	"profit": 73.95,
  	"purchaseDate": "2020-04-01",
  	"saleDate": "2020-09-01"
  }
  ```

- /stock/{symbol}/chart

  ```json
  [
  	{
  		"symbol": "AAPL",
  		"date": "2020-03-26",
  		"open": 61.63,
  		"close": 64.61,
  		"high": 64.67,
  		"low": 61.59,
  		"volume": 252560676
  	},
  	{
  		"symbol": "AAPL",
  		"date": "2020-03-27",
  		"open": 63.19,
  		"close": 61.94,
  		"high": 63.97,
  		"low": 61.76,
  		"volume": 204216612
  },
  ...
  	{
  		"symbol": "AAPL",
  		"date": "2020-09-25",
  		"open": 108.43,
  		"close": 112.28,
  		"high": 112.44,
  		"low": 107.67,
  		"volume": 149981441
  	}
  ]
  ```

<br>

### OpenApi - IexCloud (3rd party) 소개

- [API Reference](https://iexcloud.io/docs/api/#rest-how-to)
  - [주식 symbol 기준으로 과거 주식 정보 조회하기](https://iexcloud.io/docs/api/#historical-prices) 
  - [테스트 요청](https://sandbox.iexapis.com/stable/stock/AAPL/chart/6m?token=Tpk_14ad95bb91954d929f4d657bbcb51d58)
  - [IexCloud Console](https://iexcloud.io/console/)
- 무료 계정의 경우 API 요청 횟수 제약사항이 있습니다. (180일 주식 데이터 요청 기준 약 35회)



<br>

------

## # 학습 내용

### 설계 과정에서 고민하고 고려했던 사항들

1. 주식 정보를 얻기 위한 외부 API 요청에 `RestTemplate`과 `WebClient` 어느 것을 사용하는게 좋을까?
   - 둘의 가장 큰 차이점은 동기 방식과 비동기 방식으로 나뉜다.
   - 대부분의 기능에서 `WebClient`가 유용하고, 스프링에서도 `WebClient` 사용을 권장하고 있다.
   - `WebClient`는 이전에 사용해본 경험이 없어 좋은 기회로 생각하고 `WebClient`를 학습하고 사용했다.
   - 프로젝트에서 사용한 메서드는 단일 요청으로 비동기 처리가 필요하지 않아 `block()`으로 동기처리 하였다. 
   - [RestTemplate과 WebClient 학습정리](https://wooody92.github.io/spring%20boot/Spring-Boot-Spring-REST-Client/)
2. DB를 사용해야 할까?
   - 프로젝트 요구사항을 해결만을 위해서는 DB 사용을 하지 않아도 무방하다.
   - 더 좋은 방식으로 리소스 비용을 아낄 수 있는 방법은 없을까?
   - 요청 받은 주식의 `symbol`과 날짜의 최대 수익 정보를 DB에 저장한다.
   - 다음 요청 시 동일한 `symbol`과 날짜를 요청하면 DB의 데이터로 응답한다.
     - 이를 통해 같은 요청에 대해서 불필요한 외부 API 요청과 최대 수익을 구하는 알고리즘 로직의 리소스 낭비를 줄인다.
   - DB의 경우 프로젝트 과제이기 때문에 인메모리 DB로 H2를 사용했다.
3. 확장성 있는 구조로 설계하자
   - Entity의 경우 Stock과 Profit을 분리하여 1 : N 관계로 설계하였다.
     - Stock은 주식의 `symbol` 을 갖는다.
     - Profit은 최대 수익 알고리즘을 통해 얻은 날짜, 최대수익, 매수일, 매도일(`date`, `profit`, `purchaseDate`, `saleDate`)을 필드값으로 갖는다.
   - 3rd party API 소스를 변경에 용의하도록 설계하였다.
     - `OpenApiProvider` 인터페이스를 생성하고, 상속구조로 `IexCloudProvider`를 구현했다.
     - 외부 API 소스를 변경해도 메인 로직은 수정을 최대한 하지 않는 방향으로 코드를 작성하였다.
4. WebClient로 외부 API 로직을 처리하는 클래스는 DDD에서 어느 계층 구조에 가까울까?
   - 굳이 따지자면 Serviece 계층에 가까운 것 같다.
   - 하지만 의미상 모호한 점이 있어 `Provider`로 이름을 명명하고 따로 패키지화 하여 관리했다.
5. 최대 수익을 구하는 알고리즘의 O(n)의 시간복잡도로 구현하자
   - 주식 가격과 날짜 정보를 입력받아 최대 수익과 매수매도 날짜를 구하도록 구현했다.
   - 주식 가격이 변동이 없거나 계속 하락하면 최대 수익은 0이고, 매수 매도일은 입력된 기간의 첫째 날이다.
   - 알고리즘과 같은 유틸성 클래스는 어느 패키지에 넣어야 할까 고민했다.
6. 나중에 읽어도 편하게 읽히는 클린 코드 및 객체 지향적 설계를 지향하자

<br>

### 프로젝트 간 발생한 애로 사항

1. 해외 주식 정보를 REST API로 공개하는 적당한 웹 사이트를 찾는데 의외로 시간이 걸렸다.

   - 이번 프로젝트에서 사용한 IexCloud의 경우에는 아래 제약사항들이 있었다.
   - 요청 횟수의 제한 - 무료 계정의 경우 180일 주식 데이터 요청 기준 약 35회의 제한이 있었다. (물론 테스트 API 환경은 제공했다.)
   - 특정 날짜 기준으로 180일 이전의 일일 주식 정보를 제공하는 엔드포인트가 없었다, 무조건 오늘 기준으로 조회가 가능했다.

2. 사용자가 `/stock/{symbol}` 요청하는 날짜와 외부 API 응답으로 받아온 주식 정보의 최신 날짜가 다름으로 발생하는 DB 조회 문제

   - 이미 요청받은 최대 수익 정보의 경우 요청 날짜를 기준으로 DB를 조회하는데 시차, 데이터 업로드 시간 등으로 예외 발생하여 아래와 같이 변경했다.

     ```
     - 기존 : LocalDate.now().minusDays(1)을 사용했으나 자정이 지나면 기록된 날짜와 2일 차이로 로직상 에러 발생
     - 변경 : openAPI의 가장 최근 기록 날짜를 가져와 사용하도록 변경
     ```

3. WebClient 사용하여 외부 API 요청시 3rd party에서 발생하는 4xx, 5xx 에러는 어떻게 잡을까?

   - WebClient에서 지원하는 `onStatus() `메서드를 이용해 예외처리 했다.

     ```java
     .retrieve()
     .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new InvalidSymbolException()))
     .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new IexCloudException()))
     ```

<br>

### 이번 프로젝트를 통해 배운 점

- 아무래도 역시 가장 크게 배운 점은 테스트 코드였다.

- 이전에는 무분별하게 `SpringBootTest` 통합 테스트를 남용해서 사용하곤 했었다.

- 이번 프로젝트를 진행하며 `SpringBootTest`와 `WebMvcTest`, `DataJpaTest` 차이를 다시 한번 숙지했다.

- 아직도 많이 부족하지만 `Controller`, `Repository` 각 계층을 단위 테스트 해보고 테스트 커버리지도 늘려보는 좋은 경험이었다. (테스트를 전부 통과하면 왠지 모를 편안함이 생긴다.)

  <img width="1252" alt="test-coverage" src="https://user-images.githubusercontent.com/58318041/94372321-730d1b80-0137-11eb-8190-c3883e75c374.png">
