package dev.banksalad.stock.web.controller;

import dev.banksalad.stock.service.IexCloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final IexCloudService iexCloudService;

    @GetMapping("/{symbol}")
    public ResponseEntity viewStockChart(@PathVariable String symbol) {
        return new ResponseEntity(iexCloudService.getData(symbol), HttpStatus.OK);
    }
}
