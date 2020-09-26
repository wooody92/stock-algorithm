package dev.banksalad.stock.domain.stock;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.global.exception.NullProfitException;
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
    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public void addProfit(Profit profit) {
        profits.add(profit);
    }

    public boolean isExistDate() {
        LocalDate date = LocalDate.now().minusDays(1);
        return profits.stream().anyMatch(profit -> profit.isEqualsDate(date));
    }

    public Profit getProfit() {
        LocalDate date = LocalDate.now().minusDays(1);
        for (Profit profit : profits) {
            if (profit.isEqualsDate(date)) {
                return profit;
            }
        }
        throw new NullProfitException();
    }
}
