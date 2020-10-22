package dev.banksalad.stock.service;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.domain.stock.Stock;
import dev.banksalad.stock.openApi.OpenApiProvider;
import dev.banksalad.stock.openApi.iextrading.IexCloud;
import dev.banksalad.stock.repository.StockRepository;
import dev.banksalad.stock.web.dto.StockInformation;
import dev.banksalad.stock.web.dto.create.CreateStock;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import dev.banksalad.stock.web.dto.response.StockProfitResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    @Qualifier("iexCloudProvider")
    private final OpenApiProvider openApiProvider;

    private final StockRepository stockRepository;

    @Transactional
    public StockProfitResponse getMaxProfitDate(String symbol) {
        // DB check
        Stock stock = stockRepository.findBySymbol(symbol).orElse(CreateStock.toEntity(symbol));
        LocalDate date = getLatestRecordDate(symbol);
        if (stock.isExistDate(date)) {
            Profit profit = stock.getProfit(date);
            return StockProfitResponse.of(stock, profit);
        }

        // request API
        StockInformation stockInformation = StockInformation.of(getStockInformation(symbol));
        Profit profit = stockInformation.getMaxProfitAndDate();
        stock.addProfit(profit);
        stockRepository.save(stock);
        return StockProfitResponse.of(stock, profit);
    }

    public List<StockInformationDto> getStockInformation(String symbol) {
        List<IexCloud> iexClouds = openApiProvider.requestData(symbol);
        return openApiProvider.getStockData(symbol, iexClouds);
    }

    public LocalDate getLatestRecordDate(String symbol) {
        return openApiProvider.getLatestRecordDate(symbol);
    }
}
