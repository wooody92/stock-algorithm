package dev.banksalad.stock.service;

import dev.banksalad.stock.domain.stock.Stock;
import dev.banksalad.stock.repository.StockRepository;
import dev.banksalad.stock.web.dto.request.CreateStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final IexCloudService iexCloudService;

    private final StockRepository stockRepository;

    public void getIexCloudServiceData() {
        System.out.println(iexCloudService.getData("aapl"));
    }

    @Transactional
    public void create() {
        Stock stock = CreateStock.toEntity();
        System.out.println(stock);
        stockRepository.save(stock);
    }
}
