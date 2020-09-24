package dev.banksalad.stock.web.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import dev.banksalad.stock.iextrading.IexCloud;
import dev.banksalad.stock.service.IexCloudService;
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
    IexCloudService iexCloudService;

    private List<IexCloud> iexClouds = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        IexCloud iexCloud1 = new IexCloud("2020-09-21", 114.84, 204493929l);
        IexCloud iexCloud2 = new IexCloud("2020-09-22", 112.87, 189436014l);
        iexClouds.add(iexCloud1);
        iexClouds.add(iexCloud2);
    }

    @Test
    @DisplayName("주식 심볼을 바탕으로 해당 주식의 지난 데이터들을 조회하는 테스트")
    public void viewStockChartTest() throws Exception {
        final String stockSymbol = "AAPL";

        given(iexCloudService.getData(stockSymbol)).willReturn(iexClouds);

        mockMvc.perform(get("/stock/" + stockSymbol))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().json(iexClouds.toString()));
    }
}