package dev.banksalad.stock.service;

import static org.assertj.core.api.Assertions.*;

import dev.banksalad.stock.domain.stock.Profit;
import dev.banksalad.stock.domain.stock.Stock;
import dev.banksalad.stock.repository.StockRepository;
import dev.banksalad.stock.web.dto.StockInformation;
import dev.banksalad.stock.web.dto.create.StockFactory;
import dev.banksalad.stock.web.dto.response.StockProfitResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    private final String STOCK_SYMBOL = "AAPL";

    @Test
    @Transactional
    @DisplayName("이미 요청했던 날짜와 심볼을 다시 요청했을 때 DB에 기록된 데이터로 반환하는지 확인하는 테스트")
    public void getMaxProfitDateTest_DB() {
        //given
        Stock stock = StockFactory.toEntity(STOCK_SYMBOL);
        LocalDate date = stockService.getLatestRecordDate(STOCK_SYMBOL);
        Profit profit = Profit.builder()
            .date(date)
            .profit(108.22)
            .purchaseDate(LocalDate.parse("2020-09-23"))
            .saleDate(LocalDate.parse("2020-09-24"))
            .stock(stock)
            .build();
        stock.addProfit(profit);
        stockRepository.save(stock);

        //when
        StockProfitResponse maxProfitDate = stockService.getMaxProfitDate(STOCK_SYMBOL);

        //then
        assertThat(maxProfitDate).isNotNull();
        assertThat(maxProfitDate.getSymbol()).isEqualTo(STOCK_SYMBOL);
        assertThat(maxProfitDate.getDate()).isEqualTo(date);
        assertThat(maxProfitDate.getProfit()).isEqualTo(108.22);
        assertThat(maxProfitDate.getPurchaseDate()).isEqualTo(LocalDate.parse("2020-09-23"));
        assertThat(maxProfitDate.getSaleDate()).isEqualTo(LocalDate.parse("2020-09-24"));
    }

    @Test
    @Transactional
    @DisplayName("외부 API 요청 후 최대 수익과 날짜를 정상적으로 반환하는지 확인하는 테스트")
    public void getMaxProfitDateTest() {
        // given
        StockProfitResponse maxProfitDate = stockService.getMaxProfitDate(STOCK_SYMBOL);
        StockInformation stockInformation = StockInformation
            .of(stockService.getStockInformation(STOCK_SYMBOL));

        // when
        List<LocalDate> date = stockInformation.getDate();
        LocalDate firstDate = date.get(0);
        LocalDate lastDate = date.get(date.size() - 1);

        //then
        assertThat(maxProfitDate).isNotNull();
        assertThat(maxProfitDate.getSymbol()).isEqualTo(STOCK_SYMBOL);
        assertThat(maxProfitDate.getDate()).isEqualTo(lastDate);
        assertThat(maxProfitDate.getPurchaseDate()).isBetween(firstDate, lastDate);
        assertThat(maxProfitDate.getSaleDate())
            .isBetween(maxProfitDate.getPurchaseDate(), lastDate);
    }
}