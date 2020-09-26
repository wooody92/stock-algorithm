package dev.banksalad.stock.web.dto.request;

import dev.banksalad.stock.domain.stock.Stock;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateStock {

    private String name;
    private Double profit;
    private LocalDate date;
    private LocalDate buyDate;
    private LocalDate sellDate;

    @Builder
    public CreateStock(String name, Double profit, LocalDate date, LocalDate buyDate,
        LocalDate sellDate) {
        this.name = name;
        this.profit = profit;
        this.date = date;
        this.buyDate = buyDate;
        this.sellDate = sellDate;
    }

    public static Stock toEntity(String symbol) {
        return Stock.builder()
            .name(symbol)
            .build();
    }
}
