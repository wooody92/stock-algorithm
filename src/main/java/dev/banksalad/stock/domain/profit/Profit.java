package dev.banksalad.stock.domain.profit;

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
public class Profit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double profit;

    private LocalDate date;

    private LocalDate purchaseDate;

    private LocalDate saleDate;

    @Builder
    public Profit(Double profit, LocalDate date, LocalDate purchaseDate, LocalDate saleDate) {
        this.profit = profit;
        this.date = date;
        this.purchaseDate = purchaseDate;
        this.saleDate = saleDate;
    }
}
