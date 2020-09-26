package dev.banksalad.stock.utils;

import dev.banksalad.stock.web.dto.PriceAndDate;
import dev.banksalad.stock.web.dto.request.CreateProfit;
import java.time.LocalDate;
import java.util.List;

public class StockAlgorithm {

    public static CreateProfit getMaxProfitAndDate(List<Double> price, List<LocalDate> dates) {
        double maxProfit = 0;
        double minPrice = price.get(0);
        int purchaseIndex = -1;
        int saleIndex = -1;

        for (int i = 1; i < price.size(); i++) {
            Double profit = price.get(i) - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
                saleIndex = i;
            }
            if (price.get(i) < minPrice) {
                minPrice = price.get(i);
            }
        }
        for (int i = 0; i < saleIndex; i++) {
            if (price.get(saleIndex) - price.get(i) == maxProfit) {
                purchaseIndex = i;
                break;
            }
        }
        return CreateProfit.builder()
            .profit(maxProfit)
            .date(dates.get(dates.size() - 1))
            .purchaseDate(dates.get(purchaseIndex))
            .saleDate(dates.get(saleIndex))
            .build();
    }
}
