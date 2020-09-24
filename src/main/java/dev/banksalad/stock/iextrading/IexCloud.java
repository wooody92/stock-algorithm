package dev.banksalad.stock.iextrading;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IexCloud {

    private String date;

    @JsonProperty(value = "price")
    private Double close;

    private Long volume;

    public IexCloud(String date, Double close, Long volume) {
        this.date = date;
        this.close = close;
        this.volume = volume;
    }
}
