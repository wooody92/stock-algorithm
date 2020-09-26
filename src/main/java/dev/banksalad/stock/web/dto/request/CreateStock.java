package dev.banksalad.stock.web.dto.request;

import dev.banksalad.stock.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateStock {

    private String symbol;

    @Builder
    public CreateStock(String symbol) {
        this.symbol = symbol;
    }

    public static Stock toEntity(String symbol) {
        return Stock.builder()
            .symbol(symbol)
            .build();
    }
}
