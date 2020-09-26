package dev.banksalad.stock.openApi;

import java.util.List;

public interface OpenApiProvider {

    <T> List<T> requestData(String symbol);
}
