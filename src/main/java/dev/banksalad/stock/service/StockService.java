package dev.banksalad.stock.service;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.domain.stock.Stock;
import dev.banksalad.stock.openApi.iextrading.IexCloud;
import dev.banksalad.stock.openApi.iextrading.IexCloudProvider;
import dev.banksalad.stock.repository.StockRepository;
import dev.banksalad.stock.utils.StockAlgorithm;
import dev.banksalad.stock.web.dto.PriceAndDate;
import dev.banksalad.stock.web.dto.request.CreateProfit;
import dev.banksalad.stock.web.dto.request.CreateStock;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final IexCloudProvider iexCloudProvider;

    private final StockRepository stockRepository;

    public List<StockInformationDto> getStockInformation(String symbol) {
        List<IexCloud> iexClouds = iexCloudProvider.requestData(symbol);
        return iexCloudProvider.getStockData(symbol, iexClouds);
    }

    public void getMaxProfitDate(String symbol) {
        List<StockInformationDto> stockInformation = getStockInformation(symbol);
        List<Double> price = parsePrice(stockInformation);
        List<LocalDate> dates = parseDate(stockInformation);

        Stock stock = CreateStock.toEntity(symbol);
        CreateProfit createProfit = StockAlgorithm.getMaxProfitAndDate(price, dates);
        Profit profit = CreateProfit.toEntity(createProfit, stock);
        stock.addProfit(profit);

        stockRepository.save(stock);
        System.out.println(profit);
    }

    private List<Double> parsePrice(List<StockInformationDto> stockInformation) {
        return stockInformation.stream()
            .map(data -> new Double(data.getClose()))
            .collect(Collectors.toList());
    }

    private List<LocalDate> parseDate(List<StockInformationDto> stockInformation) {
        List<LocalDate> dates = new ArrayList<>();
        for (StockInformationDto data : stockInformation) {
            dates.add(data.getDate());
        }
        return dates;
    }
}
