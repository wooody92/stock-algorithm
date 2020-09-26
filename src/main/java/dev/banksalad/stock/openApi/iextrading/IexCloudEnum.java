package dev.banksalad.stock.openApi.iextrading;

import lombok.Getter;

@Getter
public enum IexCloudEnum {
    BASE_URL("https://sandbox.iexapis.com/stable"),
    PATH_STOCK("/stock/"),
    PATH_CHART("/chart"),
    PATH_MONTH("/1m"),
    TOKEN("token"),
    PUBLIC_KEY("Tpk_14ad95bb91954d929f4d657bbcb51d58");

    private String value;

    IexCloudEnum(String value) {
        this.value = value;
    }
}
