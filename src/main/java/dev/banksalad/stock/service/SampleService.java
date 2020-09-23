package dev.banksalad.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SampleService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public Sample[] getData() {
        RestTemplate restTemplate = restTemplateBuilder.rootUri("https://sandbox.iexapis.com").build();
        Sample[] data = restTemplate.getForObject("/stable/stock/AAPL/chart/1m?token=Tpk_14ad95bb91954d929f4d657bbcb51d58", Sample[].class);
        return data;
    }
}
