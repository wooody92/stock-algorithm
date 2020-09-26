package dev.banksalad.stock.service;

import dev.banksalad.stock.domain.profit.Profit;
import dev.banksalad.stock.openApi.iextrading.IexCloud;
import dev.banksalad.stock.openApi.iextrading.IexCloudProvider;
import dev.banksalad.stock.repository.StockRepository;
import dev.banksalad.stock.utils.StockAlgorithm;
import dev.banksalad.stock.web.dto.PriceAndDate;
import dev.banksalad.stock.web.dto.request.CreateProfit;
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
        List<PriceAndDate> priceAndDates = parsePriceAndDate(stockInformation);

        CreateProfit createProfit = StockAlgorithm.getMaxProfitAndDate(priceAndDates);
        Profit profit = CreateProfit.toEntity(createProfit);

        System.out.println(profit);
    }

    private List<PriceAndDate> parsePriceAndDate(List<StockInformationDto> stockInformation) {
        return stockInformation.stream()
            .map(stockInformationDto -> PriceAndDate
                .of(stockInformationDto.getClose(), stockInformationDto.getDate()))
            .collect(Collectors.toList());
    }

//    private List<Double> parsePrice(List<StockInformationDto> stockInformation) {
//        List<Double> price = new ArrayList<>();
//        for (StockInformationDto data : stockInformation) {
//            price.add(data.getClose());
//        }
//        return price;
//    }
//
//    private List<LocalDate> parseDate(List<StockInformationDto> stockInformation) {
//        List<LocalDate> dates = new ArrayList<>();
//        for (StockInformationDto data : stockInformation) {
//            dates.add(data.getDate());
//        }
//        return dates;
//    }
}
