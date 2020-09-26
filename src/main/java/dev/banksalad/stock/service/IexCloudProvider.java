package dev.banksalad.stock.service;

import dev.banksalad.stock.iextrading.IexCloud;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static dev.banksalad.stock.iextrading.IexCloudEnum.*;

@Service
@RequiredArgsConstructor
public class IexCloudProvider implements OpenApiService{

    private final WebClient.Builder builder;

    @Override
    public List<IexCloud> requestData(String symbol) {
        WebClient webClient = builder.baseUrl(BASE_URL.getValue()).build();
        List<IexCloud> iexCloudList = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(PATH_STOCK.getValue())
                .path(symbol)
                .path(PATH_CHART.getValue())
                .path(PATH_MONTH.getValue())
                .queryParam(TOKEN.getValue(), PUBLIC_KEY.getValue())
                .build())
            .retrieve()
            .bodyToFlux(IexCloud.class)
            .collectList()
            .block();
        return iexCloudList;
    }

    public List<StockInformationDto> getStockData(String symbol, List<IexCloud> iexClouds) {
        return iexClouds.stream()
            .map(iexCloud -> StockInformationDto.of(symbol, iexCloud))
            .collect(Collectors.toList());
    }
}
