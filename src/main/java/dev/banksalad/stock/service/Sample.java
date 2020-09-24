package dev.banksalad.stock.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sample {

    private String date;

    @JsonProperty(value = "price")
    private Double close;

    private Long volume;

    public Sample(String date, Double close, Long volume) {
        this.date = date;
        this.close = close;
        this.volume = volume;
    }
}
