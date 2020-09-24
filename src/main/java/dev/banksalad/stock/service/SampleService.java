package dev.banksalad.stock.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SampleService {

    @Autowired
    WebClient.Builder builder;

    public List<Sample> getData() {
        WebClient webClient = builder.baseUrl("https://sandbox.iexapis.com").build();

        List<Sample> sampleList = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/stable")
                .path("/stock")
                .path("/APPL/chart/1m")
                .queryParam("token", "Tpk_14ad95bb91954d929f4d657bbcb51d58")
                .build())
            .retrieve()
            .bodyToFlux(Sample.class)
            .collectList()
            .block();

        return sampleList;
    }

}
