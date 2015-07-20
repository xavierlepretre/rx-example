package com.bter.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketInfo
{
    public final Boolean result;
    public final TradingPairMarketCompactMap pairs;

    public MarketInfo(
            @JsonProperty("result") boolean result,
            @JsonProperty("pairs") TradingPairMarketCompactMap pairs)
    {
        this.result = result;
        this.pairs = pairs;
    }
}
