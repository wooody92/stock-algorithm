package dev.banksalad.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final IexCloudService iexCloudService;

    public void getIexCloudServiceData() {
        System.out.println(iexCloudService.getData("aapl"));
    }
}
