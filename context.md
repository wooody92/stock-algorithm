```
in the language of your choice, wirte a web service that takes in an US stock symbol(e.g. APPL, GOOG) and returns a single buy and sell date that yields the max profit over the last 180 days. you may use any third-party api to retrieve historical stock prices. please include a readme describing your web service and appropriate documentation.

선택한 언어로 미국 주식 기호를 받아 지난 180 일 동안 최대 수익을내는 단일 매수 및 매도 날짜를 반환하는 웹 서비스를 작성합니다. 타사 API를 사용하여 과거 주가를 검색 할 수 있습니다. 웹 서비스 및 적절한 문서를 설명하는 추가 정보를 포함합니다.

-> 입력 : US stock symbol(APPL, GOOG, ..)
-> 오늘 기준 이전 180일 동안 해당 종목 금액 조회 
-> 매수 : 최저가인 날짜 검색
-> 매도 : 최고가인 날짜 검색
-> 조건 : 매수 날짜는 매도 날짜보다 전일이어야 한다. 

* Design
- provide clear abstractions between the api, business, and data layer.
- the third party data source should be easily interchangeable.
- components are reusable/testable.

* 디자인
-API, 비즈니스 및 데이터 계층간에 명확한 추상화를 제공합니다.
-타사 데이터 소스는 쉽게 교체 할 수 있어야합니다.
-구성 요소는 재사용 / 테스트 가능합니다.

* Testing
- the profit calculation is thoroughly tested(consider the different ways price history can vary)
- other components are reasonably tested
- submissions without test will be rejected.

* 테스트
-수익 계산은 철저하게 테스트됩니다 (가격 내역이 다를 수있는 다른 방식 고려)
-다른 구성 요소는 합리적으로 테스트됩니다
-테스트가 없는 제출은 거절됩니다.
```

