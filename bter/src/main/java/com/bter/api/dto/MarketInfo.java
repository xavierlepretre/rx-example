package com.bter.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketInfo
{
    public final Boolean result;
    public final Map<TradingPairId, MarketCompact> pairs;

    public MarketInfo(
            @JsonProperty("result") boolean result,
            @JsonProperty("pairs") List<? extends TradingPairMarketCompactMap> pairs)
    {
        this.result = result;
        this.pairs = new HashMap<>();
        for (TradingPairMarketCompactMap entry : pairs)
        {
            this.pairs.putAll(entry);
        }
    }
}
