package dev.banksalad.stock.web.controller;

import dev.banksalad.stock.service.StockService;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import dev.banksalad.stock.web.dto.response.StockProfitResponse;
import java.util.List;
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

    private final StockService stockService;

    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockInformationDto>> viewStockChart(@PathVariable String symbol) {
        return new ResponseEntity(stockService.getStockInformation(symbol), HttpStatus.OK);
    }

    @GetMapping("/{symbol}/profit")
    public ResponseEntity<StockProfitResponse> viewMaxProfit(@PathVariable String symbol) {
        return new ResponseEntity<>(stockService.getMaxProfitDate(symbol), HttpStatus.OK);
    }
}
