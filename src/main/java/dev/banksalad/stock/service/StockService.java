package dev.banksalad.stock.service;

import dev.banksalad.stock.iextrading.IexCloud;
import dev.banksalad.stock.repository.StockRepository;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final IexCloudService iexCloudService;

    private final StockRepository stockRepository;

    public List<StockInformationDto> getStockInformation(String symbol) {
        List<IexCloud> iexClouds = iexCloudService.requestData(symbol);
        return iexCloudService.getStockData(symbol, iexClouds);
    }
}
