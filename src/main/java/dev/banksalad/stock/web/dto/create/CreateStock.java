package dev.banksalad.stock.web.dto.create;

import dev.banksalad.stock.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
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
