package dev.banksalad.stock.utils;

import dev.banksalad.stock.web.dto.PriceAndDate;
import dev.banksalad.stock.web.dto.request.CreateProfit;
import java.time.LocalDate;
import java.util.List;

public class StockAlgorithm {

    public static CreateProfit getMaxProfitAndDate2(List<Double> price, List<LocalDate> dates) {
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

    public static CreateProfit getMaxProfitAndDate(List<PriceAndDate> priceAndDates) {
        double maxProfit = 0;
        double minPrice = priceAndDates.get(0).getPrice();
        int purchaseIndex = -1;
        int saleIndex = -1;

        for (int i = 1; i < priceAndDates.size(); i++) {
            Double profit = priceAndDates.get(i).getPrice() - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
                saleIndex = i;
            }
            if (priceAndDates.get(i).getPrice() < minPrice) {
                minPrice = priceAndDates.get(i).getPrice();
            }
        }
        for (int i = 0; i < saleIndex; i++) {
            if (priceAndDates.get(saleIndex).getPrice() - priceAndDates.get(i).getPrice()
                == maxProfit) {
                purchaseIndex = i;
                break;
            }
        }
        return CreateProfit.builder()
            .profit(maxProfit)
            .date(priceAndDates.get(priceAndDates.size() - 1).getDate())
            .purchaseDate(priceAndDates.get(purchaseIndex).getDate())
            .saleDate(priceAndDates.get(saleIndex).getDate())
            .build();
    }
}
