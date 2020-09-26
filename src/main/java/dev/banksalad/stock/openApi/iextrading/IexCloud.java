package dev.banksalad.stock.openApi.iextrading;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IexCloud {

    private LocalDate date;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Long volume;

    @Builder
    public IexCloud(LocalDate date, Double open, Double close, Double high, Double low,
        Long volume) {
        this.date = date;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }
}
