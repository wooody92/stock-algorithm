package dev.banksalad.stock.web.dto;

import dev.banksalad.stock.domain.stock.Profit;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.text.DecimalFormat;
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

    /**
     * 가격 변동이 없거나, 계속 떨어지기만 한다면 maxProfit = 0.0 입니다.
     * 해당 경우 purchaseDate, saleDate는 dates의 처음 날짜로 반환 합니다.
     */
    public Profit getMaxProfitAndDate() {
        List<Double> price = parsePrice(stockInformationDtos);
        List<LocalDate> dates = parseDate(stockInformationDtos);
        double maxProfit = 0;
        double minPrice = price.get(0);
        int purchaseIndex = 0;
        int saleIndex = 0;

        for (int i = 1; i < price.size(); i++) {
            Double profit = price.get(i) - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
                saleIndex = i;
            }
            if (price.get(i) < minPrice) {
                minPrice = price.get(i);
            }
        }
        for (int i = 0; i < saleIndex; i++) {
            if (price.get(saleIndex) - price.get(i) == maxProfit) {
                purchaseIndex = i;
                break;
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
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String format = decimalFormat.format(digit);
        return Double.parseDouble(format);
    }

    private List<Double> parsePrice(List<StockInformationDto> stockInformation) {
        return stockInformation.stream()
            .map(data -> new Double(data.getClose()))
            .collect(Collectors.toList());
    }

    private List<LocalDate> parseDate(List<StockInformationDto> stockInformation) {
        List<LocalDate> dates = new ArrayList<>();
        for (StockInformationDto data : stockInformation) {
            dates.add(data.getDate());
        }
        return dates;
    }

    public List<LocalDate> getDate() {
        return parseDate(stockInformationDtos);
    }
}
