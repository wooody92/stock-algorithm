package dev.banksalad.stock.service;

import dev.banksalad.stock.iextrading.IexCloud;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IexCloudProvider.class)
public class IexCloudProviderTest {

    @Autowired
    IexCloudProvider iexCloudProvider;

    @MockBean
    WebClient.Builder builder;

    private final String stockSymbol = "AAPL";

    @Test
    @DisplayName("IexCloud 리스트를 StockInformationDto 리스트로 변환하는 테스트")
    public void getStockDataTest() {
        // given
        List<IexCloud> iexClouds = new ArrayList<>();
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
        iexClouds.add(iexCloud1);
        iexClouds.add(iexCloud2);

        // when
        List<StockInformationDto> stockInformationDtos = iexCloudProvider
            .getStockData(stockSymbol, iexClouds);
        StockInformationDto stockInformationDto = StockInformationDto.of(stockSymbol, iexCloud1);

        // then
        assertThat(stockInformationDtos).isNotEmpty();
        assertThat(stockInformationDtos.size()).isEqualTo(2);
        assertThat(stockInformationDtos.get(0)).isInstanceOf(StockInformationDto.class);
        assertThat(stockInformationDtos.get(0).getClose())
            .isEqualTo(stockInformationDto.getClose());
    }

    @Test
    public void requestDataTest() {
    }
}