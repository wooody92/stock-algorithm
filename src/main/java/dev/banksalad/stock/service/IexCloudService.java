package dev.banksalad.stock.service;

import dev.banksalad.stock.iextrading.IexCloud;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class IexCloudService {

    private final WebClient.Builder builder;

    public List<IexCloud> getData(String symbol) {
        WebClient webClient = builder.baseUrl("https://sandbox.iexapis.com/stable").build();

        List<IexCloud> iexCloudList = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/stock/")
                .path(symbol)
                .path("/chart/6m")
                .queryParam("token", "Tpk_14ad95bb91954d929f4d657bbcb51d58")
                .build())
            .retrieve()
            .bodyToFlux(IexCloud.class)
            .collectList()
            .block();

        return iexCloudList;
    }
}
