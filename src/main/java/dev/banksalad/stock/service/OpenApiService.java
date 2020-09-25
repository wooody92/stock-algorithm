package dev.banksalad.stock.service;

import java.util.List;

public interface OpenApiService {

    <T> List<T> requestData(String symbol);
}
