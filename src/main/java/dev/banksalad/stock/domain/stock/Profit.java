package dev.banksalad.stock.domain.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.banksalad.stock.domain.stock.Stock;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "stock")
@NoArgsConstructor
public class Profit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double profit;

    private LocalDate purchaseDate;

    private LocalDate saleDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Builder
    protected Profit(LocalDate date, Double profit, LocalDate purchaseDate, LocalDate saleDate,
        Stock stock) {
        this.date = date;
        this.profit = profit;
        this.purchaseDate = purchaseDate;
        this.saleDate = saleDate;
        this.stock = stock;
    }

    public boolean isEqualsDate(LocalDate date) {
        return date.equals(this.date);
    }
}
