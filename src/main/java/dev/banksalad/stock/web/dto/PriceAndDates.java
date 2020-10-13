package dev.banksalad.stock.web.dto;

import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceAndDates {

    private List<Double> price;
    private List<LocalDate> dates;

    @Builder
    public PriceAndDates(List<Double> price, List<LocalDate> dates) {
        this.price = price;
        this.dates = dates;
    }

    public static PriceAndDates of(List<StockInformationDto> stockInformation) {
        return PriceAndDates.builder()
            .price(parsePrice(stockInformation))
            .dates(parseDate(stockInformation))
            .build();
    }

    private static List<Double> parsePrice(List<StockInformationDto> stockInformation) {
        return stockInformation.stream()
            .map(data -> new Double(data.getClose()))
            .collect(Collectors.toList());
    }

    private static List<LocalDate> parseDate(List<StockInformationDto> stockInformation) {
        List<LocalDate> dates = new ArrayList<>();
        for (StockInformationDto data : stockInformation) {
            dates.add(data.getDate());
        }
        return dates;
    }
}
