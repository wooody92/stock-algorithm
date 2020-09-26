package dev.banksalad.stock.web.dto.response;

import dev.banksalad.stock.openApi.iextrading.IexCloud;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockInformationDto {

    private String symbol;
    private LocalDate date;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Long volume;

    @Builder
    public StockInformationDto(String symbol, LocalDate date, Double open, Double close,
        Double high, Double low, Long volume) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public static StockInformationDto of(String symbol, IexCloud iexCloud) {
        return StockInformationDto.builder()
            .symbol(symbol)
            .date(iexCloud.getDate())
            .open(iexCloud.getOpen())
            .close(iexCloud.getClose())
            .high(iexCloud.getHigh())
            .low(iexCloud.getLow())
            .volume(iexCloud.getVolume())
            .build();
    }

    @Override
    public String toString() {
        return "{" +
            "\"symbol\":" + symbol +
            ",\"date\":" + date +
            ",\"open\":" + open +
            ",\"close\":" + close +
            ",\"high\":" + high +
            ",\"low\":" + low +
            ",\"volume\":" + volume +
            '}';
    }
}
