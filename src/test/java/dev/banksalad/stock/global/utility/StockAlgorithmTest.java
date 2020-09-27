package dev.banksalad.stock.global.utility;

import static org.junit.Assert.*;

import dev.banksalad.stock.web.dto.create.CreateProfit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class StockAlgorithmTest {

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
        List<Double> priceList = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();

        // when
        CreateProfit[] maxProfitAndDate = new CreateProfit[priceBucket.length];
        for (int i = 0; i < priceBucket.length; i++) {
            for (int j = 0; j < priceBucket[i].length; j++) {
                dates.add(LocalDate.of(2020, 01, 01).plusDays(j));
                priceList.add(priceBucket[i][j]);
            }
            maxProfitAndDate[i] = StockAlgorithm.getMaxProfitAndDate(priceList, dates);
            priceList.clear();
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
}