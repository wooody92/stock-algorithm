package dev.banksalad.stock.service;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.domain.stock.Stock;
import dev.banksalad.stock.openApi.iextrading.IexCloud;
import dev.banksalad.stock.openApi.iextrading.IexCloudProvider;
import dev.banksalad.stock.repository.StockRepository;
import dev.banksalad.stock.global.utility.StockAlgorithm;
import dev.banksalad.stock.web.dto.create.CreateProfit;
import dev.banksalad.stock.web.dto.create.CreateStock;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import dev.banksalad.stock.web.dto.response.StockProfitResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final IexCloudProvider iexCloudProvider;

    private final StockRepository stockRepository;

    @Transactional
    public StockProfitResponse getMaxProfitDate(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol);
        LocalDate date = getLatestRecordDate(symbol);
        if (Objects.isNull(stock)) {
            stock = CreateStock.toEntity(symbol);
        }
        if (stock.isExistDate(date)) {
            Profit profit = stock.getProfit(date);
            return StockProfitResponse.of(stock, profit);
        }
        List<StockInformationDto> stockInformation = getStockInformation(symbol);
        List<Double> price = parsePrice(stockInformation);
        List<LocalDate> dates = parseDate(stockInformation);
        CreateProfit createProfit = StockAlgorithm.getMaxProfitAndDate(price, dates);

        Profit profit = CreateProfit.toEntity(createProfit, stock);
        stock.addProfit(profit);
        stockRepository.save(stock);
        return StockProfitResponse.of(stock, profit);
    }

    public List<StockInformationDto> getStockInformation(String symbol) {
        List<IexCloud> iexClouds = iexCloudProvider.requestData(symbol);
        return iexCloudProvider.getStockData(symbol, iexClouds);
    }

    public LocalDate getLatestRecordDate(String symbol) {
        return iexCloudProvider.getLatestRecordDate(symbol);
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
