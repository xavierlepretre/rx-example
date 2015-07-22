package com.bter.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketDetail
{
    public final int no;
    public final String symbol;
    public final String name;
    public final String nameCN;
    public final TradingPairId pair;
    public final double rate;
    public final double volumeA;
    public final double volumeB;
    public final String currencyA;
    public final String currencyB;
    public final String currencySuffix;
    public final double ratePercent;
    public final Trend trend;
    public final double supply;
    public final double marketCap;
    // TODO plot

    public MarketDetail(
            @JsonProperty("no") int no,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("name") String name,
            @JsonProperty("name_cn") String nameCN,
            @JsonProperty("pair") TradingPairId pair,
            @JsonProperty("rate") String rate,
            @JsonProperty("vol_a") String volumeA,
            @JsonProperty("vol_b") String volumeB,
            @JsonProperty("curr_a") String currencyA,
            @JsonProperty("curr_b") String currencyB,
            @JsonProperty("curr_suffix") String currencySuffix,
            @JsonProperty("rate_percent") String ratePercent,
            @JsonProperty("trend") Trend trend,
            @JsonProperty("supply") String supply,
            @JsonProperty("marketcap") String marketCap)
    {
        this.no = no;
        this.symbol = symbol;
        this.name = name;
        this.nameCN = nameCN;
        this.pair = pair;
        this.rate = Double.parseDouble(rate.replace(",", ""));
        this.volumeA = Double.parseDouble(volumeA.replace(",", ""));
        this.volumeB = Double.parseDouble(volumeB.replace(",", ""));
        this.currencyA = currencyA;
        this.currencyB = currencyB;
        this.currencySuffix = currencySuffix;
        this.ratePercent = Double.parseDouble(ratePercent.replace(",", ""));
        this.trend = trend;
        this.supply = Double.parseDouble(supply.replace(",", ""));
        this.marketCap = Double.parseDouble(marketCap.replace(",", ""));
    }

    @JsonIgnore public String getNiceName()
    {
        return String.format("%1$s - %2$s", currencyA, currencyB);
    }
}
