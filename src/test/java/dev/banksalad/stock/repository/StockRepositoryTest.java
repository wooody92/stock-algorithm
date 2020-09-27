package dev.banksalad.stock.repository;

import static org.junit.Assert.*;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.domain.stock.Stock;
import dev.banksalad.stock.web.dto.create.CreateProfit;
import dev.banksalad.stock.web.dto.create.CreateStock;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StockRepositoryTest {

    @Autowired
    StockRepository stockRepository;

    private final String STOCK_SYMBOL = "AAPL";
    private final String NON_EXISTING = "NON_EXISTING";

    @Test
    @DisplayName("Stock 저장하는 테스트")
    public void saveStockTest() {
        // given
        Stock stock = CreateStock.toEntity(STOCK_SYMBOL);

        // when
        Stock newStock = stockRepository.save(stock);

        // then
        assertThat(newStock).isNotNull();
        assertThat(newStock.getSymbol()).isEqualTo(STOCK_SYMBOL);
    }

    @Test
    @DisplayName("Stock을 symbol로 조회하는 테스트")
    public void findStockBySymbolTest() {
        // given
        Stock stock = CreateStock.toEntity(STOCK_SYMBOL);
        Stock newStock = stockRepository.save(stock);

        // when
        Stock existingStock = stockRepository.findBySymbol(STOCK_SYMBOL);
        Stock nonExistingStock = stockRepository.findBySymbol(NON_EXISTING);

        // then
        assertThat(newStock).isNotNull();
        assertThat(existingStock).isNotNull();
        assertThat(existingStock.getSymbol()).isEqualTo(STOCK_SYMBOL);
        assertThat(nonExistingStock).isNull();
    }

    @Test
    @DisplayName("Stock에 Profit 추가하고 저장하는 테스트")
    public void addProfitTest() {
        // given
        Stock stock = CreateStock.toEntity(STOCK_SYMBOL);
        CreateProfit createProfit = CreateProfit.builder()
            .date(LocalDate.parse("2020-09-24"))
            .profit(108.22)
            .purchaseDate(LocalDate.parse("2020-09-23"))
            .saleDate(LocalDate.parse("2020-09-24"))
            .build();
        Profit profit = CreateProfit.toEntity(createProfit, stock);

        // when
        stock.addProfit(profit);
        Stock newStock = stockRepository.save(stock);
        List<Profit> profits = newStock.getProfits();

        // then
        assertThat(newStock).isNotNull();
        assertThat(profits).isNotEmpty();
        assertThat(profits.get(0)).isEqualTo(profit);
    }
}