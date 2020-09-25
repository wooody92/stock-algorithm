package dev.banksalad.stock.domain.stock;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double profit;

    private LocalDate date;

    private LocalDate buyDate;

    private LocalDate sellDate;

    @Builder
    public Stock(String name, Double profit, LocalDate date, LocalDate buyDate,
        LocalDate sellDate) {
        this.name = name;
        this.profit = profit;
        this.date = date;
        this.buyDate = buyDate;
        this.sellDate = sellDate;
    }
}
