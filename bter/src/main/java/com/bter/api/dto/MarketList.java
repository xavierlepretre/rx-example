package com.bter.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketList
{
    public final Boolean result;
    public final Map<TradingPairId, MarketDetail> data;

    public MarketList(
            @JsonProperty("result") boolean result,
            @JsonProperty("data") List<MarketDetail> data)
    {
        this.result = result;
        this.data = new HashMap<>();
        for (MarketDetail marketDetail : data)
        {
            this.data.put(marketDetail.pair, marketDetail);
        }
    }
}
