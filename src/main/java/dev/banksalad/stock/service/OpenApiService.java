package dev.banksalad.stock.service;

import java.util.List;

public interface OpenApiService {

    <T> List<T> getData(String symbol);
}
