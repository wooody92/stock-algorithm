package dev.banksalad.stock.web.dto;

import static org.junit.jupiter.api.Assertions.assertAll;

import dev.banksalad.stock.domain.stock.Profit;
import dev.banksalad.stock.openApi.iextrading.IexCloud;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class StockInformationTest {

    private final String STOCK_SYMBOL = "AAPL";

    /**
     * dates = {2020-01-01, 2020-01-02, 2020-01-03, 2020-01-04, 2020-01-05, 2020-01-06, 2020-01-07,
     * 2020-01-08, 2020-01-09, 2020-01-10}
     */
    @Test
    @DisplayName("주식 가격과 날짜 리스트를 받아 최대 수익을 내는 매수, 매도 날짜와 수익을 구하는 로직 테스트")
    public void getMaxProfitAndDateTest() {
        //given
        Double[][] priceBucket = {
            {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {10.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0},
            {2.0, 5.0, 3.0, 8.0, 1.0, 4.0, 7.0, 10.0, 9.0, 6.0},
            {128.31, 128.28, 128.24, 132.66, 134.66, 134.2, 123.52, 125.18, 114.78, 118.14}
        };
        List<LocalDate> dates = new ArrayList<>();
        List<StockInformationDto> stockInformationDtos = new ArrayList<>();

        // when
        Profit[] maxProfitAndDate = new Profit[priceBucket.length];
        for (int i = 0; i < priceBucket.length; i++) {
            for (int j = 0; j < priceBucket[i].length; j++) {
                IexCloud iexCloud = IexCloud.builder()
                    .close(priceBucket[i][j])
                    .date(LocalDate.of(2020, 01, 01).plusDays(j))
                    .build();
                StockInformationDto stockInformationDto = StockInformationDto.of(STOCK_SYMBOL, iexCloud);
                stockInformationDtos.add(stockInformationDto);
            }
            StockInformation stockInformation = StockInformation.of(stockInformationDtos);
            maxProfitAndDate[i] = stockInformation.getMaxProfitAndDate();
            dates.addAll(stockInformation.getDate());
            stockInformationDtos.clear();
        }

        // then
        assertAll(
            () -> assertThat(maxProfitAndDate[0].getProfit()).isEqualTo(9.0),
            () -> assertThat(maxProfitAndDate[0].getDate()).isEqualTo(dates.get(dates.size() - 1)),
            () -> assertThat(maxProfitAndDate[0].getPurchaseDate())
                .isEqualTo(LocalDate.parse("2020-01-01")),
            () -> assertThat(maxProfitAndDate[0].getSaleDate()).isEqualTo(LocalDate.parse("2020-01-10"))
        );

        assertAll(
            () -> assertThat(maxProfitAndDate[1].getProfit()).isEqualTo(0.0),
            () -> assertThat(maxProfitAndDate[1].getDate()).isEqualTo(dates.get(dates.size() - 1)),
            () -> assertThat(maxProfitAndDate[1].getPurchaseDate())
                .isEqualTo(LocalDate.parse("2020-01-01")),
            () -> assertThat(maxProfitAndDate[1].getSaleDate()).isEqualTo(LocalDate.parse("2020-01-01"))
        );

        assertAll(
            () -> assertThat(maxProfitAndDate[2].getProfit()).isEqualTo(0.0),
            () -> assertThat(maxProfitAndDate[2].getDate()).isEqualTo(dates.get(dates.size() - 1)),
            () -> assertThat(maxProfitAndDate[2].getPurchaseDate())
                .isEqualTo(LocalDate.parse("2020-01-01")),
            () -> assertThat(maxProfitAndDate[2].getSaleDate()).isEqualTo(LocalDate.parse("2020-01-01"))
        );

        assertAll(
            () -> assertThat(maxProfitAndDate[3].getProfit()).isEqualTo(9.0),
            () -> assertThat(maxProfitAndDate[3].getDate()).isEqualTo(dates.get(dates.size() - 1)),
            () -> assertThat(maxProfitAndDate[3].getPurchaseDate())
                .isEqualTo(LocalDate.parse("2020-01-05")),
            () -> assertThat(maxProfitAndDate[3].getSaleDate()).isEqualTo(LocalDate.parse("2020-01-08"))
        );

        assertAll(
            () -> assertThat(maxProfitAndDate[4].getProfit()).isEqualTo(6.42),
            () -> assertThat(maxProfitAndDate[4].getDate()).isEqualTo(dates.get(dates.size() - 1)),
            () -> assertThat(maxProfitAndDate[4].getPurchaseDate())
                .isEqualTo(LocalDate.parse("2020-01-03")),
            () -> assertThat(maxProfitAndDate[4].getSaleDate()).isEqualTo(LocalDate.parse("2020-01-05"))
        );
    }

    @Test
    @RepeatedTest(50)
    @DisplayName("최대 수익과 최대 수익을 내는 매수, 매도일을 서로 다른 알고리즘 비교를 통해 검증하는 테스트")
    public void improvedGetMaxProfitAndDateTest() {
        //given
        final int DATA_LENGTH = 100;
        List<StockInformationDto> info = new ArrayList<>();
        for (int i = 0; i < DATA_LENGTH; i++) {
            LocalDate date = LocalDate.now().minusDays(DATA_LENGTH).plusDays(i);
            Double price = getSecondDigit(Math.random() * 100);
            info.add(StockInformationDto.builder()
                .date(date)
                .close(price)
                .build());
        }
        StockInformation stockInformation = StockInformation.of(info);

        //when
        Profit profit = stockInformation.getMaxProfitAndDate();
        Profit profitByTestAlgorithm = maxProfitAlgorithm(stockInformation);

        //then
        assertThat(profit.getProfit()).isEqualTo(profitByTestAlgorithm.getProfit());
        assertThat(profit.getDate()).isEqualTo(profitByTestAlgorithm.getDate());
        assertThat(profit.getPurchaseDate()).isEqualTo(profitByTestAlgorithm.getPurchaseDate());
        assertThat(profit.getSaleDate()).isEqualTo(profitByTestAlgorithm.getSaleDate());
    }

    /**
     * 테스트 검증을 위한 비교 알고리즘
     * 시간 복잡도: O(N^2)
     */
    private Profit maxProfitAlgorithm(StockInformation stockInformation) {
        List<Double> price = stockInformation.getPrice();
        List<LocalDate> dates = stockInformation.getDate();
        double maxProfit = 0;
        int purchaseIndex = 0;
        int saleIndex = 0;

        for (int i = 0; i < price.size() - 1; i++) {
            for (int j = i + 1; j < price.size(); j++) {
                double profit = price.get(j) - price.get(i);
                if (profit > maxProfit) {
                    purchaseIndex = i;
                    saleIndex = j;
                    maxProfit = profit;
                }
            }
        }
        return Profit.builder()
            .date(dates.get(dates.size() - 1))
            .profit(getSecondDigit(maxProfit))
            .purchaseDate(dates.get(purchaseIndex))
            .saleDate(dates.get(saleIndex))
            .build();
    }

    private Double getSecondDigit(Double digit) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String format = decimalFormat.format(digit);
        return Double.parseDouble(format);
    }
}