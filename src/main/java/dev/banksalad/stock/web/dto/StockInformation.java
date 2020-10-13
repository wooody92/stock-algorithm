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
public class StockInformation {

    private List<StockInformationDto> stockInformationDtos;

    @Builder
    protected StockInformation(List<StockInformationDto> stockInformationDtos) {
        this.stockInformationDtos = stockInformationDtos;
    }

    public static StockInformation of(List<StockInformationDto> stockInformationDtos) {
        return StockInformation.builder()
            .stockInformationDtos(stockInformationDtos)
            .build();
    }

    public List<Double> getPrices() {
        return parsePrice(this.stockInformationDtos);
    }

    public List<LocalDate> getDates() {
        return parseDate(this.stockInformationDtos);
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
