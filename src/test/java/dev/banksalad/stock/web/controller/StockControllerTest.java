package dev.banksalad.stock.web.controller;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.domain.stock.Stock;
import dev.banksalad.stock.openApi.iextrading.IexCloud;
import dev.banksalad.stock.service.StockService;
import dev.banksalad.stock.web.dto.create.CreateProfit;
import dev.banksalad.stock.web.dto.create.CreateStock;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import dev.banksalad.stock.web.dto.response.StockProfitResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StockService stockService;

    private List<StockInformationDto> stockInformationDtos = new ArrayList<>();
    private final String stockSymbol = "AAPL";

    @Before
    public void setUp() throws Exception {
        IexCloud iexCloud1 = IexCloud.builder()
            .date(LocalDate.parse("2020-09-23"))
            .open(111.62)
            .close(107.12)
            .high(112.11)
            .low(106.77)
            .volume(150718671L)
            .build();
        IexCloud iexCloud2 = IexCloud.builder()
            .date(LocalDate.parse("2020-09-24"))
            .open(105.17)
            .close(108.22)
            .high(110.25)
            .low(105.0)
            .volume(167743349L)
            .build();

        stockInformationDtos.add(StockInformationDto.of(stockSymbol, iexCloud1));
        stockInformationDtos.add(StockInformationDto.of(stockSymbol, iexCloud2));
    }

    @Test
    @DisplayName("주식 심볼을 바탕으로 해당 주식의 지난 데이터들을 조회하는 테스트")
    public void viewStockChartTest() throws Exception {
        // given, when
        when(stockService.getStockInformation(stockSymbol)).thenReturn(stockInformationDtos);

        // then
        mockMvc.perform(get("/stock/" + stockSymbol + "/chart"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().json(stockInformationDtos.toString()));
    }

    @Test
    @DisplayName("주식 정보를 바탕으로 최대 수익을 내는 날짜 정보를 조회하는 테스트")
    public void viewMaxProfitTest() throws Exception {
        //given
        Stock stock = CreateStock.toEntity(stockSymbol);
        CreateProfit createProfit = CreateProfit.builder()
            .date(LocalDate.parse("2020-09-24"))
            .profit(108.22)
            .purchaseDate(LocalDate.parse("2020-09-23"))
            .saleDate(LocalDate.parse("2020-09-24"))
            .build();
        Profit profit = CreateProfit.toEntity(createProfit, stock);
        StockProfitResponse stockProfitResponse = StockProfitResponse.of(stock, profit);

        //when
        when(stockService.getMaxProfitDate(stockSymbol)).thenReturn(stockProfitResponse);

        //then
        mockMvc.perform(get("/stock/" + stockSymbol))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().json(stockProfitResponse.toString()));
    }
}