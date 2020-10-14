package dev.banksalad.stock.openApi;

import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.time.LocalDate;
import java.util.List;

public interface OpenApiProvider {

    <T> List<T> requestData(String symbol);

    <T> List<StockInformationDto> getStockData(String symbol, List<T> data);

    LocalDate getLatestRecordDate(String symbol);
}
