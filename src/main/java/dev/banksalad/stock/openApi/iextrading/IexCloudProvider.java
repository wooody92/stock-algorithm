package dev.banksalad.stock.openApi.iextrading;

import dev.banksalad.stock.global.error.exception.EmptyStockException;
import dev.banksalad.stock.global.error.exception.IexCloudException;
import dev.banksalad.stock.global.error.exception.InvalidSymbolException;
import dev.banksalad.stock.openApi.OpenApiProvider;
import dev.banksalad.stock.web.dto.response.StockInformationDto;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static dev.banksalad.stock.openApi.iextrading.IexCloudEnum.*;

@Component
@RequiredArgsConstructor
public class IexCloudProvider implements OpenApiProvider {

    private final WebClient.Builder builder;

    @Value("${IEX_CLOUD_PUBLIC_KEY}")
    private String IEX_CLOUD_PUBLIC_KEY;

    @Override
    public List<IexCloud> requestData(String symbol) {
        WebClient webClient = builder.baseUrl(BASE_URL.getValue()).build();
        List<IexCloud> iexCloudList = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(PATH_STOCK.getValue())
                .path(symbol)
                .path(PATH_CHART.getValue())
                .path(PATH_MONTH.getValue())
                .queryParam(TOKEN.getValue(), IEX_CLOUD_PUBLIC_KEY)
                .build())
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new InvalidSymbolException()))
            .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new IexCloudException()))
            .bodyToFlux(IexCloud.class)
            .collectList()
            .block();
        if (iexCloudList.isEmpty()) {
            throw new EmptyStockException();
        }
        return iexCloudList;
    }

    @Override
    public <T> List<StockInformationDto> getStockData(String symbol, List<T> iexClouds) {
        return iexClouds.stream()
            .map(iexCloud -> StockInformationDto.of(symbol, (IexCloud) iexCloud))
            .collect(Collectors.toList());
    }

    @Override
    public LocalDate getLatestRecordDate(String symbol) {
        WebClient webClient = builder.baseUrl(BASE_URL.getValue()).build();
        List<IexCloud> iexCloudList = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(PATH_STOCK.getValue())
                .path(symbol)
                .path(PATH_CHART.getValue())
                .path(PATH_DAYS.getValue())
                .queryParam(SORT.getValue(), DESC.getValue())
                .queryParam(TOKEN.getValue(), IEX_CLOUD_PUBLIC_KEY)
                .build())
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new InvalidSymbolException()))
            .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new IexCloudException()))
            .bodyToFlux(IexCloud.class)
            .collectList()
            .block();
        if (iexCloudList.isEmpty()) {
            throw new EmptyStockException();
        }
        return iexCloudList.get(0).getDate();
    }
}
