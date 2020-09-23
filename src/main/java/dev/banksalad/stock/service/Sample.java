package dev.banksalad.stock.service;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Sample {

    private String date;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Long volume;
}
