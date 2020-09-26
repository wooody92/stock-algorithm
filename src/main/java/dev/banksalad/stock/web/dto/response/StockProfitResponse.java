package dev.banksalad.stock.web.dto.response;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.domain.stock.Stock;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockProfitResponse {

    private String symbol;
    private LocalDate date;
    private Double profit;
    private LocalDate purchaseDate;
    private LocalDate saleDate;

    @Builder
    public StockProfitResponse(String symbol, LocalDate date, Double profit,
        LocalDate purchaseDate, LocalDate saleDate) {
        this.symbol = symbol;
        this.date = date;
        this.profit = profit;
        this.purchaseDate = purchaseDate;
        this.saleDate = saleDate;
    }

    public static StockProfitResponse of(Stock stock) {
        Profit profit = stock.getProfit();
        return StockProfitResponse.builder()
            .symbol(stock.getSymbol())
            .date(profit.getDate())
            .profit(profit.getProfit())
            .purchaseDate(profit.getPurchaseDate())
            .saleDate(profit.getSaleDate())
            .build();
    }
}
