package com.melotic.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Market extends PriceTicker
{
    @JsonIgnore public MarketId id;
    public final String title;

    public Market(
            @JsonProperty("title") String title,
            @JsonProperty("highest_bid") double highestBid,
            @JsonProperty("highest_deal_price") double highestDealPrice,
            @JsonProperty("latest_price") double latestPrice,
            @JsonProperty("lowest_ask") double lowestAsk,
            @JsonProperty("lowest_deal_price") double lowestDealPrice,
            @JsonProperty("volume") double volume,
            @JsonProperty("price_change") double priceChange)
    {
        super(highestBid,
                highestDealPrice,
                latestPrice,
                lowestAsk,
                lowestDealPrice,
                volume,
                priceChange);
        this.title = title;
    }
}
