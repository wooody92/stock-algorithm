package dev.banksalad.stock.web.dto.request;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.domain.stock.Stock;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateProfit {

    private Double profit;
    private LocalDate date;
    private LocalDate purchaseDate;
    private LocalDate saleDate;

    @Builder
    public CreateProfit(Double profit, LocalDate date, LocalDate purchaseDate,
        LocalDate saleDate) {
        this.profit = profit;
        this.date = date;
        this.purchaseDate = purchaseDate;
        this.saleDate = saleDate;
    }

    public static Profit toEntity(CreateProfit createProfit, Stock stock) {
        return Profit.builder()
            .date(createProfit.getDate())
            .profit(createProfit.getProfit())
            .purchaseDate(createProfit.getPurchaseDate())
            .saleDate(createProfit.getSaleDate())
            .stock(stock)
            .build();
    }
}
