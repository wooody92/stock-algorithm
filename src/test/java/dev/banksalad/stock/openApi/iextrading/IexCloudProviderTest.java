package dev.banksalad.stock.openApi.iextrading;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IexCloudProviderTest {

    @Autowired
    IexCloudProvider iexCloudProvider;

    private final String STOCK_SYMBOL = "AAPL";

    @Test
    @DisplayName("외부 API 데이터를 정상적으로 받아오는지 확인하는 테스트")
    public void requestDataTest() throws Exception {
        // given, when
        List<IexCloud> iexClouds = iexCloudProvider.requestData(STOCK_SYMBOL);

        // then
        assertThat(iexClouds).isNotEmpty();
        assertThat(iexClouds.get(0).getDate()).isNotNull();
        assertThat(iexClouds.get(0).getClose()).isNotNull();
        assertThat(iexClouds.get(iexClouds.size() - 1).getDate()).isNotNull();
        assertThat(iexClouds.get(iexClouds.size() - 1).getClose()).isNotNull();
    }

    @Test
    @DisplayName("외부 API 데이터의 최신 날짜가 한국 시간 오늘 기준으로 5일 이내인지 확인하는 테스트")
    public void getLatestRecordDateTest() {
        //given, when
        LocalDate latestRecordDate = iexCloudProvider.getLatestRecordDate(STOCK_SYMBOL);

        //then
        assertThat(latestRecordDate).isNotNull();
        assertThat(latestRecordDate)
            .isBetween(LocalDate.now().minusDays(5), LocalDate.now());
    }
}
