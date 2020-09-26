package dev.banksalad.stock.openApi.iextrading;

import lombok.Getter;

@Getter
public enum IexCloudEnum {
    BASE_URL("https://sandbox.iexapis.com/stable"),
    PATH_STOCK("/stock/"),
    PATH_CHART("/chart"),
    PATH_MONTH("/1m"),
    PATH_DAYS("/5d"),
    SORT("sort"),
    DESC("desc"),
    TOKEN("token");

    private String value;

    IexCloudEnum(String value) {
        this.value = value;
    }
}
