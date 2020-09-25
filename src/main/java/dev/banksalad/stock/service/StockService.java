package dev.banksalad.stock.service;

import dev.banksalad.stock.iextrading.IexCloud;
import dev.banksalad.stock.repository.StockRepository;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final IexCloudService iexCloudService;

    private final StockRepository stockRepository;

    public List<StockInformationDto> getStockInformation(String symbol) {
        List<IexCloud> iexClouds = iexCloudService.getData(symbol);
        return iexClouds.stream()
            .map(iexCloud -> StockInformationDto.of(symbol, iexCloud))
            .collect(Collectors.toList());
    }
}
