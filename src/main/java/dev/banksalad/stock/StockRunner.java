package dev.banksalad.stock;

import dev.banksalad.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockRunner implements ApplicationRunner {

    private final StockService stockService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        stockService.getIexCloudServiceData();
    }
}
