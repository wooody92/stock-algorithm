package dev.banksalad.stock.web.controller;

import static org.hamcrest.Matchers.is;

import dev.banksalad.stock.iextrading.IexCloud;
import dev.banksalad.stock.service.StockService;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
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

import static org.mockito.BDDMockito.given;
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
        given(stockService.getStockInformation(stockSymbol)).willReturn(stockInformationDtos);

        mockMvc.perform(get("/stock/" + stockSymbol))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().json(stockInformationDtos.toString()));
    }
}