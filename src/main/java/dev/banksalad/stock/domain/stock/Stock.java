package dev.banksalad.stock.domain.stock;

import dev.banksalad.stock.global.error.exception.NullProfitException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    private String symbol;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profit> profits = new ArrayList<>();

    @Builder
    protected Stock(String symbol) {
        this.symbol = symbol;
    }

    public void addProfit(Profit profit) {
        profits.add(profit);
    }

    public boolean isExistDate(LocalDate date) {
        return profits.stream().anyMatch(profit -> profit.isEqualsDate(date));
    }

    public Profit getProfit(LocalDate date) {
        return profits.stream()
            .filter(profit -> profit.isEqualsDate(date))
            .findFirst()
            .orElseThrow(NullProfitException::new);
    }
}
