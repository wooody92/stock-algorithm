package dev.banksalad.stock.web.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PriceAndDate {

    private Double price;
    private LocalDate date;

    @Builder
    public PriceAndDate(Double price, LocalDate date) {
        this.price = price;
        this.date = date;
    }

    public static PriceAndDate of(Double price, LocalDate date) {
        return PriceAndDate.builder()
            .price(price)
            .date(date)
            .build();
    }
}
